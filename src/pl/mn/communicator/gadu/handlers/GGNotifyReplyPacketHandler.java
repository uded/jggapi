/*
 * Created on 2004-11-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pl.mn.communicator.gadu.handlers;

import java.util.Iterator;
import java.util.Map;

import pl.mn.communicator.IStatus;
import pl.mn.communicator.IUser;
import pl.mn.communicator.gadu.GGNotifyReply;


/**
 * @author mateusz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GGNotifyReplyPacketHandler implements PacketHandler {

	/**
	 * @see pl.mn.communicator.gadu.handlers.PacketHandler#handle(pl.mn.communicator.gadu.handlers.Context)
	 */
	public void handle(Context context) {
		GGNotifyReply notifyReply = new GGNotifyReply(context.getPackageContent());
		context.getSessionAccessor().notifyGGPacketReceived(notifyReply);
		Map usersStatuses = notifyReply.getUsersStatus();
		for (Iterator it = usersStatuses.keySet().iterator();it.hasNext();) {
			IUser user = (IUser) it.next();
			IStatus status = (IStatus) usersStatuses.get(user);
			context.getSessionAccessor().notifyUserChangedStatus(user, status);
		}
	}

}
