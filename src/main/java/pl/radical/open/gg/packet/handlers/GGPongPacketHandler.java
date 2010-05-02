package pl.radical.open.gg.packet.handlers;

import pl.radical.open.gg.GGException;
import pl.radical.open.gg.packet.in.GGPong;
import pl.radical.open.gg.utils.GGUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on 2004-11-28
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
@SuppressWarnings("deprecation")
public class GGPongPacketHandler implements PacketHandler {
	private static final Logger log = LoggerFactory.getLogger(GGPongPacketHandler.class);

	/**
	 * @see pl.radical.open.gg.packet.handlers.PacketHandler#handle(pl.radical.open.gg.packet.handlers.Context)
	 */
	public void handle(final PacketContext context) throws GGException {
		if (log.isDebugEnabled()) {
			log.debug("GGPong packet received.");
			log.debug("PacketHeader: " + context.getHeader());
			log.debug("PacketBody: " + GGUtils.prettyBytesToString(context.getPackageContent()));
		}

		final GGPong pong = GGPong.getInstance();
		context.getSessionAccessor().notifyGGPacketReceived(pong);
		context.getSessionAccessor().notifyPongReceived();
	}

}
