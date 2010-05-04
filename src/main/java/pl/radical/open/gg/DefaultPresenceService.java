package pl.radical.open.gg;

import pl.radical.open.gg.event.LoginListener;
import pl.radical.open.gg.event.UserListener;
import pl.radical.open.gg.packet.GGOutgoingPackage;
import pl.radical.open.gg.packet.dicts.SessionState;
import pl.radical.open.gg.packet.dicts.StatusType;
import pl.radical.open.gg.packet.out.GGAddNotify;
import pl.radical.open.gg.packet.out.GGListEmpty;
import pl.radical.open.gg.packet.out.GGNewStatus;
import pl.radical.open.gg.packet.out.GGNotify;
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

	/**
	 * Set of listeners that will be notified of user related events
	 */
	private final Set<UserListener> userListeners = new HashSet<UserListener>();

	/**
	 * The session associated with this service
	 */
	private Session session = null;

	/**
	 * The actual status
	 */
	private ILocalStatus localStatus = new LocalStatus(StatusType.OFFLINE);

	/**
	 * The set of users that are monitored
	 */
	private Collection<IUser> monitoredUsers = new HashSet<IUser>();

	// friendly
	DefaultPresenceService(final Session session) {
		if (session == null) {
			throw new IllegalArgumentException("session cannot be null");
		}
		this.session = session;
		session.getLoginService().addLoginListener(new LoginHandler());
	}

	/**
	 * @see pl.radical.open.gg.IPresenceService#setStatus(pl.radical.open.gg.ILocalStatus)
	 */
	public void setStatus(final ILocalStatus localStatus) throws GGException {
		if (localStatus == null) {
			throw new IllegalArgumentException("status cannot be null");
		}
		checkSessionState();
		try {
			final GGNewStatus newStatus = new GGNewStatus(localStatus);
			session.getSessionAccessor().sendPackage(newStatus);
			this.localStatus = localStatus;
			notifyLocalUserChangedStatus(this.localStatus);
		} catch (final IOException ex) {
			throw new GGException("Unable to set status: " + localStatus, ex);
		}
	}

	/**
	 * @see pl.radical.open.gg.IPresenceService#getStatus()
	 */
	public ILocalStatus getStatus() {
		return localStatus;
	}

	/**
	 * @see pl.radical.open.gg.IPresenceService#addMonitoredUser(pl.radical.open.gg.IUser)
	 */
	public void addMonitoredUser(final IUser user) throws GGException {
		if (user == null) {
			throw new IllegalArgumentException("user cannot be null");
		}
		checkSessionState();
		if (monitoredUsers.contains(user)) {
			return;
		}

		try {
			final GGAddNotify addNotify = new GGAddNotify(user.getUin(), user.getUserMode());
			session.getSessionAccessor().sendPackage(addNotify);
			monitoredUsers.add(user);
		} catch (final IOException ex) {
			throw new GGException("Error occured while adding user to be monitored.", ex);
		}
	}

	/**
	 * @see pl.radical.open.gg.IPresenceService#removeMonitoredUser(pl.radical.open.gg.IUser)
	 */
	public void removeMonitoredUser(final IUser user) throws GGException {
		if (user == null) {
			throw new IllegalArgumentException("user cannot be null");
		}
		checkSessionState();
		if (!monitoredUsers.contains(user)) {
			throw new GGException("User: " + user + "+ is not monitored");
		}

		try {
			final GGRemoveNotify removeNotify = new GGRemoveNotify(user.getUin(), user.getUserMode());
			session.getSessionAccessor().sendPackage(removeNotify);
			monitoredUsers.remove(user);
		} catch (final IOException ex) {
			throw new GGException("Unable to remove monitored user", ex);
		}
	}

	/**
	 * @see pl.radical.open.gg.IPresenceService#changeMonitoredUserStatus(pl.radical.open.gg.IUser)
	 */
	public void changeMonitoredUserStatus(final IUser user) throws GGException {
		if (user == null) {
			throw new IllegalArgumentException("user cannot be null");
		}
		checkSessionState();
		if (!monitoredUsers.contains(user)) {
			throw new GGException("User: " + user + "+ is not monitored");
		}

		try {
			final GGRemoveNotify removeNotify = new GGRemoveNotify(user.getUin(), user.getUserMode());
			session.getSessionAccessor().sendPackage(removeNotify);
			final GGAddNotify addNotify = new GGAddNotify(user.getUin(), user.getUserMode());
			session.getSessionAccessor().sendPackage(addNotify);
		} catch (final IOException ex) {
			throw new GGException("Unable to remove monitored user", ex);
		}
	}

	/**
	 * @see pl.radical.open.gg.IPresenceService#getMonitoredUsers()
	 */
	public Collection<IUser> getMonitoredUsers() {
		if (session.getSessionState() != SessionState.LOGGED_IN) {
			return Collections.emptySet();
		}

		if (monitoredUsers.size() == 0) {
			return Collections.emptySet();
		} else {
			return Collections.unmodifiableCollection(monitoredUsers);
		}
	}

	/**
	 * @see pl.radical.open.gg.IPresenceService#addUserListener(pl.radical.open.gg.event.UserListener)
	 */
	public void addUserListener(final UserListener userListener) {
		if (userListener == null) {
			throw new IllegalArgumentException("userListener cannot be null");
		}
		userListeners.add(userListener);
	}

	/**
	 * @see pl.radical.open.gg.IPresenceService#removeUserListener(pl.radical.open.gg.event.UserListener)
	 */
	public void removeUserListener(final UserListener userListener) {
		if (userListener == null) {
			throw new IllegalArgumentException("userListener cannot be null");
		}
		userListeners.remove(userListener);
	}

	protected void notifyUserChangedStatus(final IUser user, final IRemoteStatus newStatus) throws GGException {
		if (user == null) {
			throw new IllegalArgumentException("user cannot be null");
		}
		if (newStatus == null) {
			throw new IllegalArgumentException("newStatus cannot be null");
		}
		for (final Object element : userListeners) {
			final UserListener userListener = (UserListener) element;
			userListener.userStatusChanged(user, newStatus);
		}
	}

	protected void notifyLocalUserChangedStatus(final ILocalStatus localStatus) throws GGException {
		if (localStatus == null) {
			throw new IllegalArgumentException("localStatus cannot be null");
		}
		for (final Object element : userListeners) {
			final UserListener userListener = (UserListener) element;
			userListener.localStatusChanged(localStatus);
		}
	}

	private void checkSessionState() throws GGSessionException {
		if (session.getSessionState() != SessionState.LOGGED_IN) {
			throw new GGSessionException(session.getSessionState());
		}
	}

	private class LoginHandler extends LoginListener.Stub {

		/**
		 * @see pl.radical.open.gg.event.LoginListener#loginOK()
		 */
		@Override
		public void loginOK() throws GGException {
			localStatus = session.getLoginService().getLoginContext().getStatus();
			monitoredUsers = session.getLoginService().getLoginContext().getMonitoredUsers();
			setStatus(localStatus);
			GGOutgoingPackage outgoingPackage;
			if (monitoredUsers.isEmpty()) {
				outgoingPackage = GGListEmpty.getInstance();
			} else {
				final IUser[] users = monitoredUsers.toArray(IUser.EMPTY_ARRAY);
				outgoingPackage = new GGNotify(users);
			}
			try {
				session.getSessionAccessor().sendPackage(outgoingPackage);
			} catch (final IOException ex) {
				throw new GGException("Unable to send initial list of users to monitor", ex);
			}
		}

	}

}
