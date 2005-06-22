/*
 * Copyright (c) 2003-2005 JGGApi Development Team. All Rights Reserved.
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package pl.mn.communicator;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;
import java.util.Queue;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.event.EventListenerList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pl.mn.communicator.event.ConnectionListener;
import pl.mn.communicator.event.GGPacketListener;
import pl.mn.communicator.event.PingListener;
import pl.mn.communicator.packet.GGHeader;
import pl.mn.communicator.packet.GGUtils;
import pl.mn.communicator.packet.handlers.PacketChain;
import pl.mn.communicator.packet.handlers.PacketContext;
import pl.mn.communicator.packet.in.GGIncomingPackage;
import pl.mn.communicator.packet.out.GGOutgoingPackage;
import pl.mn.communicator.packet.out.GGPing;

/**
 * The default implementation of <code>IConnectionService</code>.
 * <p>
 * Created on 2004-11-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: DefaultConnectionService.java,v 1.14 2005-06-22 12:34:13 winnetou25 Exp $
 */
public class DefaultConnectionService implements IConnectionService {

	private final static String WINDOW_ENCODING = "windows-1250";

	private final static Log logger = LogFactory.getLog(DefaultConnectionService.class);
	
	private EventListenerList m_listeners = new EventListenerList();
	
	/** reference to session object */
	private Session m_session = null;
	
	private Queue m_senderQueue = new ConcurrentLinkedQueue();
	
	/** chain that handles packages */
	private PacketChain m_packetChain = null;

	/** thread that monitors connection */
	private ConnectionThread m_connectionThread = null;
	
	/** the thread that pings the connection to keep it alive */
	private PingerThread m_connectionPinger = null;
	
	private IServer m_server = null;
	
	//friendly
	DefaultConnectionService(Session session) {
		if (session == null) throw new NullPointerException("session cannot be null");
		m_session = session;
		m_packetChain = new PacketChain();
	}
	
	/**
	 * @see pl.mn.communicator.IConnectionService#getServer(int)
	 */
	public IServer lookupServer(int uin) throws GGException {
    	try {
        	IGGConfiguration configuration = m_session.getGGConfiguration();

    		URL url = new URL(configuration.getServerLookupURL()+"?fmnumber="+String.valueOf(uin));
        	
        	HttpURLConnection con = (HttpURLConnection) url.openConnection();
        	con.setConnectTimeout(configuration.getSocketTimeoutInMiliseconds());
        	con.setReadTimeout(configuration.getSocketTimeoutInMiliseconds());
        	
        	con.setDoInput(true);
        	con.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), WINDOW_ENCODING));

            String line = reader.readLine();
            reader.close();

            return parseAddress(line);
    	} catch (IOException ex) {
    		throw new GGException("Unable to get default server for uin: "+String.valueOf(uin), ex);
    	}
	}

	/**
	 * @see pl.mn.communicator.IConnectionService#connect()
	 */
	public void connect(IServer server) throws GGException {
		if (server == null) throw new NullPointerException("server cannot be null");
		m_server = server;
		checkConnectionState();
		m_session.getSessionAccessor().setSessionState(SessionState.CONNECTING);
		try {
			m_connectionThread = new ConnectionThread();
			m_connectionPinger = new PingerThread();
		    m_connectionThread.openConnection(server.getAddress(), server.getPort());
			m_connectionPinger.startPinging();
		} catch (IOException ex) {
			m_session.getSessionAccessor().setSessionState(SessionState.CONNECTION_ERROR);
			throw new GGException("Unable to connect to Gadu-Gadu server: "+server, ex);
		}
	}

	/**
	 * @see pl.mn.communicator.IConnectionService#disconnect()
	 */
	public void disconnect() throws GGException {
	    checkDisconnectionState();
		m_session.getSessionAccessor().setSessionState(SessionState.DISCONNECTING);
		try {
		    if (m_connectionPinger != null) {
			    m_connectionPinger.stopPinging();
				m_connectionPinger = null;
		    }
		    if (m_connectionThread != null) {
			    m_connectionThread.closeConnection();
				m_connectionThread = null;
		    }
			m_server = null;
			m_session.getSessionAccessor().setSessionState(SessionState.DISCONNECTED);
			notifyConnectionClosed();
		} catch (IOException ex) {
			logger.error("IOException occured while trying to disconnect", ex);
			m_session.getSessionAccessor().setSessionState(SessionState.CONNECTION_ERROR);
			throw new GGException("Unable to close connection to server", ex);
		}
	}

    private void checkDisconnectionState() throws GGSessionException {
        if (m_session.getSessionState() == SessionState.DISCONNECTED) {
            throw new GGSessionException(SessionState.DISCONNECTED);
        }
    }

    /**
	 * @see pl.mn.communicator.IConnectionService#isConnected()
	 */
	public boolean isConnected() {
		boolean authenticated = m_session.getSessionState() == SessionState.LOGGED_IN;
		boolean authenticationAwaiting = m_session.getSessionState() == SessionState.AUTHENTICATION_AWAITING;
		boolean connected = m_session.getSessionState() == SessionState.CONNECTED;

		return (authenticated || authenticationAwaiting || connected);
	}
	
	/**
	 * @see pl.mn.communicator.IConnectionService#getServer()
	 */
	public IServer getServer() {
		return m_server;
	}
	
	/**
	 * @see pl.mn.communicator.IConnectionService#addConnectionListener(pl.mn.communicator.event.ConnectionListener)
	 */
	public void addConnectionListener(ConnectionListener connectionListener) {
		if (connectionListener == null) throw new NullPointerException("connectionListener cannot be null");
		m_listeners.add(ConnectionListener.class, connectionListener);
	}

	/**
	 * @see pl.mn.communicator.IConnectionService#removeConnectionListener(pl.mn.communicator.event.ConnectionListener)
	 */
	public void removeConnectionListener(ConnectionListener connectionListener) {
		if (connectionListener == null) throw new NullPointerException("connectionListener cannot be null");
		m_listeners.remove(ConnectionListener.class, connectionListener);
	}
	
	/**
	 * @see pl.mn.communicator.IConnectionService#addPacketListener(pl.mn.communicator.event.GGPacketListener)
	 */
	public void addPacketListener(GGPacketListener packetListener) {
		if (packetListener == null) throw new NullPointerException("packetListener cannot be null");
		m_listeners.add(GGPacketListener.class, packetListener);
	}
	
	/**
	 * @see pl.mn.communicator.IConnectionService#removePacketListener(pl.mn.communicator.event.GGPacketListener)
	 */
	public void removePacketListener(GGPacketListener packetListener) {
		if (packetListener == null) throw new NullPointerException("packetListener cannot be null");
		m_listeners.remove(GGPacketListener.class, packetListener);
	}
	
	/**
	 * @see pl.mn.communicator.IConnectionService#addPingListener(pl.mn.communicator.event.PingListener)
	 */
	public void addPingListener(PingListener pingListener) {
	    if (pingListener == null) throw new IllegalArgumentException("pingListener cannot be null");
		m_listeners.add(PingListener.class, pingListener);
	}

	/**
	 * @see pl.mn.communicator.IConnectionService#removePingListener(pl.mn.communicator.event.PingListener)
	 */
	public void removePingListener(PingListener pingListener) {
	    if (pingListener == null) throw new IllegalArgumentException("pingListener cannot be null");
	    m_listeners.remove(PingListener.class, pingListener);
	}
	
    protected void notifyConnectionEstablished() throws GGException {
    	m_session.getSessionAccessor().setSessionState(SessionState.AUTHENTICATION_AWAITING);
    	ConnectionListener[] connectionListeners = (ConnectionListener[]) m_listeners.getListeners(ConnectionListener.class);
    	for (int i=0; i<connectionListeners.length; i++) {
    		ConnectionListener connectionListener = connectionListeners[i];
    		connectionListener.connectionEstablished();
    	}
    	// this could be also realized as a ConnectionHandler in session class
    }

    protected void notifyConnectionClosed() throws GGException {
		m_session.getSessionAccessor().setSessionState(SessionState.DISCONNECTED);
		ConnectionListener[] connectionListeners = (ConnectionListener[]) m_listeners.getListeners(ConnectionListener.class);
		for (int i=0; i<connectionListeners.length; i++) {
			ConnectionListener connectionListener = connectionListeners[i];
			connectionListener.connectionClosed();
		}
    }

    protected void notifyConnectionError(final Exception ex) throws GGException {
    	ConnectionListener[] connectionListeners = (ConnectionListener[]) m_listeners.getListeners(ConnectionListener.class);
    	for (int i=0; i<connectionListeners.length; i++) {
    		ConnectionListener connectionListener = connectionListeners[i];
    		connectionListener.connectionError(ex);
    	}
    	m_session.getSessionAccessor().setSessionState(SessionState.CONNECTION_ERROR);
    }

    protected void notifyPingSent() {
    	PingListener[] pingListeners = (PingListener[]) m_listeners.getListeners(PingListener.class);
    	for (int i=0; i<pingListeners.length; i++) {
    		PingListener pingListener = pingListeners[i];
    		pingListener.pingSent(m_server);
    	}
    }

    protected void notifyPongReceived() {
    	PingListener[] pingListeners = (PingListener[]) m_listeners.getListeners(PingListener.class);
    	for (int i=0; i<pingListeners.length; i++) {
    		PingListener pingListener = pingListeners[i];
    		pingListener.pongReceived(m_server);
    	}
    }

    protected void notifyPacketReceived(GGIncomingPackage incomingPackage) {
    	GGPacketListener[] packetListeners = (GGPacketListener[]) m_listeners.getListeners(GGPacketListener.class);
    	for (int i=0; i<packetListeners.length; i++) {
    		GGPacketListener packetListener = packetListeners[i];
    		packetListener.receivedPacket(incomingPackage);
    	}
    }

    protected void notifyPacketSent(GGOutgoingPackage outgoingPackage) {
    	GGPacketListener[] packetListeners = (GGPacketListener[]) m_listeners.getListeners(GGPacketListener.class);
    	for (int i=0; i<packetListeners.length; i++) {
    		GGPacketListener packetListener = packetListeners[i];
    		packetListener.sentPacket(outgoingPackage);
    	}
    }

    protected void sendPackage(GGOutgoingPackage outgoingPackage) throws IOException {
		m_senderQueue.add(outgoingPackage);
    }
    
    private void checkConnectionState() throws GGSessionException {
        if (m_session.getSessionState() == SessionState.CONNECTION_AWAITING) {
            return;
        }
        if (m_session.getSessionState() == SessionState.DISCONNECTED) {
            return;
        }
        if (m_session.getSessionState() == SessionState.CONNECTION_ERROR) {
            return;
        }
        throw new GGSessionException(m_session.getSessionState());
    }
        
    /**
     * Parses the server's address.
     * @param line line to be parsed.
     * @return <code>Server</code> the server object. 
     */
    private static Server parseAddress(String line) {
        final int tokensNumber = 3;
        StringTokenizer token = new StringTokenizer(line);

        for (int i=0; i<tokensNumber; i++) {
            token.nextToken();
        }
        StringTokenizer tokenizer = new StringTokenizer(token.nextToken(), ":");

        return new Server(tokenizer.nextToken(), Integer.parseInt(tokenizer.nextToken()));
    }

    private class ConnectionThread extends Thread {
    	
		private static final int HEADER_LENGTH = 8;
    	
    	private Socket m_socket = null;
    	private BufferedInputStream m_dataInput = null;
    	private BufferedOutputStream m_dataOutput = null;
    	private boolean m_active = true;
    	
    	public void run() {
    		try {
    			while (m_active) {
    			    handleInput();
    			    if (!m_senderQueue.isEmpty()) {
        			    handleOutput();
    			    }
    			    int sleepTime = m_session.getGGConfiguration().getConnectionThreadSleepTimeInMiliseconds();
    				Thread.sleep(sleepTime);
    			}
    			m_dataInput = null;
        		m_dataOutput = null;
        		m_socket.close();
    		} catch (Exception ex) {
    			try {
        			m_active = false;
    			    notifyConnectionError(ex);
                } catch (GGException ex2) {
                    logger.warn("Unable to notify listeners", ex);
                }
    		}
    	}
    	
    	private void handleInput() throws IOException, GGException {
    	    final byte[] headerData = new byte[HEADER_LENGTH];
    	    if (m_dataInput.available() > 0) {
    	        m_dataInput.read(headerData);
    	        decodePacket(new GGHeader(headerData));
    	    }
    	}

    	private void handleOutput() throws IOException {
    	    while (!m_senderQueue.isEmpty() && !m_socket.isClosed() && m_dataOutput != null) {
    	        GGOutgoingPackage outgoingPackage = (GGOutgoingPackage) m_senderQueue.poll();
    	        sendPackage(outgoingPackage);
    			notifyPacketSent(outgoingPackage);
    	    }
    	}

    	private boolean isActive() {
    		return m_active;
    	}

    	private void openConnection(String host, int port) throws IOException {
    		//add runtime checking for port
    		m_socket = new Socket();
    		SocketAddress socketAddress = new InetSocketAddress(InetAddress.getByName(host), port);
    		int socketTimeoutInMiliseconds = m_session.getGGConfiguration().getSocketTimeoutInMiliseconds();
    		m_socket.connect(socketAddress, socketTimeoutInMiliseconds);
    		m_socket.setKeepAlive(true);
    		m_socket.setSoTimeout(socketTimeoutInMiliseconds);
   			m_dataInput = new BufferedInputStream(m_socket.getInputStream());
   			m_dataOutput = new BufferedOutputStream(m_socket.getOutputStream());
   			start();
    	}
    	
    	private void closeConnection() throws IOException {
    	    logger.debug("Closing connection...");
    	    m_active = false;
    	}
    	
    	private synchronized void sendPackage(GGOutgoingPackage outgoingPackage) throws IOException {
    		logger.debug("Sending packet: "+outgoingPackage.getPacketType()+", packetPayLoad: "+GGUtils.prettyBytesToString(outgoingPackage.getContents()));
    		
    		m_dataOutput.write(GGUtils.intToByte(outgoingPackage.getPacketType()));
    		m_dataOutput.write(GGUtils.intToByte(outgoingPackage.getLength()));
    		
    		if (outgoingPackage.getLength() > 0) {
    			m_dataOutput.write(outgoingPackage.getContents());
    		}

    		m_dataOutput.flush();
    	}
    	
		private void decodePacket(GGHeader header) throws IOException, GGException {
			byte[] keyBytes = new byte[header.getLength()];
			m_dataInput.read(keyBytes);
			PacketContext context = new PacketContext(m_session, header, keyBytes);
			m_packetChain.sendToChain(context);
		}
		
    }
    
    private class PingerThread extends Thread {
    	
    	private boolean m_active = false;
    	
    	/**
    	 * @see java.lang.Thread#run()
    	 */
		public void run() {
			while (m_active && m_connectionThread.isActive()) {
				try {
					logger.debug("Pinging...");
					DefaultConnectionService.this.sendPackage(GGPing.getPing());
					DefaultConnectionService.this.notifyPingSent();
					int pingInterval = m_session.getGGConfiguration().getPingIntervalInMiliseconds();
					Thread.sleep(pingInterval);
				} catch (IOException ex) {
					m_active = false;
					//logger.error("PingerThreadError: ", ex);
					try {
                        notifyConnectionError(ex);
                    } catch (GGException e) {
                        logger.warn("Unable to notify connection error listeners", ex);
                    }
				} catch (InterruptedException ex) {
					m_active = false;
					logger.debug("PingerThread was interruped", ex);
				}
			}
		}
    	
    	private void startPinging() {
    	    logger.debug("Starting pinging...");
    	    m_active = true;
    	    start();
    	}
    	
    	private void stopPinging() {
    	    logger.debug("Stopping pinging...");
    	    m_active = false;
    	}
    }
    
}
