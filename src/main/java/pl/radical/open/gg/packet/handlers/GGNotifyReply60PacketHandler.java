package pl.radical.open.gg.packet.handlers;

import pl.radical.open.gg.GGException;
import pl.radical.open.gg.IRemoteStatus;
import pl.radical.open.gg.IUser;
import pl.radical.open.gg.RemoteStatus;
import pl.radical.open.gg.packet.in.GGNotifyReply60;
import pl.radical.open.gg.utils.GGUtils;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on 2004-12-12
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
@SuppressWarnings("deprecation")
public class GGNotifyReply60PacketHandler implements PacketHandler {
	private static final Logger LOG = LoggerFactory.getLogger(GGNotifyReply60PacketHandler.class);

	/**
	 * @see pl.radical.open.gg.packet.handlers.PacketHandler#handle(pl.radical.open.gg.packet.handlers.Context)
	 */
	public void handle(final PacketContext context) throws GGException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("NotifyPacketReply60 packet received.");
			LOG.debug("PacketHeader: " + context.getHeader());
			LOG.debug("PacketBody: " + GGUtils.prettyBytesToString(context.getPackageContent()));
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
