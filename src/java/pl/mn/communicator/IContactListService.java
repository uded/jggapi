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
package pl.mn.communicator;

import java.util.Collection;

import pl.mn.communicator.event.ContactListListener;

/**
 * Through this service client can either clear, export or import contact list
 * to the Gadu-Gadu server.
 * <p>
 * Created on 2004-11-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: IContactListService.java,v 1.3 2004-12-19 13:42:31 winnetou25 Exp $
 */
public interface IContactListService {

	/**
	 * Clears user's contact list from the Gadu-Gadu server.
	 * 
	 * @throws GGException if there is an error while exporting contact 
	 */
	void clearContactList() throws GGException;
	
	/**
	 * Exports user's contact list to the Gadu-Gadu server.
	 * 
	 * @param users collection of <code>LocalUser</code> objects.
	 * @throws GGException if where is an error while exporting contact list.
	 */
	void exportContactList(Collection users) throws GGException;

	/**
	 * Imports user's contact list from the Gadu-Gadu server.
	 * 
	 * @throws GGException if there is an error while importing contact list.
	 */
	void importContactList() throws GGException;
	
	/**
	 * Adds <code>ContactListListener</code> to the list of listeners
	 * to be notified of events such as successfuly importing or 
	 * exporting contact list.
	 * 
	 * @param contactListListener the listener to be notified of contact list events.
	 */
	void addContactListListener(ContactListListener contactListListener);
	
	/**
	 * Removes <code>ContactListListener</code> from the list of listeners
	 * that are notified of events such as successfuly importing or exporting
	 * contact list.
	 * 
	 * @param contactListListener the listener object to be removed from contact list events notification.
	 */
	void removeContactListlistener(ContactListListener contactListListener);
	
}
