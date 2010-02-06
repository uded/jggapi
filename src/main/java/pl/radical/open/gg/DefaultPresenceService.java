package pl.radical.open.gg;

import pl.radical.open.gg.event.LoginListener;
import pl.radical.open.gg.event.UserListener;
import pl.radical.open.gg.packet.out.GGAddNotify;
import pl.radical.open.gg.packet.out.GGListEmpty;
import pl.radical.open.gg.packet.out.GGNewStatus;
import pl.radical.open.gg.packet.out.GGNotify;
import pl.radical.open.gg.packet.out.GGOutgoingPackage;
import pl.radical.open.gg.packet.out.GGRemoveNotify;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * The implementation of <code>IPresenceService</code>.
 * <p>
 * Created on 2004-11-28
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class DefaultPresenceService implements IPresenceService {

	/** Set of listeners that will be notified of user related events */
	private final Set<UserListener> m_userListeners = new HashSet<UserListener>();

	/** The session associated with this service */
	private Session m_session = null;

	/** The actual status */
	private ILocalStatus m_localStatus = new LocalStatus(StatusType.OFFLINE);

	/** The set of users that are monitored */
	private Collection<IUser> m_monitoredUsers = new HashSet<IUser>();

	// friendly
	DefaultPresenceService(final Session session) {
		if (session == null) {
			throw new NullPointerException("session cannot be null");
		}
		m_session = session;
		m_session.getLoginService().addLoginListener(new LoginHandler());
	}

	/**
	 * @see pl.radical.open.gg.IPresenceService#setStatus(pl.radical.open.gg.ILocalStatus)
	 */
	public void setStatus(final ILocalStatus localStatus) throws GGException {
		if (localStatus == null) {
			throw new NullPointerException("status cannot be null");
		}
		checkSessionState();
		try {
			final GGNewStatus newStatus = new GGNewStatus(localStatus);
			m_session.getSessionAccessor().sendPackage(newStatus);
			m_localStatus = localStatus;
			notifyLocalUserChangedStatus(localStatus);
		} catch (final IOException ex) {
			throw new GGException("Unable to set status: " + localStatus, ex);
		}
	}

	/**
	 * @see pl.radical.open.gg.IPresenceService#getStatus()
	 */
	public ILocalStatus getStatus() {
		return m_localStatus;
	}

	/**
	 * @see pl.radical.open.gg.IPresenceService#addMonitoredUser(pl.radical.open.gg.IUser)
	 */
	public void addMonitoredUser(final IUser user) throws GGException {
		if (user == null) {
			throw new NullPointerException("user cannot be null");
		}
		checkSessionState();
		if (m_monitoredUsers.contains(user)) {
			return;
		}

		try {
			final GGAddNotify addNotify = new GGAddNotify(user.getUin(), user.getUserMode());
			m_session.getSessionAccessor().sendPackage(addNotify);
			m_monitoredUsers.add(user);
		} catch (final IOException ex) {
			throw new GGException("Error occured while adding user to be monitored.", ex);
		}
	}

	/**
	 * @see pl.radical.open.gg.IPresenceService#removeMonitoredUser(pl.radical.open.gg.IUser)
	 */
	public void removeMonitoredUser(final IUser user) throws GGException {
		if (user == null) {
			throw new NullPointerException("user cannot be null");
		}
		checkSessionState();
		if (!m_monitoredUsers.contains(user)) {
			throw new GGException("User: " + user + "+ is not monitored");
		}

		try {
			final GGRemoveNotify removeNotify = new GGRemoveNotify(user.getUin(), user.getUserMode());
			m_session.getSessionAccessor().sendPackage(removeNotify);
			m_monitoredUsers.remove(user);
		} catch (final IOException ex) {
			throw new GGException("Unable to remove monitored user", ex);
		}
	}

	/**
	 * @see pl.radical.open.gg.IPresenceService#changeMonitoredUserStatus(pl.radical.open.gg.IUser)
	 */
	public void changeMonitoredUserStatus(final IUser user) throws GGException {
		if (user == null) {
			throw new NullPointerException("user cannot be null");
		}
		checkSessionState();
		if (!m_monitoredUsers.contains(user)) {
			throw new GGException("User: " + user + "+ is not monitored");
		}

		try {
			final GGRemoveNotify removeNotify = new GGRemoveNotify(user.getUin(), user.getUserMode());
			m_session.getSessionAccessor().sendPackage(removeNotify);
			final GGAddNotify addNotify = new GGAddNotify(user.getUin(), user.getUserMode());
			m_session.getSessionAccessor().sendPackage(addNotify);
		} catch (final IOException ex) {
			throw new GGException("Unable to remove monitored user", ex);
		}
	}

	/**
	 * @see pl.radical.open.gg.IPresenceService#getMonitoredUsers()
	 */
	public Collection<IUser> getMonitoredUsers() {
		if (m_session.getSessionState() != SessionState.LOGGED_IN) {
			return Collections.emptySet();
		}

		if (m_monitoredUsers.size() == 0) {
			return Collections.emptySet();
		} else {
			return Collections.unmodifiableCollection(m_monitoredUsers);
		}
	}

	/**
	 * @see pl.radical.open.gg.IPresenceService#addUserListener(pl.radical.open.gg.event.UserListener)
	 */
	public void addUserListener(final UserListener userListener) {
		if (userListener == null) {
			throw new NullPointerException("userListener cannot be null");
		}
		m_userListeners.add(userListener);
	}

	/**
	 * @see pl.radical.open.gg.IPresenceService#removeUserListener(pl.radical.open.gg.event.UserListener)
	 */
	public void removeUserListener(final UserListener userListener) {
		if (userListener == null) {
			throw new NullPointerException("userListener cannot be null");
		}
		m_userListeners.remove(userListener);
	}

	protected void notifyUserChangedStatus(final IUser user, final IRemoteStatus newStatus) throws GGException {
		if (user == null) {
			throw new NullPointerException("user cannot be null");
		}
		if (newStatus == null) {
			throw new NullPointerException("newStatus cannot be null");
		}
		for (final Object element : m_userListeners) {
			final UserListener userListener = (UserListener) element;
			userListener.userStatusChanged(user, newStatus);
		}
	}

	protected void notifyLocalUserChangedStatus(final ILocalStatus localStatus) throws GGException {
		if (localStatus == null) {
			throw new NullPointerException("localStatus cannot be null");
		}
		for (final Object element : m_userListeners) {
			final UserListener userListener = (UserListener) element;
			userListener.localStatusChanged(localStatus);
		}
	}

	private void checkSessionState() throws GGSessionException {
		if (m_session.getSessionState() != SessionState.LOGGED_IN) {
			throw new GGSessionException(m_session.getSessionState());
		}
	}

	private class LoginHandler extends LoginListener.Stub {

		/**
		 * @see pl.radical.open.gg.event.LoginListener#loginOK()
		 */
		@Override
		public void loginOK() throws GGException {
			m_localStatus = m_session.getLoginService().getLoginContext().getStatus();
			m_monitoredUsers = m_session.getLoginService().getLoginContext().getMonitoredUsers();
			setStatus(m_localStatus);
			GGOutgoingPackage outgoingPackage = null;
			if (m_monitoredUsers.size() == 0) {
				outgoingPackage = GGListEmpty.getInstance();
			} else {
				final IUser[] users = m_monitoredUsers.toArray(IUser.EMPTY_ARRAY);
				outgoingPackage = new GGNotify(users);
			}
			try {
				m_session.getSessionAccessor().sendPackage(outgoingPackage);
			} catch (final IOException ex) {
				throw new GGException("Unable to send initial list of users to monitor", ex);
			}
		}

	}

}
