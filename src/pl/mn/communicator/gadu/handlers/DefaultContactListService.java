/*
 * Created on 2004-12-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pl.mn.communicator.gadu.handlers;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import pl.mn.communicator.GGException;
import pl.mn.communicator.GGSessionException;
import pl.mn.communicator.IContactListService;
import pl.mn.communicator.SessionState;
import pl.mn.communicator.event.ContactListListener;
import pl.mn.communicator.gadu.GGUserListRequest;

/**
 * @author mateusz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DefaultContactListService implements IContactListService {

	private HashSet m_contactListListeners = null;
	
	private Session m_session = null;
	
	public DefaultContactListService(Session session) {
		if (session == null) throw new NullPointerException("session cannot be null");
		m_session = session;
		m_contactListListeners = new HashSet();
	}
	
	/**
	 * @see pl.mn.communicator.IContactListService#clearUserListRequest()
	 */
	public void clearUserListRequest() throws GGException {
		try {
			if (m_session.getSessionState() == SessionState.AUTHENTICATED) {
				GGUserListRequest clearContactListRequest = GGUserListRequest.createClearUsetListRequest();
				m_session.getSessionAccessor().sendPackage(clearContactListRequest);
			} else {
				throw new GGSessionException("Invalid session state: "+SessionState.getState(m_session.getSessionState()));
			}
		} catch (IOException ex) {
			throw new GGException("Unable to clear contact list", ex);
		}
	}
	
	/**
	 * @see pl.mn.communicator.IContactListService#exportContacts(java.util.Collection)
	 */
	public void exportContacts(Collection localUsers) throws GGException {
		try {
			if (m_session.getSessionState() == SessionState.AUTHENTICATED) {
				GGUserListRequest putUserListRequest = GGUserListRequest.createPutUserListRequest(localUsers);
				m_session.getSessionAccessor().sendPackage(putUserListRequest);
			} else {
				throw new GGSessionException("Invalid session state: "+SessionState.getState(m_session.getSessionState()));
			}
		} catch (IOException ex) {
			throw new GGException("Unable to export contact list", ex);
		}
	}

	/**
	 * @see pl.mn.communicator.IContactListService#importContacts()
	 */
	public void importContacts() throws GGException {
		try {
			if (m_session.getSessionState() == SessionState.AUTHENTICATED) {
				GGUserListRequest getContactListRequest = GGUserListRequest.createGetUserListRequest();
				m_session.getSessionAccessor().sendPackage(getContactListRequest);
			}
		} catch (IOException ex) {
			throw new GGException("Unable to import contact list", ex);
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
	
	protected void notifyContactListExported() {
		for (Iterator it = m_contactListListeners.iterator(); it.hasNext();) {
			ContactListListener contactListListener = (ContactListListener) it.next();
			contactListListener.contactListExported();
		}
	}

	protected void notifyContactListReceived(Collection users) {
		for (Iterator it = m_contactListListeners.iterator(); it.hasNext();) {
			ContactListListener contactListListener = (ContactListListener) it.next();
			contactListListener.contactListReceived(users);
		}
	}

}
