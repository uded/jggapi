/*
 * Copyright (c) 2003 Marcin Naglik (mnaglik@gazeta.pl)
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

import pl.mn.communicator.IncommingMessage;
import pl.mn.communicator.event.MessageArrivedEvent;
import pl.mn.communicator.packet.GGUtils;
import pl.mn.communicator.packet.in.GGRecvMsg;

/**
 * Created on 2004-11-28
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGMessageReceivedPacketHandler.java,v 1.1 2004-12-14 19:29:56 winnetou25 Exp $
 */
public class GGMessageReceivedPacketHandler implements PacketHandler {

	private static final Log logger = LogFactory.getLog(GGMessageReceivedPacketHandler.class);

	/**
	 * @see pl.mn.communicator.packet.handlers.PacketHandler#handle(pl.mn.communicator.gadu.handlers.Context)
	 */
	public void handle(Context context) {
		logger.debug("GGMessageReceived packet received.");
		logger.debug("PacketHeader: "+context.getHeader());
		logger.debug("PacketLoad: "+GGUtils.bytesToString(context.getPackageContent()));

		GGRecvMsg recvMsg = new GGRecvMsg(context.getPackageContent());
		context.getSessionAccessor().notifyGGPacketReceived(recvMsg);
		IncommingMessage incommingMessage = new IncommingMessage(recvMsg.getSenderUin(), recvMsg.getMessage(), recvMsg.getMessageSeq(), recvMsg.getTime(), recvMsg.getMsgClass());
		MessageArrivedEvent messageArrivedEvent = new MessageArrivedEvent(this, incommingMessage);
		context.getSessionAccessor().notifyMessageArrived(messageArrivedEvent);
	}

}