/*
 * Copyright (c) 2003-2005 <a href="http://jggapi.sourceforge.net/team-list.html">JGGApi Development Team</a> All Rights Reserved.
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
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pl.mn.communicator.GGException;
import pl.mn.communicator.GGSessionException;
import pl.mn.communicator.IConnectionService;
import pl.mn.communicator.IServer;
import pl.mn.communicator.SessionState;
import pl.mn.communicator.event.ConnectionListener;
import pl.mn.communicator.event.GGPacketListener;
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
 * @version $Id: DefaultConnectionService.java,v 1.6 2004-12-19 18:50:51 winnetou25 Exp $
 */
public class DefaultConnectionService implements IConnectionService {

	private final static Log logger = LogFactory.getLog(DefaultConnectionService.class);
	
	private ArrayList m_connectionListeners = new ArrayList();
	private ArrayList m_packetListeners = new ArrayList();
	
	private Session m_session = null;
	private PacketChain m_packetChain = null;
	private ConnectionThread m_connectionThread = null;
	
	//friendly
	DefaultConnectionService(Session session) {
		if (session == null) throw new NullPointerException("session cannot be null");
		m_session = session;
		m_connectionThread = new ConnectionThread();
		m_packetChain = new PacketChain();
	}
	
	/**
	 * @see pl.mn.communicator.IConnectionService#connect()
	 */
	public void connect() throws GGException {
		checkConnectionState();
		m_session.getSessionAccessor().setSessionState(SessionState.CONNECTING);
		try {
			m_connectionThread.openConnection();
			m_session.getSessionAccessor().setSessionState(SessionState.CONNECTED);
		} catch (IOException ex) {
			m_session.getSessionAccessor().setSessionState(SessionState.CONNECTION_ERROR);
			throw new GGException("Unable to connect to Gadu-Gadu server: "+m_session.getServer(), ex);
		}
	}

	/**
	 * @see pl.mn.communicator.IConnectionService#disconnect()
	 */
	public void disconnect() {
		checkDisconnectionState();
		m_session.getSessionAccessor().setSessionState(SessionState.DISCONNECTING);
		try {
			m_connectionThread.closeConnection();
			notifyConnectionClosed();
			m_session.getSessionAccessor().setSessionState(SessionState.DISCONNECTED);
		} catch (IOException ex) {
			m_session.getSessionAccessor().setSessionState(SessionState.CONNECTION_ERROR);
		}
	}
	
	/**
	 * @see pl.mn.communicator.IConnectionService#isConnected()
	 */
	public boolean isConnected() {
		boolean authenticated = m_session.getSessionState() == SessionState.LOGGED_IN;
		boolean authenticationAwaiting = m_session.getSessionState() == SessionState.AUTHENTICATION_AWAITING;
		boolean connected = m_session.getSessionState() == SessionState.CONNECTED;
		return authenticated || authenticationAwaiting || connected;
	}
	
	/**
	 * @see pl.mn.communicator.IConnectionService#addConnectionListener(pl.mn.communicator.ConnectionListener)
	 */
	public void addConnectionListener(ConnectionListener connectionListener) {
		if (connectionListener == null) throw new NullPointerException("connectionListener cannot be null");
		m_connectionListeners.add(connectionListener);
	}

	/**
	 * @see pl.mn.communicator.IConnectionService#removeConnectionListener(pl.mn.communicator.ConnectionListener)
	 */
	public void removeConnectionListener(ConnectionListener connectionListener) {
		if (connectionListener == null) throw new NullPointerException("connectionListener cannot be null");
		m_connectionListeners.remove(connectionListener);
	}
	
	/**
	 * @see pl.mn.communicator.IConnectionService#addPacketListener(pl.mn.communicator.GGPacketListener)
	 */
	public void addPacketListener(GGPacketListener packetListener) {
		if (packetListener == null) throw new NullPointerException("packetListener cannot be null");
		m_packetListeners.add(packetListener);
	}
	
	/**
	 * @see pl.mn.communicator.IConnectionService#removePacketListener(pl.mn.communicator.GGPacketListener)
	 */
	public void removePacketListener(GGPacketListener packetListener) {
		if (packetListener == null) throw new NullPointerException("packetListener cannot be null");
		m_packetListeners.remove(packetListener);
	}

	//TODO clone the list of listeners
    protected void notifyConnectionEstablished() {
    	m_session.getSessionAccessor().setSessionState(SessionState.AUTHENTICATION_AWAITING);
    	for (Iterator it = m_connectionListeners.iterator(); it.hasNext();) {
    		final ConnectionListener connectionListener = (ConnectionListener) it.next();
    		connectionListener.connectionEstablished();
    	}
    	// this could be also realized as a ConnectionHandler in session class
    }

	//TODO clone the list of listeners
    protected void notifyConnectionClosed() {
		m_session.getSessionAccessor().setSessionState(SessionState.DISCONNECTED);
    	for (Iterator it = m_connectionListeners.iterator(); it.hasNext();) {
    		final ConnectionListener connectionListener = (ConnectionListener) it.next();
    		connectionListener.connectionClosed();
    	}
    }

	//TODO clone the list of listeners
    protected void notifyConnectionError(final Exception ex) {
    	for (Iterator it = m_connectionListeners.iterator(); it.hasNext();) {
    		final ConnectionListener connectionListener = (ConnectionListener) it.next();
    		connectionListener.connectionError(ex);
    	}
       	m_session.getSessionAccessor().setSessionState(SessionState.CONNECTION_ERROR);
    }

	//TODO clone the list of listeners
    protected void notifyPongReceived() {
    	for (Iterator it = m_connectionListeners.iterator(); it.hasNext();) {
    		final ConnectionListener connectionListener = (ConnectionListener) it.next();
    		connectionListener.pongReceived();
    	}
    }

	//TODO clone the list of listeners
    protected void notifyPacketReceived(GGIncomingPackage incomingPackage) {
    	for (Iterator it = m_packetListeners.iterator(); it.hasNext();) {
    		GGPacketListener packetListener = (GGPacketListener) it.next();
    		packetListener.receivedPacket(incomingPackage);
    	}
    }

	//TODO clone the list of listeners
    protected void notifyPacketSent(GGOutgoingPackage outgoingPackage) {
    	for (Iterator it = m_packetListeners.iterator(); it.hasNext();) {
    		GGPacketListener packetListener = (GGPacketListener) it.next();
    		packetListener.sentPacket(outgoingPackage);
    	}
    }

    protected void sendPackage(GGOutgoingPackage outgoingPackage) throws IOException {
    	logger.debug("Sending packet, packetType: "+outgoingPackage.getPacketType());
    	logger.debug("PacketBody: "+GGUtils.bytesToString(outgoingPackage.getContents()));
    	int header = outgoingPackage.getPacketType();
		int length = outgoingPackage.getLength();
		byte[] contents = outgoingPackage.getContents();
		m_connectionThread.sendPackage(header, length, contents);
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
    
    private class ConnectionThread extends Thread {
    	
		private static final int HEADER_LENGTH = 8;
		private static final int PING_COUNT = 25;
		private static final int THREAD_SLEEP_TIME = 500;
    	
    	private Socket m_socket = null;
    	private BufferedInputStream m_dataInput = null;
    	private BufferedOutputStream m_dataOutput = null;
    	private boolean m_active = true;
    	private int m_pingCount = 0;
    	
    	public void run() {
    		try {
    			while (m_active) {
    		   		final byte[] headerData = new byte[HEADER_LENGTH];
    		   		if (m_dataInput.available() > 0) {
       					m_dataInput.read(headerData);
       					decodePacket(new GGHeader(headerData));
    		   		} else {
    		   			ping();
    		   		}
    				Thread.sleep(THREAD_SLEEP_TIME);
    			}
    		} catch (Exception ex) {
    			logger.error("connection error: "+ex);
    			notifyConnectionError(ex);
    		}
    	}

    	private void openConnection() throws IOException {
    		IServer server = m_session.getServer();
    		m_socket = new Socket(server.getAddress(), server.getPort());
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
    	
    	private void sendPackage(int header, int length, byte[] packageContent) throws IOException {
    		m_dataOutput.write(GGUtils.intToByte(header));
    		m_dataOutput.write(GGUtils.intToByte(length));
    		
    		if (length > 0) m_dataOutput.write(packageContent);

    		m_dataOutput.flush();
    	}
    	
		private void ping() throws IOException {
			if (++m_pingCount > PING_COUNT) {
				logger.debug("Pinging...");
				DefaultConnectionService.this.sendPackage(GGPing.getPing());
				m_pingCount = 0;
			}
		}
		
		private void decodePacket(GGHeader header) throws IOException {
			byte[] keyBytes = new byte[header.getLength()];
			m_dataInput.read(keyBytes);
			Context context = new Context(m_session, header, keyBytes);
			m_packetChain.sendToChain(context);
		}
		
    }

}
