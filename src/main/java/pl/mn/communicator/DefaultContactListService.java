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

import java.beans.ExceptionListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pl.mn.communicator.event.ContactListListener;
import pl.mn.communicator.event.GGPacketListener;
import pl.mn.communicator.packet.GGUtils;
import pl.mn.communicator.packet.in.GGIncomingPackage;
import pl.mn.communicator.packet.in.GGUserListReply;
import pl.mn.communicator.packet.out.GGOutgoingPackage;
import pl.mn.communicator.packet.out.GGUserListRequest;

/**
 * The default implementation of <code>IContactListService</code>.
 * <p>
 * Created on 2004-12-11
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: DefaultContactListService.java,v 1.3 2005-11-20 16:07:22 winnetou25 Exp $
 */
public class DefaultContactListService implements IContactListService {
	
	private final static Log LOGGER = LogFactory.getLog(DefaultContactListService.class);
	
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
			final GGUserListRequest clearContactListRequest = GGUserListRequest.createClearUserListRequest();
			m_session.getSessionAccessor().sendPackage(clearContactListRequest);
		} catch (IOException ex) {
			throw new GGException("Unable to clear contact list", ex);
		}
	}
	
	/**
	 * @see pl.mn.communicator.IContactListService#exportContactList(java.util.Collection)
	 */
	public void exportContactList(final Collection localUsers) throws GGException {
		LOGGER.debug("Exporting contact list users...");
		checkSessionState();
		try {
			final List packageList = createExportContactListPackageList(localUsers);
			
			final ContactListSenderThread contactListSenderThread = new ContactListSenderThread(packageList);
			contactListSenderThread.start();
		} catch (IOException ex) {
			throw new GGException("Unable to export contact list", ex);
		}
	}
	
	private List createExportContactListPackageList(final Collection localUsers) throws IOException {
		final String allUsers = GGUserListRequest.prepareRequest(localUsers);
		final List lines = GGUserListReply.getLinesStringList(allUsers.getBytes());
		
		return exportContactListAsLines(lines);
	}
	
	private List exportContactListAsLines(final List lines) {
		final List packageList = new ArrayList();

		final ArrayList tempLines = new ArrayList();
		int counter = 0;
		for(int i=0; i<lines.size(); i++) {
			final String line = (String) lines.get(i);
			tempLines.add(line);
			counter += line.getBytes().length;
			if (counter >= 1500) {
				final GGUserListRequest putRequest = GGUserListRequest.createPutMoreUserListRequest(tempLines);
				packageList.add(putRequest);
				tempLines.clear();
				counter = 0;
			}
		}

		if (!packageList.isEmpty()) { 
			//Change first package to GG_USER_LIST_PUT
			GGUserListRequest userListRequest = (GGUserListRequest) packageList.get(0);
			GGUserListRequest.changeRequestType(userListRequest, GGUserListRequest.GG_USER_LIST_PUT);
		}
		
		return packageList;
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
	
	public void exportContactList(final InputStream is) throws GGException {
		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		
		try {
			int byteCount = GGUtils.copy(is, bos);
			if (byteCount == 0) {
				return;
			}
			final byte[] data = bos.toByteArray();
			final List lines = GGUserListReply.getLinesStringList(data);
			final List packageList = exportContactListAsLines(lines);
			
			final ContactListSenderThread contactListSenderThread = new ContactListSenderThread(packageList);
			contactListSenderThread.start();
			
		} catch (IOException ex) {
			throw new GGException("Unable to export contact list.", ex);
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
	
	//TODO clone the listeners list before notifing
	protected void notifyContactListExported() {
		for (Iterator it = m_contactListListeners.iterator(); it.hasNext();) {
			ContactListListener contactListListener = (ContactListListener) it.next();
			contactListListener.contactListExported();
		}
	}
	
	//TODO clone the listeners list before notifing
	protected void notifyContactListReceived(Collection users) {
		for (Iterator it = m_contactListListeners.iterator(); it.hasNext();) {
			ContactListListener contactListListener = (ContactListListener) it.next();
			contactListListener.contactListReceived(users);
		}
	}
	
	private class ContactListSenderThread extends Thread implements GGPacketListener {
		
		private List m_packagesToSend = null;
		
		private boolean isRunning = true;
		
		private ContactListSenderThread(final List packagesToSend) {
			if (packagesToSend == null) throw new IllegalArgumentException("packagesToSend cannot be null");
			m_packagesToSend = packagesToSend;
		}
		
		public void run() {
			startUp();
			while(!isRunning) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
					LOGGER.debug("ContactListSenderThread: thread interrupted.");
					terminate();
				}
			}
		}
		
		public void startUp() {
			m_session.getConnectionService().addPacketListener(this);
			if (m_packagesToSend.isEmpty()) {
				return;
			}
			final GGOutgoingPackage outgoingPackage = (GGOutgoingPackage) m_packagesToSend.remove(0);
			try {
				m_session.getSessionAccessor().sendPackage(outgoingPackage);
			} catch (IOException ex) {
				LOGGER.warn("Unable to send contact list packet", ex);
			}
		}
		
		public void terminate() {
			LOGGER.debug("ContactListSenderThread: terminating...");
			isRunning = false;
			m_session.getConnectionService().removePacketListener(this);
		}
		
		public void sentPacket(GGOutgoingPackage outgoingPacket) { }
		
		public void receivedPacket(final GGIncomingPackage incomingPacket) {
			if (!(incomingPacket instanceof GGUserListReply)) {
				return;
			}
			if (m_packagesToSend.isEmpty()) {
				LOGGER.debug("ContactListSenderThread: Nothing more to send.");
				terminate();
				return;
			}
			
			final GGOutgoingPackage outgoingPackage = (GGOutgoingPackage) m_packagesToSend.remove(0);
			try {
				LOGGER.debug("ContactListSenderThread: Sending outgoing package...");
				m_session.getSessionAccessor().sendPackage(outgoingPackage);
			} catch(final IOException ex) {
				LOGGER.warn("Unable to send contact list packet", ex);
				terminate();
			}
		}
		
	}
	
}
