/*
 * Copyright (c) 2003 Marcin Naglik (mnaglik@gazeta.pl)
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
package pl.mn.communicator.gadu.handlers;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import pl.mn.communicator.IConnectionService;
import pl.mn.communicator.IContactListService;
import pl.mn.communicator.ILoginService;
import pl.mn.communicator.IMessageService;
import pl.mn.communicator.IPresenceService;
import pl.mn.communicator.IServer;
import pl.mn.communicator.ISession;
import pl.mn.communicator.IStatus60;
import pl.mn.communicator.IUser;
import pl.mn.communicator.LoginContext;
import pl.mn.communicator.SessionState;
import pl.mn.communicator.event.MessageArrivedEvent;
import pl.mn.communicator.event.MessageDeliveredEvent;
import pl.mn.communicator.event.SessionStateEvent;
import pl.mn.communicator.event.SessionStateListener;
import pl.mn.communicator.gadu.GGIncomingPackage;
import pl.mn.communicator.gadu.GGOutgoingPackage;

/**
 * Created on 2004-11-28
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: Session.java,v 1.8 2004-12-13 23:44:02 winnetou25 Exp $
 */
public class Session implements ISession {

	private SessionState m_sessionState = SessionState.CONNECTION_AWAITING;
	
	private SessionAccessor m_sessionAccessor = null; 
	private Set m_sessionStateListeners = null;
	private HashMap m_sessionAttributes;
	
	private LoginContext m_loginContext = null;
	
	private DefaultConnectionService m_connectionService = null;
	private DefaultLoginService m_loginService = null;
	private DefaultPresenceService m_presenceService = null;
	private DefaultMessageService m_messageService = null;
	private DefaultRegistrationService m_registrationService = null;
	private DefaultContactListService m_contactListService = null;
	
	private IServer m_server = null;
	
	public Session(IServer server, LoginContext loginContext) {
		if (server == null) throw new NullPointerException("server cannot be null");
		if (loginContext == null) throw new NullPointerException("loginContext cannot be null");
		m_server = server;
		m_loginContext = loginContext;
		m_sessionAccessor = new SessionAccessor();
		m_sessionAttributes = new HashMap();
		m_sessionStateListeners = new HashSet();
		m_connectionService = new DefaultConnectionService(this);
		m_loginService = new DefaultLoginService(this);
		m_messageService = new DefaultMessageService(this);
		m_presenceService = new DefaultPresenceService(this);
		m_contactListService = new DefaultContactListService(this);
		//m_registrationService = new DefaultRegistrationService(this);
	}

	public SessionState getSessionState() {
		return m_sessionState;
	}
	
	public IServer getServer() {
		return m_server;
	}
	
	public LoginContext getLoginContext() {
		return m_loginContext;
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

//	/**
//	 * @see pl.mn.communicator.ISession#getFileService()
//	 */
//	public IFileService getFileService() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @see pl.mn.communicator.ISession#getPublicDirectoryService()
//	 */
//	public IPublicDirectoryService getPublicDirectoryService() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * @see pl.mn.communicator.ISession#getRegistrationService()
//	 */
//	public IRegistrationService getRegistrationService() {
//		return m_registrationService;
//	}

	/**
	 * @see pl.mn.communicator.ISession#getContactListService()
	 */
	public IContactListService getContactListService() {
		return m_contactListService;
	}
	
	protected void notifySessionStateChanged(SessionState oldState, SessionState newState) {
		
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
		
		public void setSessionState(SessionState sessionState) {
			if (m_sessionState != sessionState) {
				SessionState oldState = m_sessionState;
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

		public void notifyUserChangedStatus(IUser user, IStatus60 status) {
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
		
		public void notifyContactListExported() {
			m_contactListService.notifyContactListExported();
		}
		
		public void notifyContactListReceived(Collection contacts) {
			m_contactListService.notifyContactListReceived(contacts);
		}

		public void setLoginSeed(int seed) {
			m_sessionAttributes.put("seed", new Integer(seed));
		}

		public int getLoginSeed() {
			if (!m_sessionAttributes.containsKey("seed")) return -1;
			Integer seedInteger = (Integer) m_sessionAttributes.get("seed");
			return seedInteger.intValue();
		}

		public LoginContext getLoginContext() {
			return m_loginContext;
		}
		
	}
	
}
