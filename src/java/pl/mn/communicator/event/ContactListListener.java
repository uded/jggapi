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
package pl.mn.communicator.event;

import java.util.Collection;
import java.util.EventListener;

/**
 * The listener interface for receiving contact list related events.
 * <p>
 * The class that is interested in processing a contact list event implements
 * this interface. Then the class that implements this interface has to register in
 * <code>ContactListService</code>. When the contact list event occurs
 * the object's (ContactListHandler) methods will be invoked.
 * <p>
 * Created on 2004-12-11
 * 
 * @see pl.mn.communicator.IContactListService
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: ContactListListener.java,v 1.3 2004-12-19 13:42:31 winnetou25 Exp $
 */
public interface ContactListListener extends EventListener {

	/** 
	 * Notification that contact list has been successfuly exported
	 * to Gadu-Gadu server.
	 */
	void contactListExported();
	
	/**
	 * Notification that contact list has been successfuly received
	 * from Gadu-Gadu server.
	 * @param users the collection of <code>LocalUser</code> objects.
	 */
	void contactListReceived(Collection users);

	public static class Stub implements ContactListListener {

		/**
		 * @see pl.mn.communicator.event.ContactListListener#contactListExported()
		 */
		public void contactListExported() { }

		/**
		 * @see pl.mn.communicator.event.ContactListListener#receivedContactList(java.util.Collection)
		 */
		public void contactListReceived(Collection users) { }
		
	}
	
}
