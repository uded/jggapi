/*
 * Created on 2004-11-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pl.mn.communicator.gadu.handlers;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import pl.mn.communicator.GGException;
import pl.mn.communicator.GGSessionException;
import pl.mn.communicator.IPresenceService;
import pl.mn.communicator.IStatus;
import pl.mn.communicator.IUser;
import pl.mn.communicator.SessionState;
import pl.mn.communicator.UserListener;
import pl.mn.communicator.gadu.GGAddNotify;
import pl.mn.communicator.gadu.GGNewStatus;
import pl.mn.communicator.gadu.GGRemoveNotify;

/**
 * @author mateusz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DefaultPresenceService implements IPresenceService {

	private Set m_userListeners = null;
	private Session m_session = null;
	private IStatus m_status = null;
	private Set m_monitoredUsers = null;

	//TODO STATUS is null at the beginning
	public DefaultPresenceService(Session session) {
		m_session = session;
		m_userListeners = new HashSet();
		m_monitoredUsers = new HashSet();
	}
	
	/**
	 * @see pl.mn.communicator.IPresenceService#setStatus(pl.mn.communicator.IStatus)
	 */
	public void setStatus(IStatus status) throws GGException {
		if (status == null) throw new NullPointerException("status cannot be null");
		if (m_session.getSessionState() == SessionState.AUTHENTICATED) {
			try {
				GGNewStatus newStatus = new GGNewStatus(status);
				m_session.getSessionAccessor().sendPackage(newStatus);
				m_status = status;
			} catch (IOException ex) {
				throw new GGException("Unable to set status: "+status, ex);
			}
		} else {
			throw new GGSessionException("invalid session state: "+SessionState.getState(m_session.getSessionState()));
		}
	}

	/**
	 * @see pl.mn.communicator.IPresenceService#getStatus()
	 */
	public IStatus getStatus() {
		return m_status;
	}
	
	/**
	 * @see pl.mn.communicator.IPresenceService#addMonitoredUser(pl.mn.communicator.IUser)
	 */
	public void addMonitoredUser(IUser user) {
		if (user == null) throw new NullPointerException("user cannot be null");
		if (m_session.getSessionState() == SessionState.AUTHENTICATED) {
			GGAddNotify addNotify = new GGAddNotify(user.getNumber());
			m_session.getSessionAccessor().sendPackage(addNotify);
			m_monitoredUsers.add(user);
		}
	}
	
	/**
	 * @see pl.mn.communicator.IPresenceService#removeMonitoredUser(pl.mn.communicator.IUser)
	 */
	public void removeMonitoredUser(IUser user) {
		if (m_session.getSessionState() == SessionState.AUTHENTICATED) {
			GGRemoveNotify removeNotify = new GGRemoveNotify(user.getNumber());
			m_session.getSessionAccessor().sendPackage(removeNotify);
			m_monitoredUsers.remove(user);
		}
	}
	
	/**
	 * @see pl.mn.communicator.IPresenceService#getMonitoredUsers()
	 */
	public Collection getMonitoredUsers() {
		if (m_session.getSessionState() == SessionState.AUTHENTICATED) {
			return Collections.unmodifiableCollection(m_monitoredUsers);
		} else {
			return Collections.EMPTY_LIST;
		}
	}

	/**
	 * @see pl.mn.communicator.IPresenceService#addUserListener(pl.mn.communicator.UserListener)
	 */
	public void addUserListener(UserListener userListener) {
		if (userListener == null) throw new NullPointerException("userListener cannot be null");
		m_userListeners.add(userListener);
	}

	/**
	 * @see pl.mn.communicator.IPresenceService#removeUserListener(pl.mn.communicator.UserListener)
	 */
	public void removeUserListener(UserListener userListener) {
		if (userListener == null) throw new NullPointerException("userListener cannot be null");
		m_userListeners.remove(userListener);
	}
	
	protected void notifyUserChangedStatus(IUser user, IStatus status) {
		if (user == null) throw new NullPointerException("user cannot be null");
		if (status == null) throw new NullPointerException("status cannot be null");
		for (Iterator it = m_userListeners.iterator(); it.hasNext();) {
			UserListener userListener = (UserListener) it.next();
			userListener.userStatusChanged(user, status);
		}
	}
	
}
