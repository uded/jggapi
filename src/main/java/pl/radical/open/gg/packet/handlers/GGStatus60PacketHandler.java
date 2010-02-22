package pl.radical.open.gg.packet.handlers;

import pl.radical.open.gg.GGException;
import pl.radical.open.gg.IRemoteStatus;
import pl.radical.open.gg.IUser;
import pl.radical.open.gg.packet.GGUtils;
import pl.radical.open.gg.packet.in.GGStatus60;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on 2004-12-12
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class GGStatus60PacketHandler implements PacketHandler {

	private final static Logger logger = LoggerFactory.getLogger(GGStatus60PacketHandler.class);

	/**
	 * @see pl.radical.open.gg.packet.handlers.PacketHandler#handle(pl.radical.open.gg.packet.handlers.Context)
	 */
	public void handle(final PacketContext context) throws GGException {
		if (logger.isDebugEnabled()) {
			logger.debug("Received GGStatus60 packet.");
			logger.debug("PacketHeader: " + context.getHeader());
			logger.debug("PacketBody: " + GGUtils.prettyBytesToString(context.getPackageContent()));
		}

		final GGStatus60 status60 = new GGStatus60(context.getPackageContent());
		context.getSessionAccessor().notifyGGPacketReceived(status60);

		final IUser user = status60.getUser();
		final IRemoteStatus status = status60.getStatus60();
		context.getSessionAccessor().notifyUserChangedStatus(user, status);
	}

}
