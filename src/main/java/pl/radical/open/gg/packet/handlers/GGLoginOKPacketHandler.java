package pl.radical.open.gg.packet.handlers;

import pl.radical.open.gg.GGException;
import pl.radical.open.gg.packet.in.GGLoginOK;
import pl.radical.open.gg.utils.GGUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
@SuppressWarnings("deprecation")
@Deprecated
public class GGLoginOKPacketHandler implements PacketHandler {
	private static final Logger LOG = LoggerFactory.getLogger(GGLoginOKPacketHandler.class);

	/**
	 * @see pl.radical.open.gg.packet.handlers.PacketHandler#handle(pl.radical.open.gg.packet.handlers.Context)
	 */
	public void handle(final PacketContext context) throws GGException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("LoginOK packet received.");
			LOG.debug("PacketHeader: " + context.getHeader());
			LOG.debug("PacketBody: " + GGUtils.prettyBytesToString(context.getPackageContent()));
		}

		final GGLoginOK loginOk = GGLoginOK.getInstance();
		context.getSessionAccessor().notifyGGPacketReceived(loginOk);
		context.getSessionAccessor().notifyLoginOK();
	}

}
