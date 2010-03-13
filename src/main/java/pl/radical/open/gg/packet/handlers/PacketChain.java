package pl.radical.open.gg.packet.handlers;

import pl.radical.open.gg.GGException;
import pl.radical.open.gg.GGNullPointerException;
import pl.radical.open.gg.packet.GGUtils;
import pl.radical.open.gg.packet.in.GGDisconnecting;
import pl.radical.open.gg.packet.in.GGLogin80OK;
import pl.radical.open.gg.packet.in.GGLoginFailed;
import pl.radical.open.gg.packet.in.GGLoginOK;
import pl.radical.open.gg.packet.in.GGNeedEmail;
import pl.radical.open.gg.packet.in.GGNotifyReply;
import pl.radical.open.gg.packet.in.GGNotifyReply60;
import pl.radical.open.gg.packet.in.GGPong;
import pl.radical.open.gg.packet.in.GGPubdirReply;
import pl.radical.open.gg.packet.in.GGRecvMsg;
import pl.radical.open.gg.packet.in.GGSendMsgAck;
import pl.radical.open.gg.packet.in.GGStatus;
import pl.radical.open.gg.packet.in.GGStatus60;
import pl.radical.open.gg.packet.in.GGUserListReply;
import pl.radical.open.gg.packet.in.GGWelcome;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on 2004-11-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class PacketChain {

	private final static Logger logger = LoggerFactory.getLogger(PacketChain.class);

	private final HashMap<Integer, PacketHandler> m_packetHandlers;

	public PacketChain() {
		m_packetHandlers = new HashMap<Integer, PacketHandler>();
		registerDefaultHandlers();
	}

	public void registerGGPackageHandler(final int packetType, final PacketHandler packetHandler) {
		if (packetHandler == null) {
			throw new GGNullPointerException("packetHandler cannot be null");
		}
		m_packetHandlers.put(Integer.valueOf(packetType), packetHandler);
	}

	public void unregisterGGPackageHandler(final int packetType) {
		m_packetHandlers.remove(Integer.valueOf(packetType));
	}

	public void sendToChain(final PacketContext packageContent) throws GGException {
		final PacketHandler packetHandler = m_packetHandlers.get(Integer.valueOf(packageContent.getHeader().getType()));
		if (packetHandler == null) {
			logger.warn("Unknown package.");
			logger.warn("PacketHeader: " + packageContent.getHeader());
			logger.warn("PacketBody: " + GGUtils.prettyBytesToString(packageContent.getPackageContent()));
			return;
		}

		packetHandler.handle(packageContent);
	}

	private void registerDefaultHandlers() {
		registerGGPackageHandler(GGWelcome.GG_PACKAGE_WELCOME, new GGWelcomePacketHandler());
		registerGGPackageHandler(GGLoginOK.GG_LOGIN_OK, new GGLoginOKPacketHandler());
		registerGGPackageHandler(GGLogin80OK.GG_LOGIN80_OK, new GGLogin80OKPacketHandler());
		registerGGPackageHandler(GGLoginFailed.GG_LOGIN_FAILED, new GGLoginFailedPacketHandler());
		registerGGPackageHandler(GGNeedEmail.GG_NEED_EMAIL, new GGNeedEmailPacketHandler());
		registerGGPackageHandler(GGStatus.GG_STATUS, new GGStatusPacketHandler());
		registerGGPackageHandler(GGStatus60.GG_STATUS60, new GGStatus60PacketHandler());
		registerGGPackageHandler(GGNotifyReply.GG_NOTIFY_REPLY, new GGNotifyReplyPacketHandler());
		registerGGPackageHandler(GGNotifyReply60.GG_NOTIFY_REPLY60, new GGNotifyReply60PacketHandler());
		registerGGPackageHandler(GGRecvMsg.GG_RECV_MSG, new GGMessageReceivedPacketHandler());
		registerGGPackageHandler(GGSendMsgAck.GG_SEND_MSG_ACK, new GGSentMessageAckPacketHandler());
		registerGGPackageHandler(GGUserListReply.GG_USERLIST_REPLY, new GGUserListReplyHandler());
		registerGGPackageHandler(GGPubdirReply.GG_PUBDIR50_REPLY, new GGPubdirReplyPacketHandler());

		registerGGPackageHandler(GGDisconnecting.GG_DISCONNECTING, new GGDisconnectingPacketHandler());
		registerGGPackageHandler(GGPong.GG_PONG, new GGPongPacketHandler());
	}

}
