package pl.radical.open.gg.packet.handlers;

import pl.radical.open.gg.GGException;
import pl.radical.open.gg.packet.GGUtils;
import pl.radical.open.gg.packet.in.GGPong;

import org.apache.log4j.Logger;

/**
 * Created on 2004-11-28
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class GGPongPacketHandler implements PacketHandler {

	private final static Logger logger = Logger.getLogger(GGPongPacketHandler.class);

	/**
	 * @see pl.radical.open.gg.packet.handlers.PacketHandler#handle(pl.radical.open.gg.packet.handlers.Context)
	 */
	public void handle(final PacketContext context) throws GGException {
		if (logger.isDebugEnabled()) {
			logger.debug("GGPong packet received.");
			logger.debug("PacketHeader: " + context.getHeader());
			logger.debug("PacketBody: " + GGUtils.prettyBytesToString(context.getPackageContent()));
		}

		final GGPong pong = GGPong.getInstance();
		context.getSessionAccessor().notifyGGPacketReceived(pong);
		context.getSessionAccessor().notifyPongReceived();
	}

}
