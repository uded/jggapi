package pl.radical.open.gg.packet.handlers;

import pl.radical.open.gg.GGException;
import pl.radical.open.gg.IRemoteStatus;
import pl.radical.open.gg.IUser;
import pl.radical.open.gg.RemoteStatus;
import pl.radical.open.gg.User;
import pl.radical.open.gg.packet.in.GGNotifyReply;
import pl.radical.open.gg.utils.GGUtils;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on 2004-11-28
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
@SuppressWarnings("deprecation")
public class GGNotifyReplyPacketHandler implements PacketHandler {
	private static final Logger log = LoggerFactory.getLogger(GGNotifyReplyPacketHandler.class);

	/**
	 * @see pl.radical.open.gg.packet.handlers.PacketHandler#handle(pl.radical.open.gg.packet.handlers.Context)
	 */
	public void handle(final PacketContext context) throws GGException {
		if (log.isDebugEnabled()) {
			log.debug("NotifyPacketReply received.");
			log.debug("PacketHeader: " + context.getHeader());
			log.debug("PacketBody: " + GGUtils.prettyBytesToString(context.getPackageContent()));
		}

		final GGNotifyReply notifyReply = new GGNotifyReply(context.getPackageContent());
		context.getSessionAccessor().notifyGGPacketReceived(notifyReply);

		final Map<User, RemoteStatus> usersStatuses = notifyReply.getUsersStatus();
		for (final Object element : usersStatuses.keySet()) {
			final IUser user = (IUser) element;
			final IRemoteStatus status = usersStatuses.get(user);
			context.getSessionAccessor().notifyUserChangedStatus(user, status);
		}
	}

}
