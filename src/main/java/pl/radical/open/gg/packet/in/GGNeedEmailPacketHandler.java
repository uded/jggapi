package pl.radical.open.gg.packet.in;

import pl.radical.open.gg.GGException;
import pl.radical.open.gg.event.LoginFailedEvent;
import pl.radical.open.gg.packet.GGUtils;
import pl.radical.open.gg.packet.handlers.GGWelcomePacketHandler;
import pl.radical.open.gg.packet.handlers.PacketContext;
import pl.radical.open.gg.packet.handlers.PacketHandler;

import org.apache.log4j.Logger;

/**
 * Created on 2005-10-25
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class GGNeedEmailPacketHandler implements PacketHandler {

	private static final Logger LOGGER = Logger.getLogger(GGWelcomePacketHandler.class);

	public void handle(final PacketContext context) throws GGException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("GGNeedEmail packet received.");
			LOGGER.debug("PacketHeader: " + context.getHeader());
			LOGGER.debug("PacketLoad: " + GGUtils.prettyBytesToString(context.getPackageContent()));
		}

		final GGNeedEmail needEmail = GGNeedEmail.getInstance();
		context.getSessionAccessor().notifyGGPacketReceived(needEmail);
		final LoginFailedEvent loginFailedEvent = new LoginFailedEvent(this);
		loginFailedEvent.setReason(LoginFailedEvent.NEED_EMAIL_REASON);
		context.getSessionAccessor().notifyLoginFailed(loginFailedEvent);
	}

}
