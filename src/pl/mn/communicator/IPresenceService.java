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
public interface IPresenceService {

	void setStatus(IStatus status) throws GGException;

	IStatus getStatus();
	
	void addMonitoredUser(IUser user);

	void removeMonitoredUser(IUser user);
	
	Collection getMonitoredUsers();
	
	void addUserListener(UserListener userListener);
	void removeUserListener(UserListener userListener);
	
}
