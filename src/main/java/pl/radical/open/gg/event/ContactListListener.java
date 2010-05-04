package pl.radical.open.gg.event;

import pl.radical.open.gg.LocalUser;

import java.util.Collection;
import java.util.EventListener;

/**
 * The listener interface for receiving contact list related events.
 * <p>
 * The class that is interested in processing a contact list event implements this interface. Then the class that
 * implements this interface has to register in <code>ContactListService</code>. When the contact list event occurs the
 * object's (ContactListHandler) methods will be invoked.
 * <p>
 * Created on 2004-12-11
 * 
 * @see pl.radical.open.gg.IContactListService
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public interface ContactListListener extends EventListener {

	/**
	 * Notification that contact list has been successfuly exported to Gadu-Gadu server.
	 */
	void contactListExported();

	/**
	 * Notification that contact list has been successfuly received from Gadu-Gadu server.
	 * 
	 * @param users
	 *            the collection of <code>LocalUser</code> objects.
	 */
	void contactListReceived(Collection<LocalUser> users);

	// void contactListReceivedAsByteArray(byte[] contactList);

	class Stub implements ContactListListener {

		/**
		 * @see pl.radical.open.gg.event.ContactListListener#contactListExported()
		 */
		public void contactListExported() {
		}

		/**
		 * @see pl.radical.open.gg.event.ContactListListener#contactListReceived(Collection)
		 */
		public void contactListReceived(final Collection<LocalUser> users) {
		}

		// public void contactListReceivedAsByteArray(byte[] contactList) { }

	}

}
