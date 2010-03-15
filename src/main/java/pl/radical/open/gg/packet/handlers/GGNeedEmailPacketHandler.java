package pl.radical.open.gg.packet.handlers;

import pl.radical.open.gg.GGException;
import pl.radical.open.gg.event.LoginFailedEvent;
import pl.radical.open.gg.packet.in.GGNeedEmail;
import pl.radical.open.gg.utils.GGUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on 2005-10-25
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class GGNeedEmailPacketHandler implements PacketHandler {
	private static final Logger log = LoggerFactory.getLogger(GGWelcomePacketHandler.class);

	public void handle(final PacketContext context) throws GGException {
		if (log.isDebugEnabled()) {
			log.debug("GGNeedEmail packet received.");
			log.debug("PacketHeader: " + context.getHeader());
			log.debug("PacketLoad: " + GGUtils.prettyBytesToString(context.getPackageContent()));
		}

		final GGNeedEmail needEmail = GGNeedEmail.getInstance();
		context.getSessionAccessor().notifyGGPacketReceived(needEmail);
		final LoginFailedEvent loginFailedEvent = new LoginFailedEvent(this);
		loginFailedEvent.setReason(LoginFailedEvent.NEED_EMAIL_REASON);
		context.getSessionAccessor().notifyLoginFailed(loginFailedEvent);
	}

}
