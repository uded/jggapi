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
	private static final Logger LOG = LoggerFactory.getLogger(GGWelcomePacketHandler.class);

	public void handle(final PacketContext context) throws GGException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("GGNeedEmail packet received.");
			LOG.debug("PacketHeader: " + context.getHeader());
			LOG.debug("PacketLoad: " + GGUtils.prettyBytesToString(context.getPackageContent()));
		}

		final GGNeedEmail needEmail = GGNeedEmail.getInstance();
		context.getSessionAccessor().notifyGGPacketReceived(needEmail);
		final LoginFailedEvent loginFailedEvent = new LoginFailedEvent(this);
		loginFailedEvent.setReason(LoginFailedEvent.NEED_EMAIL_REASON);
		context.getSessionAccessor().notifyLoginFailed(loginFailedEvent);
	}

}
