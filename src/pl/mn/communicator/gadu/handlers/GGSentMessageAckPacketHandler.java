/*
 * Created on 2004-11-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pl.mn.communicator.gadu.handlers;

import pl.mn.communicator.IUser;
import pl.mn.communicator.MessageDeliveredEvent;
import pl.mn.communicator.User;
import pl.mn.communicator.gadu.GGSendMsgAck;

/**
 * @author mateusz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GGSentMessageAckPacketHandler implements PacketHandler {

	/**
	 * @see pl.mn.communicator.gadu.handlers.PacketHandler#handle(pl.mn.communicator.gadu.handlers.Context)
	 */
	public void handle(Context context) {
		GGSendMsgAck sendMessageAck = new GGSendMsgAck(context.getPackageContent());
		context.getSessionAccessor().notifyGGPacketReceived(sendMessageAck);
		IUser user = new User(sendMessageAck.getRecipientUin());
		MessageDeliveredEvent messageDeliveredEvent = new MessageDeliveredEvent(this, user, sendMessageAck.getMessageSeq(), sendMessageAck.getStatus());
		context.getSessionAccessor().notifyMessageDelivered(messageDeliveredEvent);
	}

}
