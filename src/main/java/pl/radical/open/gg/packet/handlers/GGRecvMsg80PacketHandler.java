package pl.radical.open.gg.packet.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.radical.open.gg.GGException;
import pl.radical.open.gg.IIncommingMessage;
import pl.radical.open.gg.IncomingMessage;
import pl.radical.open.gg.packet.in.GGRecvMsg80;
import pl.radical.open.gg.utils.GGUtils;

/**
 * 
 * @author Klatkowski
 *
 */
public class GGRecvMsg80PacketHandler implements PacketHandler {

	private static final Logger log = LoggerFactory.getLogger(GGRecvMsg80PacketHandler.class);

	/**
	 * @see pl.mn.communicator.packet.handlers.PacketHandler#handle(pl.mn.communicator.packet.handlers.Context)
	 */
	public void handle(PacketContext context) throws GGException {
		if (log.isDebugEnabled()) {
			log.debug("GGRecvMsg80 packet received.");
			log.debug("PacketHeader: "+context.getHeader());
			log.debug("PacketBody: "+GGUtils.prettyBytesToString(context.getPackageContent()));
		}

		GGRecvMsg80 recvMsg = new GGRecvMsg80(context.getPackageContent());
		context.getSessionAccessor().notifyGGPacketReceived(recvMsg);
		//FIXME only plain message
		IIncommingMessage incommingMessage = new IncomingMessage(recvMsg.getSenderUin(), recvMsg.getPlainMessage(), recvMsg.getMessageSeq(), recvMsg.getTime(), recvMsg.getMsgClass());
		context.getSessionAccessor().notifyMessageArrived(incommingMessage);
	}

}
