package pl.radical.open.gg.packet.handlers;

import pl.radical.open.gg.GGException;
import pl.radical.open.gg.event.LoginFailedEvent;
import pl.radical.open.gg.packet.GGUtils;
import pl.radical.open.gg.packet.in.GGLoginFailed;

import org.apache.log4j.Logger;

/**
 * Created on 2004-11-28
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class GGLoginFailedPacketHandler implements PacketHandler {

	private final static Logger logger = Logger.getLogger(GGLoginFailedPacketHandler.class);

	/**
	 * @see pl.radical.open.gg.packet.handlers.PacketHandler#handle(pl.radical.open.gg.packet.handlers.Context)
	 */
	public void handle(final PacketContext context) throws GGException {
		if (logger.isDebugEnabled()) {
			logger.debug("LoginFailed packet received.");
			logger.debug("PacketHeader: " + context.getHeader());
			logger.debug("PacketLoad: " + GGUtils.prettyBytesToString(context.getPackageContent()));
		}

		final GGLoginFailed loginFailed = GGLoginFailed.getInstance();
		context.getSessionAccessor().notifyGGPacketReceived(loginFailed);
		final LoginFailedEvent loginFailedEvent = new LoginFailedEvent(this);
		context.getSessionAccessor().notifyLoginFailed(loginFailedEvent);
	}

}
