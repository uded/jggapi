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
package pl.mn.communicator.packet.handlers;

import java.io.IOException;
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
import pl.mn.communicator.IUser;
import pl.mn.communicator.SessionState;
import pl.mn.communicator.event.LoginListener;
import pl.mn.communicator.event.UserListener;
import pl.mn.communicator.packet.out.GGAddNotify;
import pl.mn.communicator.packet.out.GGListEmpty;
import pl.mn.communicator.packet.out.GGNewStatus;
import pl.mn.communicator.packet.out.GGNotify;
import pl.mn.communicator.packet.out.GGOutgoingPackage;
import pl.mn.communicator.packet.out.GGRemoveNotify;

/**
 * Created on 2004-11-28
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: DefaultPresenceService.java,v 1.2 2004-12-14 19:49:05 winnetou25 Exp $
 */
public class DefaultPresenceService implements IPresenceService {

	private final static Log logger = LogFactory.getLog(DefaultPresenceService.class);
	
	private Set m_userListeners = null;
	private Session m_session = null;
	private IStatus m_localStatus = null;
	private Collection m_monitoredUsers = new HashSet();

	public DefaultPresenceService(Session session) {
		m_session = session;
		m_localStatus = session.getLoginContext().getStatus();
		m_userListeners = new HashSet();
		if (session.getLoginContext().getMonitoredUsers() != null) {
			m_monitoredUsers = session.getLoginContext().getMonitoredUsers();
		}
		m_session.getLoginService().addLoginListener(new LoginHandler());
	}
	
	/**
	 * @see pl.mn.communicator.IPresenceService#setStatus(pl.mn.communicator.IStatus)
	 */
	public void setStatus(IStatus localStatus) throws GGException {
		if (localStatus == null) throw new NullPointerException("status cannot be null");
		checkSessionState();
		try {
			GGNewStatus newStatus = new GGNewStatus(localStatus);
			m_session.getSessionAccessor().sendPackage(newStatus);
			m_localStatus = localStatus;
		} catch (IOException ex) {
			throw new GGException("Unable to set status: "+localStatus, ex);
		}
	}

	/**
	 * @see pl.mn.communicator.IPresenceService#getStatus()
	 */
	public IStatus getStatus() {
		return m_localStatus;
	}
	
	/**
	 * @see pl.mn.communicator.IPresenceService#addMonitoredUser(pl.mn.communicator.IUser)
	 */
	public void addMonitoredUser(IUser user) throws GGException {
		if (user == null) throw new NullPointerException("user cannot be null");
		checkSessionState();
		if (m_monitoredUsers.contains(user)) return;
		try {
			GGAddNotify addNotify = new GGAddNotify(user.getUin(), user.getUserMode());
			m_session.getSessionAccessor().sendPackage(addNotify);
			m_monitoredUsers.add(user);
		} catch (IOException ex) {
			throw new GGException("Error occured while adding user to be monitored.", ex);
		}
	}
	
	/**
	 * @see pl.mn.communicator.IPresenceService#removeMonitoredUser(pl.mn.communicator.IUser)
	 */
	public void removeMonitoredUser(IUser user) throws GGException {
		if (user == null) throw new NullPointerException("user cannot be null");
		checkSessionState();
		try {
			if (!m_monitoredUsers.contains(user)) return;
			GGRemoveNotify removeNotify = new GGRemoveNotify(user.getUin(), user.getUserMode());
			m_session.getSessionAccessor().sendPackage(removeNotify);
			m_monitoredUsers.remove(user);
		} catch (IOException ex) {
			throw new GGException("Unable to remove monitored user", ex);
		}
	}
	
	/**
	 * @see pl.mn.communicator.IPresenceService#changeMonitoredUserStatus(pl.mn.communicator.IUser)
	 */
	public void changeMonitoredUserStatus(IUser user) throws GGException {
		if (user == null) throw new NullPointerException("user cannot be null");
		checkSessionState();
		if (m_monitoredUsers.contains(user)) {
			try {
				GGRemoveNotify removeNotify = new GGRemoveNotify(user.getUin(), user.getUserMode());
				m_session.getSessionAccessor().sendPackage(removeNotify);
				GGAddNotify addNotify = new GGAddNotify(user.getUin(), user.getUserMode());
				m_session.getSessionAccessor().sendPackage(addNotify);
			} catch (IOException ex) {
				throw new GGException("Unable to remove monitored user", ex);
			}
		}
	}
	
	/**
	 * @see pl.mn.communicator.IPresenceService#getMonitoredUsers()
	 */
	public Collection getMonitoredUsers() {
		if (m_session.getSessionState() != SessionState.LOGGED_IN) return Collections.EMPTY_LIST;
		
		if (m_monitoredUsers.size() == 0) {
			return Collections.EMPTY_LIST;
		} else {
			return Collections.unmodifiableCollection(m_monitoredUsers);
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
	
	private void checkSessionState() {
		if (m_session.getSessionState() != SessionState.LOGGED_IN) {
			throw new GGSessionException("Incorrect session state: "+m_session.getSessionState());
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
				if (m_monitoredUsers.size() == 0) {
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
