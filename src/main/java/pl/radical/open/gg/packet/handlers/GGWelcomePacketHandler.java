package pl.radical.open.gg.packet.handlers;

import pl.radical.open.gg.GGException;
import pl.radical.open.gg.SessionState;
import pl.radical.open.gg.packet.GGUtils;
import pl.radical.open.gg.packet.in.GGWelcome;

import org.apache.log4j.Logger;

/**
 * Created on 2004-11-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class GGWelcomePacketHandler implements PacketHandler {

	private static final Logger logger = Logger.getLogger(GGWelcomePacketHandler.class);

	/**
	 * @see pl.radical.open.gg.packet.handlers.PacketHandler#handle(pl.radical.open.gg.packet.handlers.Context)
	 */
	public void handle(final PacketContext context) throws GGException {
		if (logger.isDebugEnabled()) {
			logger.debug("GGWelcome packet received.");
			logger.debug("PacketHeader: " + context.getHeader());
			logger.debug("PacketLoad: " + GGUtils.prettyBytesToString(context.getPackageContent()));
		}

		final GGWelcome welcome = new GGWelcome(context.getPackageContent());
		context.getSessionAccessor().notifyGGPacketReceived(welcome);
		context.getSessionAccessor().setLoginSeed(welcome.getSeed());
		context.getSessionAccessor().setSessionState(SessionState.CONNECTED);
		context.getSessionAccessor().notifyConnectionEstablished();
	}

}
