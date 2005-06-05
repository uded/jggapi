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
package pl.mn.communicator;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pl.mn.communicator.event.LoginListener;
import pl.mn.communicator.event.UserListener;
import pl.mn.communicator.packet.out.GGAddNotify;
import pl.mn.communicator.packet.out.GGListEmpty;
import pl.mn.communicator.packet.out.GGNewStatus;
import pl.mn.communicator.packet.out.GGNotify;
import pl.mn.communicator.packet.out.GGOutgoingPackage;
import pl.mn.communicator.packet.out.GGRemoveNotify;

/**
 * The implementation of <code>IPresenceService</code>.
 * <p>
 * Created on 2004-11-28
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: DefaultPresenceService.java,v 1.5 2005-06-05 14:49:19 winnetou25 Exp $
 */
public class DefaultPresenceService implements IPresenceService {

	private final static Log logger = LogFactory.getLog(DefaultPresenceService.class);
	
	/** Set of listeners that will be notified of user related events */
	private Set m_userListeners = Collections.synchronizedSet(new HashSet());
	
	/** The session associated with this service */
	private Session m_session = null;

	/** The actual status */
	private ILocalStatus m_localStatus = new LocalStatus(StatusType.OFFLINE);

	/** The set of users that are monitored */
	private Collection m_monitoredUsers = new HashSet();

	//friendly
	DefaultPresenceService(Session session) {
		if (session == null) throw new NullPointerException("session cannot be null");
		m_session = session;
		m_session.getLoginService().addLoginListener(new LoginHandler());
	}
	
	/**
	 * @see pl.mn.communicator.IPresenceService#setStatus(pl.mn.communicator.ILocalStatus)
	 */
	public void setStatus(ILocalStatus localStatus) throws GGException {
	    if (localStatus == null) throw new NullPointerException("status cannot be null");
	    checkSessionState();
	    try {
	        GGNewStatus newStatus = new GGNewStatus(localStatus);
	        m_session.getSessionAccessor().sendPackage(newStatus);
	        m_localStatus = localStatus;
	        notifyLocalUserChangedStatus(localStatus);
	    } catch (IOException ex) {
			throw new GGException("Unable to set status: "+localStatus, ex);
		}
	}

	/**
	 * @see pl.mn.communicator.IPresenceService#getStatus()
	 */
	public ILocalStatus getStatus() {
		return m_localStatus;
	}
	
	/**
	 * @see pl.mn.communicator.IPresenceService#addMonitoredUser(pl.mn.communicator.IUser)
	 */
	public void addMonitoredUser(IUser user) throws GGException {
		if (user == null) throw new NullPointerException("user cannot be null");
		checkSessionState();
		if (m_monitoredUsers.contains(user)) {
		    return;
		}
		
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
		if (!m_monitoredUsers.contains(user)) {
			throw new GGException("User: "+user+"+ is not monitored");
		}

		try {
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
		if (!m_monitoredUsers.contains(user)) {
			throw new GGException("User: "+user+"+ is not monitored");
		}
		
		try {
			GGRemoveNotify removeNotify = new GGRemoveNotify(user.getUin(), user.getUserMode());
			m_session.getSessionAccessor().sendPackage(removeNotify);
			GGAddNotify addNotify = new GGAddNotify(user.getUin(), user.getUserMode());
			m_session.getSessionAccessor().sendPackage(addNotify);
		} catch (IOException ex) {
			throw new GGException("Unable to remove monitored user", ex);
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
	 * @see pl.mn.communicator.IPresenceService#addUserListener(pl.mn.communicator.event.UserListener)
	 */
	public void addUserListener(UserListener userListener) {
		if (userListener == null) throw new NullPointerException("userListener cannot be null");
		m_userListeners.add(userListener);
	}

	/**
	 * @see pl.mn.communicator.IPresenceService#removeUserListener(pl.mn.communicator.event.UserListener)
	 */
	public void removeUserListener(UserListener userListener) {
		if (userListener == null) throw new NullPointerException("userListener cannot be null");
		m_userListeners.remove(userListener);
	}

	protected void notifyUserChangedStatus(IUser user, IRemoteStatus newStatus) throws GGException {
		if (user == null) throw new NullPointerException("user cannot be null");
		if (newStatus == null) throw new NullPointerException("newStatus cannot be null");
		for (Iterator it = m_userListeners.iterator(); it.hasNext();) {
			UserListener userListener = (UserListener) it.next();
			userListener.userStatusChanged(user, newStatus);
		}
	}

	protected void notifyLocalUserChangedStatus(ILocalStatus localStatus) throws GGException {
		if (localStatus == null) throw new NullPointerException("localStatus cannot be null");
		for (Iterator it = m_userListeners.iterator(); it.hasNext();) {
			UserListener userListener = (UserListener) it.next();
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
		 * @see pl.mn.communicator.event.LoginListener#loginOK()
		 */
	    public void loginOK() throws GGException {
	        m_localStatus = m_session.getLoginService().getLoginContext().getStatus();
	        m_monitoredUsers = m_session.getLoginService().getLoginContext().getMonitoredUsers();
	        setStatus(m_localStatus);
	        GGOutgoingPackage outgoingPackage = null;
	        if (m_monitoredUsers.size() == 0) {
	            outgoingPackage = GGListEmpty.getInstance();
	        } else {
	            IUser[] users = (IUser[]) m_monitoredUsers.toArray(IUser.EMPTY_ARRAY);
	            outgoingPackage = new GGNotify(users);
	        }
	        try {
		        m_session.getSessionAccessor().sendPackage(outgoingPackage);
	        } catch (IOException ex) {
	            throw new GGException("Unable to send initial list of users to monitor", ex);
	        }
	    }
	    
	}
	
}
