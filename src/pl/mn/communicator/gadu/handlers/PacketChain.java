/*
 * Created on 2004-11-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pl.mn.communicator.gadu.handlers;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pl.mn.communicator.gadu.GGDisconnecting;
import pl.mn.communicator.gadu.GGLoginFailed;
import pl.mn.communicator.gadu.GGLoginOK;
import pl.mn.communicator.gadu.GGNotifyReply;
import pl.mn.communicator.gadu.GGPong;
import pl.mn.communicator.gadu.GGRecvMsg;
import pl.mn.communicator.gadu.GGSendMsgAck;
import pl.mn.communicator.gadu.GGStatus;
import pl.mn.communicator.gadu.GGUtils;
import pl.mn.communicator.gadu.GGWelcome;


/**
 * @author mateusz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
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
			logger.error("Unknown package: "+GGUtils.bytesToString(packageContent.getPackageContent()));
		}
		packetHandler.handle(packageContent);
	}
	
	private void registerDefaultHandlers() {
		registerGGPackageHandler(GGLoginOK.GG_LOGIN_OK, new GGLoginOKPacketHandler());
		registerGGPackageHandler(GGLoginFailed.GG_LOGIN_FAILED, new GGLoginFailedPacketHandler());
		registerGGPackageHandler(GGWelcome.GG_PACKAGE_WELCOME, new GGWelcomePacketHandler());
		registerGGPackageHandler(GGDisconnecting.GG_DISCONNECTING, new GGDisconnectingPacketHandler());
		registerGGPackageHandler(GGStatus.GG_STATUS, new GGStatusPacketHandler());
		registerGGPackageHandler(GGNotifyReply.GG_NOTIFY_REPLY, new GGNotifyReplyPacketHandler());
		registerGGPackageHandler(GGPong.GG_PONG, new GGPongPacketHandler());
		registerGGPackageHandler(GGRecvMsg.GG_RECV_MSG, new GGMessageReceivedPacketHandler());
		registerGGPackageHandler(GGSendMsgAck.GG_SEND_MSG_ACK, new GGSentMessageAckPacketHandler());
	}

}
