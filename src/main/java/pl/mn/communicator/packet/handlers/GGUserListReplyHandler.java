/*
 * Copyright (c) 2003-2005 JGGApi Development Team. All Rights Reserved. This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version. This program is distributed in
 * the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a
 * copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package pl.mn.communicator.packet.handlers;

import pl.mn.communicator.GGException;
import pl.mn.communicator.LocalUser;
import pl.mn.communicator.packet.GGUtils;
import pl.mn.communicator.packet.in.GGUserListReply;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on 2004-12-11
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGUserListReplyHandler.java,v 1.3 2005/11/20 16:08:10 winnetou25 Exp $
 */
public class GGUserListReplyHandler implements PacketHandler {

	private final static Logger LOGGER = LoggerFactory.getLogger(GGUserListReplyHandler.class);

	private final ArrayList<LocalUser> m_users = new ArrayList<LocalUser>();

	/**
	 * @see pl.mn.communicator.packet.handlers.PacketHandler#handle(pl.mn.communicator.packet.handlers.Context)
	 */
	public void handle(final PacketContext context) throws GGException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("GGUserlistReply packet received.");
			LOGGER.debug("PacketHeader: " + context.getHeader());
			LOGGER.debug("Got packet: " + GGUtils.prettyBytesToString(context.getPackageContent()));
		}

		try {
			final GGUserListReply userListReply = new GGUserListReply(context.getPackageContent());
			context.getSessionAccessor().notifyGGPacketReceived(userListReply);
			if (userListReply.isGetMoreReply()) {
				LOGGER.debug("GGUserListReply.GetMoreReply");
				final Collection<LocalUser> contactList = userListReply.getContactList();
				LOGGER.debug("GGUserListReply: adding users to private user collection...");
				m_users.addAll(contactList);
			} else if (userListReply.isGetReply()) {
				LOGGER.debug("GGUserListReply.GetReply");
				final Collection<LocalUser> contactList = userListReply.getContactList();
				m_users.addAll(contactList);
				final ArrayList<LocalUser> clonedUsers = new ArrayList<LocalUser>(m_users);
				LOGGER.debug("GGUserListReply: clearing private users collection...");
				m_users.clear();
				context.getSessionAccessor().notifyContactListReceived(clonedUsers);
			} else if (userListReply.isPutMoreReply()) {
				LOGGER.debug("GGUserListReply.PutMoreReply");
			} else if (userListReply.isPutReply()) {
				LOGGER.debug("GGUserListReply.PutReply");
				// context.getSessionAccessor().notifyContactListExported();
			}
		} catch (final IOException ex) {
			LOGGER.error("Unable to handle incomming packet: " + GGUtils.prettyBytesToString(context.getPackageContent()), ex);
			throw new GGException("Unable to handle incoming user list packet.", ex);
		}

	}

}
