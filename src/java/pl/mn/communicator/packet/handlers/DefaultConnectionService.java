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
package pl.mn.communicator.packet.handlers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.util.StringTokenizer;

import javax.swing.event.EventListenerList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pl.mn.communicator.GGException;
import pl.mn.communicator.GGSessionException;
import pl.mn.communicator.IConnectionService;
import pl.mn.communicator.IServer;
import pl.mn.communicator.Server;
import pl.mn.communicator.SessionState;
import pl.mn.communicator.event.ConnectionListener;
import pl.mn.communicator.event.GGPacketListener;
import pl.mn.communicator.event.PingListener;
import pl.mn.communicator.packet.GGHeader;
import pl.mn.communicator.packet.GGUtils;
import pl.mn.communicator.packet.in.GGIncomingPackage;
import pl.mn.communicator.packet.out.GGOutgoingPackage;
import pl.mn.communicator.packet.out.GGPing;

/**
 * The default implementation of <code>IConnectionService</code>.
 * <p>
 * Created on 2004-11-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: DefaultConnectionService.java,v 1.16 2005-01-31 21:21:46 winnetou25 Exp $
 */
public class DefaultConnectionService implements IConnectionService {

	private final static String WINDOW_ENCODING = "windows-1250";

	private final static Log logger = LogFactory.getLog(DefaultConnectionService.class);
	
	private EventListenerList m_listeners = new EventListenerList();
	
	/** reference to session object */
	private Session m_session = null;
	
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
		m_connectionThread = new ConnectionThread();
		m_connectionPinger = new PingerThread();
		m_packetChain = new PacketChain();
	}
	
	/**
	 * @see pl.mn.communicator.IConnectionService#getServer(int)
	 */
	public IServer lookupServer(int uin) throws GGException {
    	try {
        	URL url = new URL("http://appmsg.gadu-gadu.pl/appsvc/appmsg.asp?fmnumber="+ String.valueOf(uin));
        	
        	HttpURLConnection con = (HttpURLConnection) url.openConnection();

        	//con.setReadTimeout(120*1000); //JDK 1.5
        	//con.setConnectTimeout(120*1000);	//JDK 1.5

        	con.setDoInput(true);
        	con.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), WINDOW_ENCODING));

            String line = in.readLine();
            in.close();

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
			m_connectionThread.openConnection(server.getAddress(), server.getPort());
			m_connectionPinger.startPinging();
			m_session.getSessionAccessor().setSessionState(SessionState.CONNECTED);
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
			m_connectionThread.closeConnection();
			notifyConnectionClosed();
			m_session.getSessionAccessor().setSessionState(SessionState.DISCONNECTED);
			m_server = null;
		} catch (IOException ex) {
			logger.error("IOException occured while trying to disconnect", ex);
			m_session.getSessionAccessor().setSessionState(SessionState.CONNECTION_ERROR);
			throw new GGException("Unable to close connection to server", ex);
		}
	}

	/**
	 * @see pl.mn.communicator.IConnectionService#isConnected()
	 */
	public boolean isConnected() {
		boolean authenticated = m_session.getSessionState() == SessionState.LOGGED_IN;
		boolean authenticationAwaiting = m_session.getSessionState() == SessionState.AUTHENTICATION_AWAITING;
		boolean connected = m_session.getSessionState() == SessionState.CONNECTED;
		return authenticated || authenticationAwaiting || connected ;
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
		m_listeners.add(PingListener.class, pingListener);
	}

	/**
	 * @see pl.mn.communicator.IConnectionService#removePingListener(pl.mn.communicator.event.PingListener)
	 */
	public void removePingListener(PingListener pingListener) {
		m_listeners.remove(PingListener.class, pingListener);
	}
	
	//TODO clone the list of listeners
    protected void notifyConnectionEstablished() throws GGException {
    	m_session.getSessionAccessor().setSessionState(SessionState.AUTHENTICATION_AWAITING);
    	ConnectionListener[] connectionListeners = (ConnectionListener[]) m_listeners.getListeners(ConnectionListener.class);
    	for (int i=0; i<connectionListeners.length; i++) {
    		ConnectionListener connectionListener = connectionListeners[i];
    		connectionListener.connectionEstablished();
    	}
    	// this could be also realized as a ConnectionHandler in session class
    }

	//TODO clone the list of listeners
    protected void notifyConnectionClosed() throws GGException {
		m_session.getSessionAccessor().setSessionState(SessionState.DISCONNECTED);
		ConnectionListener[] connectionListeners = (ConnectionListener[]) m_listeners.getListeners(ConnectionListener.class);
		for (int i=0; i<connectionListeners.length; i++) {
			ConnectionListener connectionListener = connectionListeners[i];
			connectionListener.connectionClosed();
		}
    }

	//TODO clone the list of listeners
    protected void notifyConnectionError(final Exception ex) {
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

	//TODO clone the list of listeners
    protected void notifyPongReceived() {
    	PingListener[] pingListeners = (PingListener[]) m_listeners.getListeners(PingListener.class);
    	for (int i=0; i<pingListeners.length; i++) {
    		PingListener pingListener = pingListeners[i];
    		pingListener.pongReceived(m_server);
    	}
    }

	//TODO clone the list of listeners
    protected void notifyPacketReceived(GGIncomingPackage incomingPackage) {
    	GGPacketListener[] packetListeners = (GGPacketListener[]) m_listeners.getListeners(GGPacketListener.class);
    	for (int i=0; i<packetListeners.length; i++) {
    		GGPacketListener packetListener = packetListeners[i];
    		packetListener.receivedPacket(incomingPackage);
    	}
    }

	//TODO clone the list of listeners
    protected void notifyPacketSent(GGOutgoingPackage outgoingPackage) {
    	GGPacketListener[] packetListeners = (GGPacketListener[]) m_listeners.getListeners(GGPacketListener.class);
    	for (int i=0; i<packetListeners.length; i++) {
    		GGPacketListener packetListener = packetListeners[i];
    		packetListener.sentPacket(outgoingPackage);
    	}
    }

    protected void sendPackage(GGOutgoingPackage outgoingPackage) throws IOException {
    	int packetType = outgoingPackage.getPacketType();
		int length = outgoingPackage.getLength();
		byte[] contents = outgoingPackage.getContents();
		m_connectionThread.sendPackage(packetType, length, contents);
		notifyPacketSent(outgoingPackage);
    }
    
    private void checkDisconnectionState() {
		if (!(m_session.getSessionState() == SessionState.CONNECTED)
				|| (m_session.getSessionState() == SessionState.LOGGED_IN)
				|| (m_session.getSessionState() == SessionState.LOGGED_OUT)
				|| (m_session.getSessionState() == SessionState.AUTHENTICATION_AWAITING)
				|| (m_session.getSessionState() == SessionState.CONNECTION_ERROR)) {
			throw new GGSessionException(m_session.getSessionState());
		}
    }

    private void checkConnectionState() {
		if (!(m_session.getSessionState() == SessionState.CONNECTION_AWAITING)
			|| (m_session.getSessionState() == SessionState.DISCONNECTED)) {
			throw new GGSessionException(m_session.getSessionState());
		}
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
		private static final int THREAD_SLEEP_TIME = 100;
    	
    	private Socket m_socket = null;
    	private BufferedInputStream m_dataInput = null;
    	private BufferedOutputStream m_dataOutput = null;
    	private boolean m_active = true;
    	
    	public void run() {
    		try {
    			while (m_active) {
    		   		final byte[] headerData = new byte[HEADER_LENGTH];
    		   		if (m_dataInput.available() > 0) {
       					m_dataInput.read(headerData);
       					decodePacket(new GGHeader(headerData));
    		   		}
    				Thread.sleep(THREAD_SLEEP_TIME);
    			}
    		} catch (Exception ex) {
    			m_active = false;
    			logger.error("Connection error: "+ex);
    			notifyConnectionError(ex);
    		}
    	}
    	
    	private boolean isActive() {
    		return m_active;
    	}

    	private void openConnection(String host, int port) throws IOException {
    		//add runtime checking for port
    		m_socket = new Socket(host, port);
   			m_dataInput = new BufferedInputStream(m_socket.getInputStream());
   			m_dataOutput = new BufferedOutputStream(m_socket.getOutputStream());
   			start();
    	}
    	
    	private void closeConnection() throws IOException {
    		m_active = false;
    		m_dataInput = null;
    		m_dataOutput = null;
    		m_socket.close();
    	}
    	
    	private synchronized void sendPackage(int packetType, int length, byte[] packageContent) throws IOException {
    		logger.debug("Sending packet: "+packetType+", packetPayLoad: "+GGUtils.prettyBytesToString(packageContent));
    		
    		m_dataOutput.write(GGUtils.intToByte(packetType));
    		m_dataOutput.write(GGUtils.intToByte(length));
    		
    		if (length > 0) {
    			m_dataOutput.write(packageContent);
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
    	
    	private static final int PING_COUNT = 10;
    	private int m_pingCount = 0;
    	private boolean m_active = true;
		private static final int THREAD_SLEEP_TIME = 1000;
    	
    	/**
    	 * @see java.lang.Thread#run()
    	 */
		public void run() {
			while (m_active && m_connectionThread.isActive()) {
				try {
					if (++m_pingCount > PING_COUNT) {
						logger.debug("Pinging...");
						DefaultConnectionService.this.sendPackage(GGPing.getPing());
						DefaultConnectionService.this.notifyPingSent();
						m_pingCount = 0;
					}
					Thread.sleep(THREAD_SLEEP_TIME);
				} catch (IOException ex) {
					m_active = false;
					logger.error("PingerThreadError: "+ex);
					notifyConnectionError(ex);
				} catch (InterruptedException ex) {
					m_active = false;
					logger.debug("PingerThread was interruped");
				}
			}
		}
    	
    	private void startPinging() {
    		start();
    	}
    	
    	private void stopPinging() {
    		m_active = false;
    	}
    }

}
