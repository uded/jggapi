/*
 * Copyright (c) 2003-2005 JGGApi Development Team. All Rights Reserved.
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package pl.mn.communicator.packet.handlers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pl.mn.communicator.MessageStatus;
import pl.mn.communicator.packet.GGUtils;
import pl.mn.communicator.packet.in.GGSendMsgAck;

/**
 * Created on 2004-11-28
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGSentMessageAckPacketHandler.java,v 1.10 2005-01-25 23:52:31 winnetou25 Exp $
 */
public class GGSentMessageAckPacketHandler implements PacketHandler {

	private final static Log logger = LogFactory.getLog(GGSentMessageAckPacketHandler.class);

	/**
	 * @see pl.mn.communicator.packet.handlers.PacketHandler#handle(pl.mn.communicator.packet.handlers.Context)
	 */
	public void handle(Context context) {
		if (logger.isDebugEnabled()) {
			logger.debug("GGSentMessageAck packet received.");
			logger.debug("PacketHeader: "+context.getHeader());
			logger.debug("PacketBody: "+GGUtils.prettyBytesToString(context.getPackageContent()));
		}
		GGSendMsgAck sendMessageAck = new GGSendMsgAck(context.getPackageContent());
		context.getSessionAccessor().notifyGGPacketReceived(sendMessageAck);
		int uin  = sendMessageAck.getRecipientUin();
		int messageID = sendMessageAck.getMessageSeq();
		MessageStatus messageStatus = GGUtils.getClientMessageStatus(sendMessageAck.getMessageStatus());
		context.getSessionAccessor().notifyMessageDelivered(uin, messageID, messageStatus);
	}

}
