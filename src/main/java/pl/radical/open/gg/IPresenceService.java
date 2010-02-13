package pl.radical.open.gg;

import pl.radical.open.gg.event.UserListener;

import java.util.Collection;

/**
 * This service should be used it there is a need to set a new status.
 * <p>
 * Also through this service the client can add user to be monitored of changing it's status. It can also remove user
 * from being monitored and the client will no longer be notified if user changes it's status.
 * <p>
 * In addition, by using this interface the client can block the user and in this case it will no longer receive any
 * kind of notification from it including new messages.
 * <p>
 * Created on 2004-11-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public interface IPresenceService {

	/**
	 * Invocation of this method tries to set a new status of the user that is currently logged in.
	 * 
	 * @param localStatus
	 *            the new status to be set.
	 * @throws GGException
	 *             if there is an error while setting status.
	 * @throws GGSessionException
	 *             if user is not logged in.
	 * @throws NullPointerException
	 *             if status is null.
	 */
	void setStatus(ILocalStatus localStatus) throws GGException;

	/**
	 * Returns the actual status of the user that is logged in.
	 * 
	 * @return the actual status of the user.
	 */
	ILocalStatus getStatus();

	/**
	 * Adds the user to be monitored.
	 * <p>
	 * After adding the user <code>UserListener</code>'s will receive events related to changing status of the user.
	 * <p>
	 * Note that you can also block the user using this method. After blocking the user you will no longer receive any
	 * messages from him/her.
	 * 
	 * @param user
	 *            that will be monitored or blocked.
	 * @throws GGException
	 *             if there is an error while adding user to be monitored.
	 * @throws GGSessionException
	 *             if we are currently not logged in to the Gadu-Gadu server.
	 * @throws NullPointerException
	 *             if user is null.
	 */
	void addMonitoredUser(IUser user) throws GGException;

	/**
	 * Remove the user that was previously being monitored.
	 * 
	 * @param user
	 *            that we want to unregister from being monitored.
	 * @throws GGException
	 *             if there is an error while removing user from being monitored or if the user is not being monitored.
	 * @throws GGSessionException
	 *             if we are currently not logged in to the Gadu-Gadu server
	 * @throws NullPointerException
	 *             if the user is null.
	 */
	void removeMonitoredUser(IUser user) throws GGException;

	/**
	 * Changes status of the currently monitored user.
	 * 
	 * @param user
	 *            that contains a new status to be changed.
	 * @throws GGException
	 *             if there is an error while changing status of the user or if the user is not being monitored.
	 * @throws GGSessionException
	 *             if we are currently not logged in to the Gadu-Gadu server.
	 */
	void changeMonitoredUserStatus(IUser user) throws GGException;

	/**
	 * Returns collections of the users that are currently being monitored. Note that it is unmodifiable collection
	 * therefore you cannot modify it.
	 * 
	 * @return Collection unmodifiable collection of users that are currently being monitored.
	 */
	Collection<IUser> getMonitoredUsers();

	/**
	 * Adds <code>UserListener</code> to the list of listeners that will be notified of status related events.
	 * 
	 * @param userListener
	 *            the <code>UserListener</code> object that will be notified.
	 * @throws NullPointerException
	 *             if userListener is null.
	 */
	void addUserListener(UserListener userListener);

	/**
	 * Removes <code>UserListener</code> from the list of listeners that will be notified of status related events.
	 * 
	 * @param userListener
	 *            the <code>UserListener</code> object that will no longer be notified.
	 * @throws NullPointerException
	 *             if userListener is null.
	 */
	void removeUserListener(UserListener userListener);

}
