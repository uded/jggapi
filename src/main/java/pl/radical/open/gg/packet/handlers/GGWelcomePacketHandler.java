package pl.radical.open.gg.packet.handlers;

import pl.radical.open.gg.GGException;
import pl.radical.open.gg.packet.dicts.SessionState;
import pl.radical.open.gg.packet.in.GGWelcome;
import pl.radical.open.gg.utils.GGUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on 2004-11-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class GGWelcomePacketHandler implements PacketHandler {
	private static final Logger LOG = LoggerFactory.getLogger(GGWelcomePacketHandler.class);

	/**
	 * @see pl.radical.open.gg.packet.handlers.PacketHandler#handle(pl.radical.open.gg.packet.handlers.Context)
	 */
	public void handle(final PacketContext context) throws GGException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("GGWelcome packet received.");
			LOG.debug("PacketHeader: " + context.getHeader());
			LOG.debug("PacketLoad: " + GGUtils.prettyBytesToString(context.getPackageContent()));
		}

		final GGWelcome welcome = new GGWelcome(context.getPackageContent());
		context.getSessionAccessor().notifyGGPacketReceived(welcome);
		context.getSessionAccessor().setLoginSeed(welcome.getSeed());
		context.getSessionAccessor().setSessionState(SessionState.CONNECTED);
		context.getSessionAccessor().notifyConnectionEstablished();
	}

}
