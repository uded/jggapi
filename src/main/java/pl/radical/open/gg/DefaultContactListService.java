package pl.radical.open.gg;

import pl.radical.open.gg.event.ContactListListener;
import pl.radical.open.gg.event.GGPacketListener;
import pl.radical.open.gg.packet.GGUtils;
import pl.radical.open.gg.packet.in.GGIncomingPackage;
import pl.radical.open.gg.packet.in.GGUserListReply;
import pl.radical.open.gg.packet.out.GGOutgoingPackage;
import pl.radical.open.gg.packet.out.GGUserListRequest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * The default implementation of <code>IContactListService</code>.
 * <p>
 * Created on 2004-12-11
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class DefaultContactListService implements IContactListService {

	private final static Logger LOGGER = Logger.getLogger(DefaultContactListService.class);

	private HashSet<ContactListListener> m_contactListListeners = null;

	private Session m_session = null;

	// friendly
	DefaultContactListService(final Session session) {
		if (session == null) {
			throw new GGNullPointerException("session cannot be null");
		}
		m_session = session;
		m_contactListListeners = new HashSet<ContactListListener>();
	}

	/**
	 * @see pl.radical.open.gg.IContactListService#clearContactList()
	 */
	public void clearContactList() throws GGException {
		checkSessionState();
		try {
			final GGUserListRequest clearContactListRequest = GGUserListRequest.createClearUserListRequest();
			m_session.getSessionAccessor().sendPackage(clearContactListRequest);
		} catch (final IOException ex) {
			throw new GGException("Unable to clear contact list", ex);
		}
	}

	/**
	 * @see pl.radical.open.gg.IContactListService#exportContactList(java.util.Collection)
	 */
	public void exportContactList(final Collection<LocalUser> localUsers) throws GGException {
		LOGGER.debug("Exporting contact list users...");
		checkSessionState();
		try {
			final List<GGUserListRequest> packageList = createExportContactListPackageList(localUsers);

			final ContactListSenderThread contactListSenderThread = new ContactListSenderThread(packageList);
			contactListSenderThread.start();
		} catch (final IOException ex) {
			throw new GGException("Unable to export contact list", ex);
		}
	}

	private List<GGUserListRequest> createExportContactListPackageList(final Collection<LocalUser> localUsers) throws IOException {
		final String allUsers = GGUserListRequest.prepareRequest(localUsers);
		final List<String> lines = GGUserListReply.getLinesStringList(allUsers.getBytes());

		return exportContactListAsLines(lines);
	}

	private List<GGUserListRequest> exportContactListAsLines(final List<String> lines) {
		final List<GGUserListRequest> packageList = new ArrayList<GGUserListRequest>();

		final ArrayList<String> tempLines = new ArrayList<String>();
		int counter = 0;
		for (int i = 0; i < lines.size(); i++) {
			final String line = lines.get(i);
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
			// Change first package to GG_USER_LIST_PUT
			final GGUserListRequest userListRequest = packageList.get(0);
			GGUserListRequest.changeRequestType(userListRequest, GGUserListRequest.GG_USER_LIST_PUT);
		}

		return packageList;
	}

	/**
	 * @see pl.radical.open.gg.IContactListService#importContactList()
	 */
	public void importContactList() throws GGException {
		checkSessionState();
		try {
			final GGUserListRequest getContactListRequest = GGUserListRequest.createGetUserListRequest();
			m_session.getSessionAccessor().sendPackage(getContactListRequest);
		} catch (final IOException ex) {
			throw new GGException("Unable to import contact list", ex);
		}
	}

	public void exportContactList(final InputStream is) throws GGException {
		final ByteArrayOutputStream bos = new ByteArrayOutputStream();

		try {
			final int byteCount = GGUtils.copy(is, bos);
			if (byteCount == 0) {
				return;
			}
			final byte[] data = bos.toByteArray();
			final List<String> lines = GGUserListReply.getLinesStringList(data);
			final List<GGUserListRequest> packageList = exportContactListAsLines(lines);

			final ContactListSenderThread contactListSenderThread = new ContactListSenderThread(packageList);
			contactListSenderThread.start();

		} catch (final IOException ex) {
			throw new GGException("Unable to export contact list.", ex);
		}
	}

	private void checkSessionState() throws GGSessionException {
		if (m_session.getSessionState() != SessionState.LOGGED_IN) {
			throw new GGSessionException(m_session.getSessionState());
		}
	}

	/**
	 * @see pl.radical.open.gg.IContactListService#addContactListListener(pl.radical.open.gg.event.ContactListListener)
	 */
	public void addContactListListener(final ContactListListener contactListListener) {
		if (contactListListener == null) {
			throw new GGNullPointerException("contactListListener cannot be null");
		}
		m_contactListListeners.add(contactListListener);
	}

	/**
	 * @see pl.radical.open.gg.IContactListService#removeContactListlistener(pl.radical.open.gg.event.ContactListListener)
	 */
	public void removeContactListlistener(final ContactListListener contactListListener) {
		if (contactListListener == null) {
			throw new GGNullPointerException("conractListListener cannot be null");
		}
		m_contactListListeners.add(contactListListener);
	}

	// TODO clone the listeners list before notifing
	protected void notifyContactListExported() {
		for (final Object element : m_contactListListeners) {
			final ContactListListener contactListListener = (ContactListListener) element;
			contactListListener.contactListExported();
		}
	}

	// TODO clone the listeners list before notifing
	protected void notifyContactListReceived(final Collection<LocalUser> users) {
		for (final Object element : m_contactListListeners) {
			final ContactListListener contactListListener = (ContactListListener) element;
			contactListListener.contactListReceived(users);
		}
	}

	private class ContactListSenderThread extends Thread implements GGPacketListener {

		private List<GGUserListRequest> m_packagesToSend = null;

		private boolean isRunning = true;

		private ContactListSenderThread(final List<GGUserListRequest> packagesToSend) {
			if (packagesToSend == null) {
				throw new IllegalArgumentException("packagesToSend cannot be null");
			}
			m_packagesToSend = packagesToSend;
		}

		@Override
		public void run() {
			startUp();
			while (!isRunning) {
				try {
					Thread.sleep(1000);
				} catch (final InterruptedException ex) {
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
			final GGOutgoingPackage outgoingPackage = m_packagesToSend.remove(0);
			try {
				m_session.getSessionAccessor().sendPackage(outgoingPackage);
			} catch (final IOException ex) {
				LOGGER.warn("Unable to send contact list packet", ex);
			}
		}

		public void terminate() {
			LOGGER.debug("ContactListSenderThread: terminating...");
			isRunning = false;
			m_session.getConnectionService().removePacketListener(this);
		}

		public void sentPacket(final GGOutgoingPackage outgoingPacket) {
		}

		public void receivedPacket(final GGIncomingPackage incomingPacket) {
			if (!(incomingPacket instanceof GGUserListReply)) {
				return;
			}
			if (m_packagesToSend.isEmpty()) {
				LOGGER.debug("ContactListSenderThread: Nothing more to send.");
				terminate();
				return;
			}

			final GGOutgoingPackage outgoingPackage = m_packagesToSend.remove(0);
			try {
				LOGGER.debug("ContactListSenderThread: Sending outgoing package...");
				m_session.getSessionAccessor().sendPackage(outgoingPackage);
			} catch (final IOException ex) {
				LOGGER.warn("Unable to send contact list packet", ex);
				terminate();
			}
		}

	}

}
