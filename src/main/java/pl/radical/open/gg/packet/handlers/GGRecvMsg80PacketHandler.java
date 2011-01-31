package pl.radical.open.gg.packet.handlers;

import pl.radical.open.gg.GGException;
import pl.radical.open.gg.IIncommingMessage;
import pl.radical.open.gg.IncomingMessage;
import pl.radical.open.gg.packet.in.GGRecvMsg80;
import pl.radical.open.gg.utils.GGUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on 2010-03-18
 * 
 * @author <a href="mailto:klacia.85@gmail.com">Kamil Klatkowski</a>
 * @since 1.7.0
 */

public class GGRecvMsg80PacketHandler implements PacketHandler {

	private static final Logger LOG = LoggerFactory.getLogger(GGRecvMsg80PacketHandler.class);

	/**
	 * @see pl.mn.communicator.packet.handlers.PacketHandler#handle(pl.mn.communicator.packet.handlers.Context)
	 */
	@Override
	public void handle(final PacketContext context) throws GGException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("GGRecvMsg80 packet received.");
			LOG.debug("PacketHeader: " + context.getHeader());
			LOG.debug("PacketBody: " + GGUtils.prettyBytesToString(context.getPackageContent()));
		}

		final GGRecvMsg80 recvMsg = new GGRecvMsg80(context.getPackageContent());
		if (LOG.isTraceEnabled()) {
			LOG.trace(recvMsg.toString());
		}
		context.getSessionAccessor().notifyGGPacketReceived(recvMsg);
		// FIXME only plain message
		final IIncommingMessage incommingMessage = new IncomingMessage(recvMsg.getSender(), recvMsg.getPlainMessage(), recvMsg
				.getMessageSeq(), recvMsg.getTime(), recvMsg.getMsgClass());
		context.getSessionAccessor().notifyMessageArrived(incommingMessage);
	}

}
