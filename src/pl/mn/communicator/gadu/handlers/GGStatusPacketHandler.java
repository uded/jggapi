/*
 * Created on 2004-11-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pl.mn.communicator.gadu.handlers;

import pl.mn.communicator.IStatus;
import pl.mn.communicator.IUser;
import pl.mn.communicator.gadu.GGStatus;

/**
 * @author mateusz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GGStatusPacketHandler implements PacketHandler {

	/**
	 * @see pl.mn.communicator.gadu.handlers.PacketHandler#handle(pl.mn.communicator.gadu.handlers.Context)
	 */
	public void handle(Context context) {
		GGStatus status = new GGStatus(context.getPackageContent());
		context.getSessionAccessor().notifyGGPacketReceived(status);
		IUser user = status.getUser();
		IStatus statusBiz = status.getStatus();
		context.getSessionAccessor().notifyUserChangedStatus(user, statusBiz);
	}

}
