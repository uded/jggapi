package pl.radical.open.gg.packet.handlers;

import pl.radical.open.gg.GGException;
import pl.radical.open.gg.IRemoteStatus;
import pl.radical.open.gg.IUser;
import pl.radical.open.gg.RemoteStatus;
import pl.radical.open.gg.packet.GGUtils;
import pl.radical.open.gg.packet.in.GGNotifyReply60;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on 2004-12-12
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class GGNotifyReply60PacketHandler implements PacketHandler {

	private final static Logger logger = LoggerFactory.getLogger(GGNotifyReply60PacketHandler.class);

	/**
	 * @see pl.radical.open.gg.packet.handlers.PacketHandler#handle(pl.radical.open.gg.packet.handlers.Context)
	 */
	public void handle(final PacketContext context) throws GGException {
		if (logger.isDebugEnabled()) {
			logger.debug("NotifyPacketReply60 packet received.");
			logger.debug("PacketHeader: " + context.getHeader());
			logger.debug("PacketBody: " + GGUtils.prettyBytesToString(context.getPackageContent()));
		}

		final GGNotifyReply60 notifyReply = new GGNotifyReply60(context.getPackageContent());
		context.getSessionAccessor().notifyGGPacketReceived(notifyReply);

		final Map<IUser, RemoteStatus> usersStatuses = notifyReply.getUsersStatus();
		for (final Object element : usersStatuses.keySet()) {
			final IUser user = (IUser) element;
			final IRemoteStatus status = usersStatuses.get(user);
			context.getSessionAccessor().notifyUserChangedStatus(user, status);
		}
	}

}
