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
public interface IPublicDirectoryService {

	/**
	 * Looks up for users matching certain criteria.
	 * 
	 * @param searchContext the information provided to look for users.
	 * @return the collection of users that matched the query.
	 */
	Collection search(SearchContext searchContext);
	
}
