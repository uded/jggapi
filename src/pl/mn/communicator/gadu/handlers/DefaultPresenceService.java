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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pl.mn.communicator.GGException;
import pl.mn.communicator.GGSessionException;
import pl.mn.communicator.IPresenceService;
import pl.mn.communicator.IStatus;
import pl.mn.communicator.IStatus60;
import pl.mn.communicator.IUser;
import pl.mn.communicator.SessionState;
import pl.mn.communicator.Status60;
import pl.mn.communicator.event.LoginListener;
import pl.mn.communicator.event.UserListener;
import pl.mn.communicator.gadu.GGOutgoingPackage;
import pl.mn.communicator.gadu.out.GGAddNotify;
import pl.mn.communicator.gadu.out.GGListEmpty;
import pl.mn.communicator.gadu.out.GGNewStatus;
import pl.mn.communicator.gadu.out.GGNotify;
import pl.mn.communicator.gadu.out.GGRemoveNotify;

/**
 * Created on 2004-11-28
 * 
 * @author mateusz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DefaultPresenceService implements IPresenceService {

	private final static Log logger = LogFactory.getLog(DefaultPresenceService.class);
	
	private Set m_userListeners = null;
	private Session m_session = null;
	private Status60 m_localStatus = null;
	private Collection m_monitoredUsers = null;

	public DefaultPresenceService(Session session) {
		m_session = session;
		m_localStatus = session.getLoginContext().getStatus();
		m_userListeners = new HashSet();
		m_monitoredUsers = session.getLoginContext().getMonitoredUsers();
		m_session.getLoginService().addLoginListener(new LoginHandler());
	}
	
	/**
	 * @see pl.mn.communicator.IPresenceService#setStatus(pl.mn.communicator.IStatus)
	 */
	public void setStatus(Status60 localStatus) throws GGException {
		if (localStatus == null) throw new NullPointerException("status cannot be null");
		if (m_session.getSessionState() == SessionState.LOGGED_IN) {
			try {
				GGNewStatus newStatus = new GGNewStatus(localStatus);
				m_session.getSessionAccessor().sendPackage(newStatus);
				m_localStatus = localStatus;
			} catch (IOException ex) {
				throw new GGException("Unable to set status: "+localStatus, ex);
			}
		} else {
			throw new GGSessionException("invalid session state: "+m_session.getSessionState());
		}
	}

	/**
	 * @see pl.mn.communicator.IPresenceService#getStatus()
	 */
	public Status60 getStatus() {
		return m_localStatus;
	}
	
	/**
	 * @see pl.mn.communicator.IPresenceService#addMonitoredUser(pl.mn.communicator.IUser)
	 */
	public void addMonitoredUser(IUser user) throws GGException {
		if (user == null) throw new NullPointerException("user cannot be null");
		if (m_session.getSessionState() == SessionState.LOGGED_IN) {
			try {
				GGAddNotify addNotify = new GGAddNotify(user.getUin(), user.getUserMode());
				m_session.getSessionAccessor().sendPackage(addNotify);
				if (m_monitoredUsers == null) m_monitoredUsers = new ArrayList();
				m_monitoredUsers.add(user);
			} catch (IOException ex) {
				throw new GGException("Error occured while adding user to be monitored.", ex);
			}
		}
	}
	
	/**
	 * @see pl.mn.communicator.IPresenceService#removeMonitoredUser(pl.mn.communicator.IUser)
	 */
	public void removeMonitoredUser(IUser user) throws GGException {
		if (m_session.getSessionState() == SessionState.LOGGED_IN) {
			try {
				GGRemoveNotify removeNotify = new GGRemoveNotify(user.getUin(), user.getUserMode());
				m_session.getSessionAccessor().sendPackage(removeNotify);
				m_monitoredUsers.remove(user);
			} catch (IOException ex) {
				throw new GGException("Unable to remove monitored user", ex);
			}
		}
	}
	
	/**
	 * @see pl.mn.communicator.IPresenceService#getMonitoredUsers()
	 */
	public Collection getMonitoredUsers() {
		if (m_session.getSessionState() == SessionState.LOGGED_IN) {
			if (m_monitoredUsers == null) {
				return Collections.EMPTY_LIST;
			} else {
				return Collections.unmodifiableCollection(m_monitoredUsers);
			}
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

	protected void notifyUserChangedStatus60(IUser user, IStatus60 status) {
		if (user == null) throw new NullPointerException("user cannot be null");
		if (status == null) throw new NullPointerException("status cannot be null");
		for (Iterator it = m_userListeners.iterator(); it.hasNext();) {
			UserListener userListener = (UserListener) it.next();
			userListener.userStatus60Changed(user, status);
		}
	}

	private class LoginHandler extends LoginListener.Stub {

		/**
		 * @see pl.mn.communicator.event.LoginListener#loginOK()
		 */
		public void loginOK() {
			try {
				setStatus(m_localStatus);
				GGOutgoingPackage outgoingPackage = null;
				if (m_monitoredUsers == null) {
					outgoingPackage = GGListEmpty.getInstance();
				} else {
					IUser[] users = (IUser[]) m_monitoredUsers.toArray(new IUser[0]);
					outgoingPackage = new GGNotify(users);
				}
				m_session.getSessionAccessor().sendPackage(outgoingPackage);
			} catch (GGException ex) {
				logger.error("Unable to set initial status", ex);
			} catch (IOException ex) {
				logger.error("Unable to send users to notify", ex);
			}
		}

	}
	
}
