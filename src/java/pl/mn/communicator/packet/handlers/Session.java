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

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
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
import pl.mn.communicator.IPublicDirectoryService;
import pl.mn.communicator.IServer;
import pl.mn.communicator.ISession;
import pl.mn.communicator.IStatus;
import pl.mn.communicator.IUser;
import pl.mn.communicator.IncomingMessage;
import pl.mn.communicator.LoginContext;
import pl.mn.communicator.MessageStatus;
import pl.mn.communicator.PersonalInfo;
import pl.mn.communicator.PublicDirSearchReply;
import pl.mn.communicator.SessionState;
import pl.mn.communicator.event.SessionStateListener;
import pl.mn.communicator.packet.in.GGIncomingPackage;
import pl.mn.communicator.packet.out.GGOutgoingPackage;

/**
 * Created on 2004-11-28
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: Session.java,v 1.17 2004-12-19 21:19:58 winnetou25 Exp $
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
	//private DefaultRegistrationService m_registrationService = null;
	private DefaultContactListService m_contactListService = null;
	private DefaultPublicDirectoryService m_publicDirectoryService = null;
	
	private HashMap m_proxies = new HashMap();
	
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
		m_publicDirectoryService = new DefaultPublicDirectoryService(this);
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
		if (!m_proxies.containsKey(IConnectionService.class.getName())) {
			ClassLoader classLoader = Session.class.getClassLoader();
			SessionInvocationHandler invocationHandler = new SessionInvocationHandler(m_connectionService);
			IConnectionService connectionServiceProxy = (IConnectionService) Proxy.newProxyInstance(classLoader, new Class[]{IConnectionService.class}, invocationHandler);
			m_proxies.put(IConnectionService.class.getName(), connectionServiceProxy);
		}
		return (IConnectionService) m_proxies.get(IConnectionService.class.getName());
	}

	/**
	 * @see pl.mn.communicator.ISession#getLoginService()
	 */
	public ILoginService getLoginService() {
		if (!m_proxies.containsKey(ILoginService.class.getName())) {
			ClassLoader classLoader = Session.class.getClassLoader();
			SessionInvocationHandler invocationHandler = new SessionInvocationHandler(m_loginService);
			ILoginService loginServiceProxy = (ILoginService) Proxy.newProxyInstance(classLoader, new Class[]{ILoginService.class}, invocationHandler);
			m_proxies.put(ILoginService.class.getName(), loginServiceProxy);
		}
		return (ILoginService) m_proxies.get(ILoginService.class.getName());
	}

	/**
	 * @see pl.mn.communicator.ISession#getMessageService()
	 */
	public IMessageService getMessageService() {
		if (!m_proxies.containsKey(IMessageService.class.getName())) {
			ClassLoader classLoader = Session.class.getClassLoader();
			SessionInvocationHandler invocationHandler = new SessionInvocationHandler(m_messageService);
			IMessageService messageServiceProxy = (IMessageService) Proxy.newProxyInstance(classLoader, new Class[]{IMessageService.class}, invocationHandler);
			m_proxies.put(IMessageService.class.getName(), messageServiceProxy);
		}
		return (IMessageService) m_proxies.get(IMessageService.class.getName());
	}

	/**
	 * @see pl.mn.communicator.ISession#getPresenceService()
	 */
	public IPresenceService getPresenceService() {
		if (!m_proxies.containsKey(IPresenceService.class.getName())) {
			ClassLoader classLoader = Session.class.getClassLoader();
			SessionInvocationHandler invocationHandler = new SessionInvocationHandler(m_presenceService);
			IPresenceService presenceServiceProxy = (IPresenceService) Proxy.newProxyInstance(classLoader, new Class[]{IPresenceService.class}, invocationHandler);
			m_proxies.put(IPresenceService.class.getName(), presenceServiceProxy);
		}
		return (IPresenceService) m_proxies.get(IPresenceService.class.getName());
	}
	
	/**
	 * @see pl.mn.communicator.ISession#getPublicDirectoryService()
	 */
	public IPublicDirectoryService getPublicDirectoryService() {
		if (!m_proxies.containsKey(IPublicDirectoryService.class.getName())) {
			ClassLoader classLoader = Session.class.getClassLoader();
			SessionInvocationHandler invocationHandler = new SessionInvocationHandler(m_publicDirectoryService);
			IPublicDirectoryService publicDirectoryServiceProxy = (IPublicDirectoryService) Proxy.newProxyInstance(classLoader, new Class[]{IPublicDirectoryService.class}, invocationHandler);
			m_proxies.put(IPublicDirectoryService.class.getName(), publicDirectoryServiceProxy);
		}
		return (IPublicDirectoryService) m_proxies.get(IPublicDirectoryService.class.getName());
	}

	/**
	 * @see pl.mn.communicator.ISession#getContactListService()
	 */
	public IContactListService getContactListService() {
		if (!m_proxies.containsKey(IContactListService.class.getName())) {
			ClassLoader classLoader = Session.class.getClassLoader();
			SessionInvocationHandler invocationHandler = new SessionInvocationHandler(m_contactListService);
			IContactListService contactListServiceProxy = (IContactListService) Proxy.newProxyInstance(classLoader, new Class[]{IContactListService.class}, invocationHandler);
			m_proxies.put(IContactListService.class.getName(), contactListServiceProxy);
		}
		return (IContactListService) m_proxies.get(IContactListService.class.getName());
	}
	
	protected void notifySessionStateChanged(SessionState oldState, SessionState newState) {
		if (oldState == null) throw new NullPointerException("oldState cannot be null");
		if (newState == null) throw new NullPointerException("newState cannot be null");
		
		for (Iterator it = m_sessionStateListeners.iterator(); it.hasNext();) {
			SessionStateListener sessionStateListener = (SessionStateListener) it.next();
			if (oldState != newState) {
				sessionStateListener.sessionStateChanged(oldState, newState);
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

		public void notifyUserChangedStatus(IUser user, IStatus status) {
			m_presenceService.notifyUserChangedStatus(user, status);
		}

		public void notifyMessageArrived(IncomingMessage incommingMessage) {
			m_messageService.notifyMessageArrived(incommingMessage);
		}

		public void notifyMessageDelivered(int uin, int messageID, MessageStatus messageStatus) {
			m_messageService.notifyMessageDelivered(uin, messageID, messageStatus);
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
		
		public void notifyPubdirRead(int queryID, PersonalInfo publicDirInfo) {
			m_publicDirectoryService.notifyPubdirRead(queryID, publicDirInfo);
		}

		public void notifyPubdirUpdated(int queryID) {
			m_publicDirectoryService.notifyPubdirUpdated(queryID);
		}

		public void notifyPubdirGotSearchResults(int queryID, PublicDirSearchReply searchReply) {
			m_publicDirectoryService.notifyPubdirGotSearchResults(queryID, searchReply);
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

	private final static class SessionInvocationHandler implements InvocationHandler {

		private Object m_delegate = null;
		
		private SessionInvocationHandler(Object delegate) {
			if(delegate == null) throw new NullPointerException( "delegate cannot be null.");
			m_delegate = delegate;
		}
		
		/**
		 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
		 */
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
	        try {
	            return method.invoke(m_delegate, args);
	        } catch(final InvocationTargetException ex) {
	            throw ex.getTargetException();
	        }
		}
		
	}
	
}
