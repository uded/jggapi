/*
 * Created on 2004-12-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pl.mn.communicator.gadu.handlers;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pl.mn.communicator.gadu.GGUserListReply;
import pl.mn.communicator.gadu.GGUtils;

/**
 * @author mateusz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GGUserListReplyHandler implements PacketHandler {

	private final static Log logger = LogFactory.getLog(GGUserListReplyHandler.class);
	
	/**
	 * @see pl.mn.communicator.gadu.handlers.PacketHandler#handle(pl.mn.communicator.gadu.handlers.Context)
	 */
	public void handle(Context context) {
		logger.debug("PacketHeader: "+context.getHeader());
		logger.debug("Got packet: "+GGUtils.bytesToString(context.getPackageContent()));

		GGUserListReply userListReply = new GGUserListReply(context.getPackageContent());
		context.getSessionAccessor().notifyGGPacketReceived(userListReply);

		if (userListReply.isPutReply()) {
			logger.debug("GGUserListReply.PutReply");
			context.getSessionAccessor().notifyContactListExported();
		} else if (userListReply.isGetMoreReply()) {
			logger.debug("GGUserListReply.GetMoreReply");
			Collection contactList = userListReply.getContactList();
			context.getSessionAccessor().notifyContactListReceived(contactList);
		} else if (userListReply.isGetReply()) {
			logger.debug("GGUserListReply.GetReply");
			Collection contactList = userListReply.getContactList();
			context.getSessionAccessor().notifyContactListReceived(contactList);
		}
	}

}
