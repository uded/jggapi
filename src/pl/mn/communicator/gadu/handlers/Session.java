/*
 * Created on 2004-11-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pl.mn.communicator.gadu.handlers;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import pl.mn.communicator.IConnectionService;
import pl.mn.communicator.IContactListService;
import pl.mn.communicator.IFileService;
import pl.mn.communicator.ILoginService;
import pl.mn.communicator.IMessageService;
import pl.mn.communicator.IPresenceService;
import pl.mn.communicator.IPublicDirectoryService;
import pl.mn.communicator.IRegistrationService;
import pl.mn.communicator.IServer;
import pl.mn.communicator.ISession;
import pl.mn.communicator.IStatus;
import pl.mn.communicator.IUser;
import pl.mn.communicator.MessageArrivedEvent;
import pl.mn.communicator.MessageDeliveredEvent;
import pl.mn.communicator.SessionState;
import pl.mn.communicator.SessionStateEvent;
import pl.mn.communicator.SessionStateListener;
import pl.mn.communicator.gadu.GGIncomingPackage;
import pl.mn.communicator.gadu.GGOutgoingPackage;

/**
 * @author mateusz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Session implements ISession {

	public final static String SEED = "seed";

	private int m_sessionState = SessionState.CONNECTION_AWAITING;
	
	private SessionAccessor m_sessionAccessor = null; 
	private Set m_sessionStateListeners = null;
	private HashMap m_sessionAttributes;
	
	private DefaultConnectionService m_connectionService = null;
	private DefaultLoginService m_loginService = null;
	private DefaultPresenceService m_presenceService = null;
	private DefaultMessageService m_messageService = null;
	private DefaultRegistrationService m_registrationService = null;
	
	private IServer m_server = null;
	
	public Session(IServer server) {
		if (server == null) throw new NullPointerException("server cannot be null");
		m_server = server;
		m_sessionAccessor = new SessionAccessor();
		m_sessionAttributes = new HashMap();
		m_sessionStateListeners = new HashSet();
		m_connectionService = new DefaultConnectionService(this);
		m_loginService = new DefaultLoginService(this);
		m_presenceService = new DefaultPresenceService(this);
		m_messageService = new DefaultMessageService(this);
		m_registrationService = new DefaultRegistrationService(this);
	}

	public int getSessionState() {
		return m_sessionState;
	}
	
	public IServer getServer() {
		return m_server;
	}
	
	public boolean getBooleanAttribute(String attributeName) {
		Boolean booleanObject = (Boolean) m_sessionAttributes.get(attributeName);
		return booleanObject.booleanValue();
	}

	public int getIntegerAttribute(String attributeName) {
		Integer intObject = (Integer) m_sessionAttributes.get(attributeName);
		return intObject.intValue();
	}
	
	public void addSessionStateListener(SessionStateListener sessionStateListener) {
		if (sessionStateListener == null) throw new NullPointerException("sessionStateListener cannot be null.");
		m_sessionStateListeners.add(sessionStateListener);
	}
	
	public void removeSessionStateListener(SessionStateListener sessionStateListener) {
		if (sessionStateListener == null) throw new NullPointerException("sessionStateListener cannot be null");
		m_sessionStateListeners.remove(sessionStateListener);
	}
	
	/**
	 * @see pl.mn.communicator.ISession#getConnectionService()
	 */
	public IConnectionService getConnectionService() {
		return m_connectionService;
	}

	/**
	 * @see pl.mn.communicator.ISession#getLoginService()
	 */
	public ILoginService getLoginService() {
		return m_loginService;
	}

	/**
	 * @see pl.mn.communicator.ISession#getMessageService()
	 */
	public IMessageService getMessageService() {
		return m_messageService;
	}

	/**
	 * @see pl.mn.communicator.ISession#getPresenceService()
	 */
	public IPresenceService getPresenceService() {
		return m_presenceService;
	}

	/**
	 * @see pl.mn.communicator.ISession#getFileService()
	 */
	public IFileService getFileService() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see pl.mn.communicator.ISession#getPublicDirectoryService()
	 */
	public IPublicDirectoryService getPublicDirectoryService() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see pl.mn.communicator.ISession#getRegistrationService()
	 */
	public IRegistrationService getRegistrationService() {
		return m_registrationService;
	}

	/**
	 * @see pl.mn.communicator.ISession#getContactListService()
	 */
	public IContactListService getContactListService() {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected void notifySessionStateChanged(int oldState, int newState) {
		
		for (Iterator it = m_sessionStateListeners.iterator(); it.hasNext();) {
			SessionStateListener sessionStateListener = (SessionStateListener) it.next();
			if (oldState != newState) {
				SessionStateEvent sessionStateEvent = new SessionStateEvent(this, oldState, newState);
				sessionStateListener.sessionStateChanged(sessionStateEvent);
			}
		}
	}
	
	//friendly
	SessionAccessor getSessionAccessor() {
		return m_sessionAccessor;
	}

	public class SessionAccessor {
		
		public void setSessionState(int sessionState) {
			if (m_sessionState != sessionState) {
				int oldState = m_sessionState;
				m_sessionState = sessionState;
				notifySessionStateChanged(oldState, sessionState);
			}
		}
		
		public void sendPackage(GGOutgoingPackage outgoingPackage) throws IOException {
			m_connectionService.sendPackage(outgoingPackage);
		}
		
		public void notifyConnectionEstablished() {
			m_connectionService.notifyConnectionEstablished();
		}

		public void notifyConnectionClosed() {
			m_connectionService.notifyConnectionClosed();
		}

		public void notifyConnectionError(Exception exception) {
			m_connectionService.notifyConnectionError(exception);
		}
		
		public void notifyPongReceived() {
			m_connectionService.notifyPongReceived();
		}

		public void notifyLoginOK() {
			m_loginService.notifyLoginOK();
		}
		
		public void notifyLoginFailed() {
			m_loginService.notifyLoginFailed();
		}

		public void notifyUserChangedStatus(IUser user, IStatus status) {
			m_presenceService.notifyUserChangedStatus(user, status);
		}

		public void notifyMessageArrived(MessageArrivedEvent messageArrivedEvent) {
			m_messageService.notifyMessageArrived(messageArrivedEvent);
		}

		public void notifyMessageDelivered(MessageDeliveredEvent messageDeliveredEvent) {
			m_messageService.notifyMessageDelivered(messageDeliveredEvent);
		}
		
		public void notifyGGPacketReceived(GGIncomingPackage incomingPackage) {
			m_connectionService.notifyPacketReceived(incomingPackage);
		}

		public void setBooleanAttribute(String attributeName, boolean bool) {
			m_sessionAttributes.put(attributeName, new Boolean(bool));
		}

		public void setIntegerAttribute(String attributeName, int integer) {
			m_sessionAttributes.put(attributeName, new Integer(integer));
		}

	}
	
}