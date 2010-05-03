package pl.radical.open.gg;

import pl.radical.open.gg.event.LoginFailedEvent;
import pl.radical.open.gg.event.SessionStateListener;
import pl.radical.open.gg.packet.GGIncomingPackage;
import pl.radical.open.gg.packet.GGOutgoingPackage;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created on 2004-11-28
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class Session implements ISession {

	private SessionState m_sessionState = SessionState.CONNECTION_AWAITING;

	private SessionAccessor sessionAccessor = null;
	private Set<SessionStateListener> sessionStateListeners = null;
	private Map<String, Integer> sessionAttributes = null;
	private IGGConfiguration configuration = new GGConfiguration();

	private DefaultConnectionService connectionService = null;
	private DefaultLoginService loginService = null;
	private DefaultPresenceService presenceService = null;
	private DefaultMessageService messageService = null;
	private DefaultRegistrationService registrationService = null;
	private DefaultContactListService contactListService = null;
	private DefaultPublicDirectoryService publicDirectoryService = null;

	private final Map<String, Object> proxies = new HashMap<String, Object>();

	public Session(final IGGConfiguration configuration) throws GGException {
		this();
		if (configuration == null) {
			throw new IllegalArgumentException("configuration cannot be null");
		}
		this.configuration = configuration;
	}

	public Session() throws GGException {
		sessionAccessor = new SessionAccessor();
		sessionAttributes = new HashMap<String, Integer>();
		sessionStateListeners = new HashSet<SessionStateListener>();
		connectionService = new DefaultConnectionService(this);
		loginService = new DefaultLoginService(this);
		messageService = new DefaultMessageService(this);
		presenceService = new DefaultPresenceService(this);
		contactListService = new DefaultContactListService(this);
		publicDirectoryService = new DefaultPublicDirectoryService(this);
		registrationService = new DefaultRegistrationService(this);
	}

	public SessionState getSessionState() {
		return m_sessionState;
	}

	/**
	 * @see pl.radical.open.gg.ISession#getConfiguration()
	 */
	public IGGConfiguration getGGConfiguration() {
		return configuration;
	}

	public void addSessionStateListener(final SessionStateListener sessionStateListener) {
		if (sessionStateListener == null) {
			throw new IllegalArgumentException("sessionStateListener cannot be null.");
		}
		sessionStateListeners.add(sessionStateListener);
	}

	public void removeSessionStateListener(final SessionStateListener sessionStateListener) {
		if (sessionStateListener == null) {
			throw new IllegalArgumentException("sessionStateListener cannot be null");
		}
		sessionStateListeners.remove(sessionStateListener);
	}

	/**
	 * @see pl.radical.open.gg.ISession#getConnectionService()
	 */
	public IConnectionService getConnectionService() {
		if (!proxies.containsKey(IConnectionService.class.getName())) {
			final ClassLoader classLoader = Session.class.getClassLoader();
			final SessionInvocationHandler invocationHandler = new SessionInvocationHandler(connectionService);
			final IConnectionService connectionServiceProxy = (IConnectionService) Proxy.newProxyInstance(classLoader, new Class[] {
					IConnectionService.class
			}, invocationHandler);
			proxies.put(IConnectionService.class.getName(), connectionServiceProxy);
		}
		return (IConnectionService) proxies.get(IConnectionService.class.getName());
	}

	/**
	 * @see pl.radical.open.gg.ISession#getLoginService()
	 */
	public ILoginService getLoginService() {
		if (!proxies.containsKey(ILoginService.class.getName())) {
			final ClassLoader classLoader = Session.class.getClassLoader();
			final SessionInvocationHandler invocationHandler = new SessionInvocationHandler(loginService);
			final ILoginService loginServiceProxy = (ILoginService) Proxy.newProxyInstance(classLoader, new Class[] {
					ILoginService.class
			}, invocationHandler);
			proxies.put(ILoginService.class.getName(), loginServiceProxy);
		}
		return (ILoginService) proxies.get(ILoginService.class.getName());
	}

	/**
	 * @see pl.radical.open.gg.ISession#getMessageService()
	 */
	public IMessageService getMessageService() {
		if (!proxies.containsKey(IMessageService.class.getName())) {
			final ClassLoader classLoader = Session.class.getClassLoader();
			final SessionInvocationHandler invocationHandler = new SessionInvocationHandler(messageService);
			final IMessageService messageServiceProxy = (IMessageService) Proxy.newProxyInstance(classLoader, new Class[] {
					IMessageService.class
			}, invocationHandler);
			proxies.put(IMessageService.class.getName(), messageServiceProxy);
		}
		return (IMessageService) proxies.get(IMessageService.class.getName());
	}

	/**
	 * @see pl.radical.open.gg.ISession#getPresenceService()
	 */
	public IPresenceService getPresenceService() {
		if (!proxies.containsKey(IPresenceService.class.getName())) {
			final ClassLoader classLoader = Session.class.getClassLoader();
			final SessionInvocationHandler invocationHandler = new SessionInvocationHandler(presenceService);
			final IPresenceService presenceServiceProxy = (IPresenceService) Proxy.newProxyInstance(classLoader, new Class[] {
					IPresenceService.class
			}, invocationHandler);
			proxies.put(IPresenceService.class.getName(), presenceServiceProxy);
		}
		return (IPresenceService) proxies.get(IPresenceService.class.getName());
	}

	/**
	 * @see pl.radical.open.gg.ISession#getPublicDirectoryService()
	 */
	public IPublicDirectoryService getPublicDirectoryService() {
		if (!proxies.containsKey(IPublicDirectoryService.class.getName())) {
			final ClassLoader classLoader = Session.class.getClassLoader();
			final SessionInvocationHandler invocationHandler = new SessionInvocationHandler(publicDirectoryService);
			final IPublicDirectoryService publicDirectoryServiceProxy = (IPublicDirectoryService) Proxy
			.newProxyInstance(classLoader, new Class[] {
					IPublicDirectoryService.class
			}, invocationHandler);
			proxies.put(IPublicDirectoryService.class.getName(), publicDirectoryServiceProxy);
		}
		return (IPublicDirectoryService) proxies.get(IPublicDirectoryService.class.getName());
	}

	/**
	 * @see pl.radical.open.gg.ISession#getContactListService()
	 */
	public IContactListService getContactListService() {
		if (!proxies.containsKey(IContactListService.class.getName())) {
			final ClassLoader classLoader = Session.class.getClassLoader();
			final SessionInvocationHandler invocationHandler = new SessionInvocationHandler(contactListService);
			final IContactListService contactListServiceProxy = (IContactListService) Proxy.newProxyInstance(classLoader, new Class[] {
					IContactListService.class
			}, invocationHandler);
			proxies.put(IContactListService.class.getName(), contactListServiceProxy);
		}
		return (IContactListService) proxies.get(IContactListService.class.getName());
	}

	/**
	 * @see pl.radical.open.gg.ISession#getRegistrationService()
	 */
	public IRegistrationService getRegistrationService() {
		if (!proxies.containsKey(IRegistrationService.class.getName())) {
			final ClassLoader classLoader = Session.class.getClassLoader();
			final SessionInvocationHandler invocationHandler = new SessionInvocationHandler(registrationService);
			final IRegistrationService registrationServiceProxy = (IRegistrationService) Proxy.newProxyInstance(classLoader, new Class[] {
					IRegistrationService.class
			}, invocationHandler);
			proxies.put(IRegistrationService.class.getName(), registrationServiceProxy);
		}
		return (IRegistrationService) proxies.get(IRegistrationService.class.getName());
	}

	protected void notifySessionStateChanged(final SessionState oldState, final SessionState newState) {
		if (oldState == null) {
			throw new IllegalArgumentException("oldState cannot be null");
		}
		if (newState == null) {
			throw new IllegalArgumentException("newState cannot be null");
		}

		for (final SessionStateListener sessionStateListener : sessionStateListeners) {
			if (oldState != newState) {
				sessionStateListener.sessionStateChanged(oldState, newState);
			}
		}
	}

	// TODO restrict accesss by AspectJ friendly
	public SessionAccessor getSessionAccessor() {
		return sessionAccessor;
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
			connectionService.sendPackage(outgoingPackage);
		}

		public void notifyConnectionEstablished() throws GGException {
			connectionService.notifyConnectionEstablished();
		}

		public void notifyConnectionClosed() throws GGException {
			connectionService.notifyConnectionClosed();
		}

		public void notifyConnectionError(final Exception exception) throws GGException {
			connectionService.notifyConnectionError(exception);
		}

		public void notifyPongReceived() {
			connectionService.notifyPongReceived();
		}

		public void notifyLoginOK() throws GGException {
			loginService.notifyLoginOK();
		}

		public void notifyLoggedOut() throws GGException {
			loginService.notifyLoggedOut();
		}

		public void notifyLoginFailed(final LoginFailedEvent loginFailedEvent) throws GGException {
			loginService.notifyLoginFailed(loginFailedEvent);
		}

		public void notifyUserChangedStatus(final IUser user, final IRemoteStatus newStatus) throws GGException {
			presenceService.notifyUserChangedStatus(user, newStatus);
		}

		public void notifyMessageArrived(final IIncommingMessage incommingMessage) {
			messageService.notifyMessageArrived(incommingMessage);
		}

		public void notifyMessageDelivered(final int uin, final int messageID, final MessageStatus messageStatus) {
			messageService.notifyMessageDelivered(uin, messageID, messageStatus);
		}

		public void notifyGGPacketReceived(final GGIncomingPackage incomingPackage) {
			connectionService.notifyPacketReceived(incomingPackage);
		}

		public void notifyContactListExported() {
			contactListService.notifyContactListExported();
		}

		public void notifyContactListReceived(final Collection<LocalUser> contacts) {
			contactListService.notifyContactListReceived(contacts);
		}

		public void notifyPubdirRead(final int queryID, final PersonalInfo publicDirInfo) {
			publicDirectoryService.notifyPubdirRead(queryID, publicDirInfo);
		}

		public void notifyPubdirUpdated(final int queryID) {
			publicDirectoryService.notifyPubdirUpdated(queryID);
		}

		public void notifyPubdirGotSearchResults(final int queryID, final PublicDirSearchReply searchReply) {
			publicDirectoryService.notifyPubdirGotSearchResults(queryID, searchReply);
		}

		public void setLoginSeed(final int seed) {
			sessionAttributes.put("seed", Integer.valueOf(seed));
		}

		public int getLoginSeed() {
			if (!sessionAttributes.containsKey("seed")) {
				return -1;
			}
			final Integer seedInteger = sessionAttributes.get("seed");
			return seedInteger.intValue();
		}

		public void disconnect() throws GGException {
			connectionService.disconnect();
		}

	}

	private static final class SessionInvocationHandler implements InvocationHandler {

		private Object m_delegate = null;

		private SessionInvocationHandler(final Object delegate) {
			if (delegate == null) {
				throw new IllegalArgumentException("delegate cannot be null.");
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
