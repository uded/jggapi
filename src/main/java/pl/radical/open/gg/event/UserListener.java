package pl.radical.open.gg.event;

import pl.radical.open.gg.GGException;
import pl.radical.open.gg.ILocalStatus;
import pl.radical.open.gg.IRemoteStatus;
import pl.radical.open.gg.IUser;

import java.util.EventListener;

/**
 * The listener interface that is for classes that want to be notified of user statuses events.
 * <p>
 * If the user changes status the class that implements this interface, so called UserHandler is notified.
 * <p>
 * Created on 2004-11-28
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public interface UserListener extends EventListener {

	void localStatusChanged(ILocalStatus localStatus) throws GGException;

	/**
	 * The notification that the user changed the status.
	 * 
	 * @param user
	 *            the Gadu-Gadu user that changed the status.
	 * @param newStatus
	 *            the new status of the user.
	 */
	void userStatusChanged(IUser user, IRemoteStatus newStatus) throws GGException;

	class Stub implements UserListener {

		public void localStatusChanged(final ILocalStatus localStatus) throws GGException {
		}

		public void userStatusChanged(final IUser user, final IRemoteStatus newStatus) throws GGException {
		}

	}

}
