package pl.radical.open.gg.packet.handlers;

import pl.radical.open.gg.GGException;
import pl.radical.open.gg.IIncommingMessage;
import pl.radical.open.gg.IncomingMessage;
import pl.radical.open.gg.packet.GGUtils;
import pl.radical.open.gg.packet.in.GGRecvMsg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on 2004-11-28
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class GGMessageReceivedPacketHandler implements PacketHandler {

	private static final Logger logger = LoggerFactory.getLogger(GGMessageReceivedPacketHandler.class);

	/**
	 * @see pl.radical.open.gg.packet.handlers.PacketHandler#handle(pl.radical.open.gg.packet.handlers.Context)
	 */
	public void handle(final PacketContext context) throws GGException {
		if (logger.isDebugEnabled()) {
			logger.debug("GGMessage packet received.");
			logger.debug("PacketHeader: " + context.getHeader());
			logger.debug("PacketBody: " + GGUtils.prettyBytesToString(context.getPackageContent()));
		}

		final GGRecvMsg recvMsg = new GGRecvMsg(context.getPackageContent());
		context.getSessionAccessor().notifyGGPacketReceived(recvMsg);
		final IIncommingMessage incommingMessage = new IncomingMessage(recvMsg.getSenderUin(), recvMsg.getMessage(), recvMsg
		        .getMessageSeq(), recvMsg.getTime(), recvMsg.getMsgClass());
		context.getSessionAccessor().notifyMessageArrived(incommingMessage);
	}

}
