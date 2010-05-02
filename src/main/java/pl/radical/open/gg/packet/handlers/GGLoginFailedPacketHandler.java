package pl.radical.open.gg.packet.handlers;

import pl.radical.open.gg.GGException;
import pl.radical.open.gg.event.LoginFailedEvent;
import pl.radical.open.gg.packet.in.GGLoginFailed;
import pl.radical.open.gg.utils.GGUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on 2004-11-28
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class GGLoginFailedPacketHandler implements PacketHandler {
	private static final Logger log = LoggerFactory.getLogger(GGLoginFailedPacketHandler.class);

	/**
	 * @see pl.radical.open.gg.packet.handlers.PacketHandler#handle(pl.radical.open.gg.packet.handlers.Context)
	 */
	public void handle(final PacketContext context) throws GGException {
		if (log.isDebugEnabled()) {
			log.debug("LoginFailed packet received.");
			log.debug("PacketHeader: " + context.getHeader());
			log.debug("PacketLoad: " + GGUtils.prettyBytesToString(context.getPackageContent()));
		}

		final GGLoginFailed loginFailed = GGLoginFailed.getInstance();
		context.getSessionAccessor().notifyGGPacketReceived(loginFailed);
		final LoginFailedEvent loginFailedEvent = new LoginFailedEvent(this);
		context.getSessionAccessor().notifyLoginFailed(loginFailedEvent);
	}

}
