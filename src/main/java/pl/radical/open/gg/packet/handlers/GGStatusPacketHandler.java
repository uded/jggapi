package pl.radical.open.gg.packet.handlers;

import pl.radical.open.gg.GGException;
import pl.radical.open.gg.IRemoteStatus;
import pl.radical.open.gg.IUser;
import pl.radical.open.gg.packet.GGUtils;
import pl.radical.open.gg.packet.in.GGStatus;

import org.apache.log4j.Logger;

/**
 * Created on 2004-11-28
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class GGStatusPacketHandler implements PacketHandler {

	private final Logger logger = Logger.getLogger(GGStatusPacketHandler.class);

	/**
	 * @see pl.radical.open.gg.packet.handlers.PacketHandler#handle(pl.radical.open.gg.packet.handlers.Context)
	 */
	public void handle(final PacketContext context) throws GGException {
		if (logger.isDebugEnabled()) {
			logger.debug("Received GGStatus packet.");
			logger.debug("PacketHeader: " + context.getHeader());
			logger.debug("PacketBody: " + GGUtils.prettyBytesToString(context.getPackageContent()));
		}
		final GGStatus status = new GGStatus(context.getPackageContent());
		context.getSessionAccessor().notifyGGPacketReceived(status);
		final IUser user = status.getUser();
		final IRemoteStatus statusBiz = status.getStatus();
		context.getSessionAccessor().notifyUserChangedStatus(user, statusBiz);
	}

}
