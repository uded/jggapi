/*
 * Created on 2004-11-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pl.mn.communicator;

import java.util.Collection;

/**
 * @author mateusz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface IContactListService {

	/**
	 * Exports the collection of local users to server.
	 * @param localUsers 
	 */
	void exportContacts(Collection localUsers);

	/**
	 * Imports the collection of localUsers from server.
	 * @return
	 */
	Collection importContacts();
	
}
