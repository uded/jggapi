package pl.radical.open.gg.packet.handlers;

import pl.radical.open.gg.GGException;
import pl.radical.open.gg.MessageStatus;
import pl.radical.open.gg.packet.in.GGSendMsgAck;
import pl.radical.open.gg.utils.GGConversion;
import pl.radical.open.gg.utils.GGUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on 2004-11-28
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class GGSentMessageAckPacketHandler implements PacketHandler {
	private static final Logger LOG = LoggerFactory.getLogger(GGSentMessageAckPacketHandler.class);

	/**
	 * @see pl.radical.open.gg.packet.handlers.PacketHandler#handle(pl.radical.open.gg.packet.handlers.Context)
	 */
	public void handle(final PacketContext context) throws GGException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("GGSentMessageAck packet received.");
			LOG.debug("PacketHeader: " + context.getHeader());
			LOG.debug("PacketBody: " + GGUtils.prettyBytesToString(context.getPackageContent()));
		}

		final GGSendMsgAck sendMessageAck = new GGSendMsgAck(context.getPackageContent());
		context.getSessionAccessor().notifyGGPacketReceived(sendMessageAck);
		final int uin = sendMessageAck.getRecipientUin();
		final int messageID = sendMessageAck.getMessageSeq();
		final MessageStatus messageStatus = GGConversion.getClientMessageStatus(sendMessageAck.getMessageStatus());
		context.getSessionAccessor().notifyMessageDelivered(uin, messageID, messageStatus);
	}

}
