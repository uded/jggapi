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
import java.util.HashSet;
import java.util.Iterator;

import pl.mn.communicator.event.ContactListListener;
import pl.mn.communicator.packet.out.GGUserListRequest;

/**
 * The default implementation of <code>IContactListService</code>.
 * <p>
 * Created on 2004-12-11
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: DefaultContactListService.java,v 1.2 2005-11-19 19:56:57 winnetou25 Exp $
 */
public class DefaultContactListService implements IContactListService {

	private HashSet m_contactListListeners = null;
	
	private Session m_session = null;
	
	//friendly
	DefaultContactListService(Session session) {
		if (session == null) throw new NullPointerException("session cannot be null");
		m_session = session;
		m_contactListListeners = new HashSet();
	}
	
	/**
	 * @see pl.mn.communicator.IContactListService#clearContactList()
	 */
	public void clearContactList() throws GGException {
		checkSessionState();
		try {
			GGUserListRequest clearContactListRequest = GGUserListRequest.createClearUserListRequest();
			m_session.getSessionAccessor().sendPackage(clearContactListRequest);
		} catch (IOException ex) {
			throw new GGException("Unable to clear contact list", ex);
		}
	}
	
	/**
	 * @see pl.mn.communicator.IContactListService#exportContactList(java.util.Collection)
	 */
	public void exportContactList(Collection localUsers) throws GGException {
		checkSessionState();
		try {
			GGUserListRequest putUserListRequest = GGUserListRequest.createPutUserListRequest(localUsers);
			m_session.getSessionAccessor().sendPackage(putUserListRequest);
		} catch (IOException ex) {
			throw new GGException("Unable to export contact list", ex);
		}
	}

	/**
	 * @see pl.mn.communicator.IContactListService#importContactList()
	 */
	public void importContactList() throws GGException {
		checkSessionState();
		try {
			GGUserListRequest getContactListRequest = GGUserListRequest.createGetUserListRequest();
			m_session.getSessionAccessor().sendPackage(getContactListRequest);
		} catch (IOException ex) {
			throw new GGException("Unable to import contact list", ex);
		}
	}
	
	private void checkSessionState() throws GGSessionException {
		if (m_session.getSessionState() != SessionState.LOGGED_IN) {
			throw new GGSessionException(m_session.getSessionState());
		}
	}

	/**
	 * @see pl.mn.communicator.IContactListService#addContactListListener(pl.mn.communicator.event.ContactListListener)
	 */
	public void addContactListListener(ContactListListener contactListListener) {
		if (contactListListener == null) throw new NullPointerException("contactListListener cannot be null");
		m_contactListListeners.add(contactListListener);
	}

	/**
	 * @see pl.mn.communicator.IContactListService#removeContactListlistener(pl.mn.communicator.event.ContactListListener)
	 */
	public void removeContactListlistener(ContactListListener contactListListener) {
		if (contactListListener == null) throw new NullPointerException("conractListListener cannot be null");
		m_contactListListeners.add(contactListListener);
	}
	
	//TODO clone the listeners list beforing notifing
	protected void notifyContactListExported() {
		for (Iterator it = m_contactListListeners.iterator(); it.hasNext();) {
			ContactListListener contactListListener = (ContactListListener) it.next();
			contactListListener.contactListExported();
		}
	}

	//TODO clone the listeners list beforing notifing
	protected void notifyContactListReceived(Collection users) {
		for (Iterator it = m_contactListListeners.iterator(); it.hasNext();) {
			ContactListListener contactListListener = (ContactListListener) it.next();
			contactListListener.contactListReceived(users);
		}
	}

}
