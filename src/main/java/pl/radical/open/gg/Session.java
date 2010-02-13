package pl.radical.open.gg;

import pl.radical.open.gg.event.LoginFailedEvent;
import pl.radical.open.gg.event.SessionStateListener;
import pl.radical.open.gg.packet.in.GGIncomingPackage;
import pl.radical.open.gg.packet.out.GGOutgoingPackage;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created on 2004-11-28
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class Session implements ISession {

	private SessionState m_sessionState = SessionState.CONNECTION_AWAITING;

	private SessionAccessor m_sessionAccessor = null;
	private Set<SessionStateListener> m_sessionStateListeners = null;
	private HashMap<String, Integer> m_sessionAttributes = null;
	private IGGConfiguration m_configuration = new GGConfiguration();

	private DefaultConnectionService m_connectionService = null;
	private DefaultLoginService m_loginService = null;
	private DefaultPresenceService m_presenceService = null;
	private DefaultMessageService m_messageService = null;
	private DefaultRegistrationService m_registrationService = null;
	private DefaultContactListService m_contactListService = null;
	private DefaultPublicDirectoryService m_publicDirectoryService = null;

	private final HashMap<String, Object> m_proxies = new HashMap<String, Object>();

	public Session(final IGGConfiguration configuration) {
		this();
		if (configuration == null) {
			throw new IllegalArgumentException("configuration cannot be null");
		}
		m_configuration = configuration;
	}

	public Session() {
		m_sessionAccessor = new SessionAccessor();
		m_sessionAttributes = new HashMap<String, Integer>();
		m_sessionStateListeners = new HashSet<SessionStateListener>();
		m_connectionService = new DefaultConnectionService(this);
		m_loginService = new DefaultLoginService(this);
		m_messageService = new DefaultMessageService(this);
		m_presenceService = new DefaultPresenceService(this);
		m_contactListService = new DefaultContactListService(this);
		m_publicDirectoryService = new DefaultPublicDirectoryService(this);
		m_registrationService = new DefaultRegistrationService(this);
	}

	public SessionState getSessionState() {
		return m_sessionState;
	}

	/**
	 * @see pl.radical.open.gg.ISession#getConfiguration()
	 */
	public IGGConfiguration getGGConfiguration() {
		return m_configuration;
	}

	public void addSessionStateListener(final SessionStateListener sessionStateListener) {
		if (sessionStateListener == null) {
			throw new NullPointerException("sessionStateListener cannot be null.");
		}
		m_sessionStateListeners.add(sessionStateListener);
	}

	public void removeSessionStateListener(final SessionStateListener sessionStateListener) {
		if (sessionStateListener == null) {
			throw new NullPointerException("sessionStateListener cannot be null");
		}
		m_sessionStateListeners.remove(sessionStateListener);
	}

	/**
	 * @see pl.radical.open.gg.ISession#getConnectionService()
	 */
	public IConnectionService getConnectionService() {
		if (!m_proxies.containsKey(IConnectionService.class.getName())) {
			final ClassLoader classLoader = Session.class.getClassLoader();
			final SessionInvocationHandler invocationHandler = new SessionInvocationHandler(m_connectionService);
			final IConnectionService connectionServiceProxy = (IConnectionService) Proxy.newProxyInstance(classLoader, new Class[] {
					IConnectionService.class
			}, invocationHandler);
			m_proxies.put(IConnectionService.class.getName(), connectionServiceProxy);
		}
		return (IConnectionService) m_proxies.get(IConnectionService.class.getName());
	}

	/**
	 * @see pl.radical.open.gg.ISession#getLoginService()
	 */
	public ILoginService getLoginService() {
		if (!m_proxies.containsKey(ILoginService.class.getName())) {
			final ClassLoader classLoader = Session.class.getClassLoader();
			final SessionInvocationHandler invocationHandler = new SessionInvocationHandler(m_loginService);
			final ILoginService loginServiceProxy = (ILoginService) Proxy.newProxyInstance(classLoader, new Class[] {
					ILoginService.class
			}, invocationHandler);
			m_proxies.put(ILoginService.class.getName(), loginServiceProxy);
		}
		return (ILoginService) m_proxies.get(ILoginService.class.getName());
	}

	/**
	 * @see pl.radical.open.gg.ISession#getMessageService()
	 */
	public IMessageService getMessageService() {
		if (!m_proxies.containsKey(IMessageService.class.getName())) {
			final ClassLoader classLoader = Session.class.getClassLoader();
			final SessionInvocationHandler invocationHandler = new SessionInvocationHandler(m_messageService);
			final IMessageService messageServiceProxy = (IMessageService) Proxy.newProxyInstance(classLoader, new Class[] {
					IMessageService.class
			}, invocationHandler);
			m_proxies.put(IMessageService.class.getName(), messageServiceProxy);
		}
		return (IMessageService) m_proxies.get(IMessageService.class.getName());
	}

	/**
	 * @see pl.radical.open.gg.ISession#getPresenceService()
	 */
	public IPresenceService getPresenceService() {
		if (!m_proxies.containsKey(IPresenceService.class.getName())) {
			final ClassLoader classLoader = Session.class.getClassLoader();
			final SessionInvocationHandler invocationHandler = new SessionInvocationHandler(m_presenceService);
			final IPresenceService presenceServiceProxy = (IPresenceService) Proxy.newProxyInstance(classLoader, new Class[] {
					IPresenceService.class
			}, invocationHandler);
			m_proxies.put(IPresenceService.class.getName(), presenceServiceProxy);
		}
		return (IPresenceService) m_proxies.get(IPresenceService.class.getName());
	}

	/**
	 * @see pl.radical.open.gg.ISession#getPublicDirectoryService()
	 */
	public IPublicDirectoryService getPublicDirectoryService() {
		if (!m_proxies.containsKey(IPublicDirectoryService.class.getName())) {
			final ClassLoader classLoader = Session.class.getClassLoader();
			final SessionInvocationHandler invocationHandler = new SessionInvocationHandler(m_publicDirectoryService);
			final IPublicDirectoryService publicDirectoryServiceProxy = (IPublicDirectoryService) Proxy
			.newProxyInstance(classLoader, new Class[] {
					IPublicDirectoryService.class
			}, invocationHandler);
			m_proxies.put(IPublicDirectoryService.class.getName(), publicDirectoryServiceProxy);
		}
		return (IPublicDirectoryService) m_proxies.get(IPublicDirectoryService.class.getName());
	}

	/**
	 * @see pl.radical.open.gg.ISession#getContactListService()
	 */
	public IContactListService getContactListService() {
		if (!m_proxies.containsKey(IContactListService.class.getName())) {
			final ClassLoader classLoader = Session.class.getClassLoader();
			final SessionInvocationHandler invocationHandler = new SessionInvocationHandler(m_contactListService);
			final IContactListService contactListServiceProxy = (IContactListService) Proxy.newProxyInstance(classLoader, new Class[] {
					IContactListService.class
			}, invocationHandler);
			m_proxies.put(IContactListService.class.getName(), contactListServiceProxy);
		}
		return (IContactListService) m_proxies.get(IContactListService.class.getName());
	}

	/**
	 * @see pl.radical.open.gg.ISession#getRegistrationService()
	 */
	public IRegistrationService getRegistrationService() {
		if (!m_proxies.containsKey(IRegistrationService.class.getName())) {
			final ClassLoader classLoader = Session.class.getClassLoader();
			final SessionInvocationHandler invocationHandler = new SessionInvocationHandler(m_registrationService);
			final IRegistrationService registrationServiceProxy = (IRegistrationService) Proxy.newProxyInstance(classLoader, new Class[] {
					IRegistrationService.class
			}, invocationHandler);
			m_proxies.put(IRegistrationService.class.getName(), registrationServiceProxy);
		}
		return (IRegistrationService) m_proxies.get(IRegistrationService.class.getName());
	}

	protected void notifySessionStateChanged(final SessionState oldState, final SessionState newState) {
		if (oldState == null) {
			throw new NullPointerException("oldState cannot be null");
		}
		if (newState == null) {
			throw new NullPointerException("newState cannot be null");
		}

		for (final SessionStateListener sessionStateListener : m_sessionStateListeners) {
			if (oldState != newState) {
				sessionStateListener.sessionStateChanged(oldState, newState);
			}
		}
	}

	// TODO restrict accesss by AspectJ friendly
	public SessionAccessor getSessionAccessor() {
		return m_sessionAccessor;
	}

	public class SessionAccessor {

		public void setSessionState(final SessionState sessionState) {
			if (m_sessionState != sessionState) {
				final SessionState oldState = m_sessionState;
				m_sessionState = sessionState;
				notifySessionStateChanged(oldState, sessionState);
			}
		}

		public void sendPackage(final GGOutgoingPackage outgoingPackage) throws IOException {
			m_connectionService.sendPackage(outgoingPackage);
		}

		public void notifyConnectionEstablished() throws GGException {
			m_connectionService.notifyConnectionEstablished();
		}

		public void notifyConnectionClosed() throws GGException {
			m_connectionService.notifyConnectionClosed();
		}

		public void notifyConnectionError(final Exception exception) throws GGException {
			m_connectionService.notifyConnectionError(exception);
		}

		public void notifyPongReceived() {
			m_connectionService.notifyPongReceived();
		}

		public void notifyLoginOK() throws GGException {
			m_loginService.notifyLoginOK();
		}

		public void notifyLoggedOut() throws GGException {
			m_loginService.notifyLoggedOut();
		}

		public void notifyLoginFailed(final LoginFailedEvent loginFailedEvent) throws GGException {
			m_loginService.notifyLoginFailed(loginFailedEvent);
		}

		public void notifyUserChangedStatus(final IUser user, final IRemoteStatus newStatus) throws GGException {
			m_presenceService.notifyUserChangedStatus(user, newStatus);
		}

		public void notifyMessageArrived(final IIncommingMessage incommingMessage) {
			m_messageService.notifyMessageArrived(incommingMessage);
		}

		public void notifyMessageDelivered(final int uin, final int messageID, final MessageStatus messageStatus) {
			m_messageService.notifyMessageDelivered(uin, messageID, messageStatus);
		}

		public void notifyGGPacketReceived(final GGIncomingPackage incomingPackage) {
			m_connectionService.notifyPacketReceived(incomingPackage);
		}

		public void notifyContactListExported() {
			m_contactListService.notifyContactListExported();
		}

		public void notifyContactListReceived(final Collection<LocalUser> contacts) {
			m_contactListService.notifyContactListReceived(contacts);
		}

		public void notifyPubdirRead(final int queryID, final PersonalInfo publicDirInfo) {
			m_publicDirectoryService.notifyPubdirRead(queryID, publicDirInfo);
		}

		public void notifyPubdirUpdated(final int queryID) {
			m_publicDirectoryService.notifyPubdirUpdated(queryID);
		}

		public void notifyPubdirGotSearchResults(final int queryID, final PublicDirSearchReply searchReply) {
			m_publicDirectoryService.notifyPubdirGotSearchResults(queryID, searchReply);
		}

		public void setLoginSeed(final int seed) {
			m_sessionAttributes.put("seed", Integer.valueOf(seed));
		}

		public int getLoginSeed() {
			if (!m_sessionAttributes.containsKey("seed")) {
				return -1;
			}
			final Integer seedInteger = m_sessionAttributes.get("seed");
			return seedInteger.intValue();
		}

		public void disconnect() throws GGException {
			m_connectionService.disconnect();
		}

	}

	private final static class SessionInvocationHandler implements InvocationHandler {

		private Object m_delegate = null;

		private SessionInvocationHandler(final Object delegate) {
			if (delegate == null) {
				throw new NullPointerException("delegate cannot be null.");
			}
			m_delegate = delegate;
		}

		/**
		 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method,
		 *      java.lang.Object[])
		 */
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			try {
				return method.invoke(m_delegate, args);
			} catch (final InvocationTargetException ex) {
				throw ex.getTargetException();
			}
		}

	}

}
