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
package pl.mn.communicator.gadu.handlers;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pl.mn.communicator.gadu.GGUtils;
import pl.mn.communicator.gadu.in.GGDisconnecting;
import pl.mn.communicator.gadu.in.GGLoginFailed;
import pl.mn.communicator.gadu.in.GGLoginOK;
import pl.mn.communicator.gadu.in.GGNotifyReply;
import pl.mn.communicator.gadu.in.GGNotifyReply60;
import pl.mn.communicator.gadu.in.GGPong;
import pl.mn.communicator.gadu.in.GGRecvMsg;
import pl.mn.communicator.gadu.in.GGSendMsgAck;
import pl.mn.communicator.gadu.in.GGStatus;
import pl.mn.communicator.gadu.in.GGStatus60;
import pl.mn.communicator.gadu.in.GGUserListReply;
import pl.mn.communicator.gadu.in.GGWelcome;

/**
 * Created on 2004-11-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: PacketChain.java,v 1.7 2004-12-13 23:44:02 winnetou25 Exp $
 */
public class PacketChain {

	private final static Log logger = LogFactory.getLog(PacketChain.class);
	
	private HashMap m_packetHandlers;
	
	private static PacketChain m_instance;
	
	private PacketChain() {
		m_packetHandlers = new HashMap();
		registerDefaultHandlers();
	}
	
	public final static PacketChain getInstance() {
		if (m_instance == null) {
			m_instance = new PacketChain();
		}
		return m_instance;
	}

	public void registerGGPackageHandler(int packetType, PacketHandler packetHandler) {
		if (packetHandler == null) throw new NullPointerException("packetHandler cannot be null");
		m_packetHandlers.put(new Integer(packetType), packetHandler);
	}
	
	public void unregisterGGPackageHandler(int packetType) {
		m_packetHandlers.remove(new Integer(packetType));
	}
	
	public void sendToChain(Context packageContent) {
		PacketHandler packetHandler = (PacketHandler) m_packetHandlers.get(new Integer(packageContent.getHeader().getType()));
		if (packetHandler == null) {
			logger.error("Unknown package.");
			logger.error("PacketHeader: "+packageContent.getHeader());
			logger.error("PacketBody: "+GGUtils.bytesToString(packageContent.getPackageContent()));
		} else {
			packetHandler.handle(packageContent);
		}
	}
	
	private void registerDefaultHandlers() {
		registerGGPackageHandler(GGWelcome.GG_PACKAGE_WELCOME, new GGWelcomePacketHandler());
		registerGGPackageHandler(GGLoginOK.GG_LOGIN_OK, new GGLoginOKPacketHandler());
		registerGGPackageHandler(GGLoginFailed.GG_LOGIN_FAILED, new GGLoginFailedPacketHandler());
		registerGGPackageHandler(GGStatus.GG_STATUS, new GGStatusPacketHandler());
		registerGGPackageHandler(GGStatus60.GG_STATUS60, new GGStatus60PacketHandler());
		registerGGPackageHandler(GGNotifyReply.GG_NOTIFY_REPLY, new GGNotifyReplyPacketHandler());
		registerGGPackageHandler(GGNotifyReply60.GG_NOTIFY_REPLY60, new GGNotifyReply60PacketHandler());
		registerGGPackageHandler(GGRecvMsg.GG_RECV_MSG, new GGMessageReceivedPacketHandler());
		registerGGPackageHandler(GGSendMsgAck.GG_SEND_MSG_ACK, new GGSentMessageAckPacketHandler());
		registerGGPackageHandler(GGUserListReply.GG_USERLIST_REPLY, new GGUserListReplyHandler());

		registerGGPackageHandler(GGDisconnecting.GG_DISCONNECTING, new GGDisconnectingPacketHandler());
		registerGGPackageHandler(GGPong.GG_PONG, new GGPongPacketHandler());
	}

}
