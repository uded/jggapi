/*
 * Copyright (c) 2003-2005 JGGApi Development Team. All Rights Reserved. This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version. This program is distributed in
 * the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a
 * copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package pl.radical.open.gg.packet.handlers;

import pl.radical.open.gg.GGException;
import pl.radical.open.gg.MessageStatus;
import pl.radical.open.gg.packet.GGConversion;
import pl.radical.open.gg.packet.GGUtils;
import pl.radical.open.gg.packet.in.GGSendMsgAck;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on 2004-11-28
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGSentMessageAckPacketHandler.java,v 1.1 2005/11/05 23:34:53 winnetou25 Exp $
 */
public class GGSentMessageAckPacketHandler implements PacketHandler {

	private final static Logger logger = LoggerFactory.getLogger(GGSentMessageAckPacketHandler.class);

	/**
	 * @see pl.radical.open.gg.packet.handlers.PacketHandler#handle(pl.radical.open.gg.packet.handlers.Context)
	 */
	public void handle(final PacketContext context) throws GGException {
		if (logger.isDebugEnabled()) {
			logger.debug("GGSentMessageAck packet received.");
			logger.debug("PacketHeader: " + context.getHeader());
			logger.debug("PacketBody: " + GGUtils.prettyBytesToString(context.getPackageContent()));
		}

		final GGSendMsgAck sendMessageAck = new GGSendMsgAck(context.getPackageContent());
		context.getSessionAccessor().notifyGGPacketReceived(sendMessageAck);
		final int uin = sendMessageAck.getRecipientUin();
		final int messageID = sendMessageAck.getMessageSeq();
		final MessageStatus messageStatus = GGConversion.getClientMessageStatus(sendMessageAck.getMessageStatus());
		context.getSessionAccessor().notifyMessageDelivered(uin, messageID, messageStatus);
	}

}