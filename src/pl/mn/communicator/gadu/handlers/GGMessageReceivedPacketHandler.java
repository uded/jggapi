/*
 * Created on 2004-11-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pl.mn.communicator.gadu.handlers;

import pl.mn.communicator.IncommingMessage;
import pl.mn.communicator.MessageArrivedEvent;
import pl.mn.communicator.gadu.GGRecvMsg;

/**
 * @author mateusz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GGMessageReceivedPacketHandler implements PacketHandler {

	/**
	 * @see pl.mn.communicator.gadu.handlers.PacketHandler#handle(pl.mn.communicator.gadu.handlers.Context)
	 */
	public void handle(Context context) {
		GGRecvMsg recvMsg = new GGRecvMsg(context.getPackageContent());
		context.getSessionAccessor().notifyGGPacketReceived(recvMsg);
		IncommingMessage incommingMessage = new IncommingMessage(recvMsg.getSender(), recvMsg.getMessage(), recvMsg.getMessageSeq(), recvMsg.getTime(), recvMsg.getMsgClass());
		MessageArrivedEvent messageArrivedEvent = new MessageArrivedEvent(this, incommingMessage);
		context.getSessionAccessor().notifyMessageArrived(messageArrivedEvent);
	}

}
