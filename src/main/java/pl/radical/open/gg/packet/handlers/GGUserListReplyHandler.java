package pl.radical.open.gg.packet.handlers;

import pl.radical.open.gg.GGException;
import pl.radical.open.gg.LocalUser;
import pl.radical.open.gg.packet.in.GGUserListReply;
import pl.radical.open.gg.utils.GGUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on 2004-12-11
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class GGUserListReplyHandler implements PacketHandler {
	private static final Logger LOG = LoggerFactory.getLogger(GGUserListReplyHandler.class);

	private final List<LocalUser> users = new ArrayList<LocalUser>();

	/**
	 * @see pl.radical.open.gg.packet.handlers.PacketHandler#handle(pl.radical.open.gg.packet.handlers.Context)
	 */
	public void handle(final PacketContext context) throws GGException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("GGUserlistReply packet received.");
			LOG.debug("PacketHeader: " + context.getHeader());
			LOG.debug("Got packet: " + GGUtils.prettyBytesToString(context.getPackageContent()));
		}

		try {
			final GGUserListReply userListReply = new GGUserListReply(context.getPackageContent());
			context.getSessionAccessor().notifyGGPacketReceived(userListReply);
			if (userListReply.isGetMoreReply()) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("GGUserListReply.GetMoreReply");
				}
				final Collection<LocalUser> contactList = userListReply.getContactList();

				if (LOG.isDebugEnabled()) {
					LOG.debug("GGUserListReply: adding users to private user collection...");
				}
				users.addAll(contactList);
			} else if (userListReply.isGetReply()) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("GGUserListReply.GetReply");
				}

				final Collection<LocalUser> contactList = userListReply.getContactList();
				users.addAll(contactList);
				final ArrayList<LocalUser> clonedUsers = new ArrayList<LocalUser>(users);

				if (LOG.isDebugEnabled()) {
					LOG.debug("GGUserListReply: clearing private users collection...");
				}
				users.clear();
				context.getSessionAccessor().notifyContactListReceived(clonedUsers);
			} else if (userListReply.isPutMoreReply()) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("GGUserListReply.PutMoreReply");
				}
			} else if (userListReply.isPutReply()) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("GGUserListReply.PutReply");
				}
				// context.getSessionAccessor().notifyContactListExported();
			}
		} catch (final IOException ex) {
			LOG.error("Unable to handle incomming packet: " + GGUtils.prettyBytesToString(context.getPackageContent()), ex);
			throw new GGException("Unable to handle incoming user list packet.", ex);
		}

	}

}
