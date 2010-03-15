package pl.radical.open.gg.packet.handlers;

import pl.radical.open.gg.GGException;
import pl.radical.open.gg.packet.IncomingPacket;
import pl.radical.open.gg.utils.GGUtils;

import java.util.HashMap;
import java.util.Set;

import org.reflections.Configuration;
import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Predicate;

/**
 * Created on 2004-11-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @author <a href="mailto:lukasz.rzanek@radical.com.pl>Łukasz Rżanek</a>
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
			throw new IllegalArgumentException("packetHandler cannot be null");
		}
		m_packetHandlers.put(Integer.valueOf(packetType), packetHandler);
	}

	public void registerGGPackageHandler(final int packetType, final Class<?> packetHandler) {
		if (packetHandler == null) {
			throw new IllegalArgumentException("packetHandler cannot be null");
		}

		try {
			m_packetHandlers.put(Integer.valueOf(packetType), (PacketHandler) packetHandler.newInstance());
		} catch (final InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		final Predicate<String> filter = new FilterBuilder().include("pl\\.radical\\.open\\.gg\\.packet\\.in.*");
		final Configuration configuration = new ConfigurationBuilder()
		.filterInputsBy(filter)
		.setScanners(new TypeAnnotationsScanner())
		.setUrls(ClasspathHelper.getUrlForClass(IncomingPacket.class));
		final Reflections reflections = new Reflections(configuration);

		final Set<Class<?>> classes = reflections.getTypesAnnotatedWith(IncomingPacket.class);

		if (classes.size() == 0) {
			logger.error("Nie znalazłem żadnych klas do rejestracji!");
		}

		for (final Class<?> c : classes) {
			final String incomingClass = c.getName();
			final IncomingPacket annotation = c.getAnnotation(IncomingPacket.class);
			final Class<?> handler = c.getAnnotation(IncomingPacket.class).handler();

			if (logger.isDebugEnabled()) {
				logger.debug("Registering class {} with handler {}", incomingClass, handler.getName());
			}

			registerGGPackageHandler(annotation.type(), handler);
		}

		// registerGGPackageHandler(GGWelcome.GG_PACKAGE_WELCOME, new GGWelcomePacketHandler());
		// registerGGPackageHandler(GGLoginOK.GG_LOGIN_OK, new GGLoginOKPacketHandler());
		// registerGGPackageHandler(GGLogin80OK.GG_LOGIN80_OK, new GGLogin80OKPacketHandler());
		// registerGGPackageHandler(GGLoginFailed.GG_LOGIN_FAILED, new GGLoginFailedPacketHandler());
		// registerGGPackageHandler(GGNeedEmail.GG_NEED_EMAIL, new GGNeedEmailPacketHandler());
		// registerGGPackageHandler(GGStatus.GG_STATUS, new GGStatusPacketHandler());
		// registerGGPackageHandler(GGStatus60.GG_STATUS60, new GGStatus60PacketHandler());
		// registerGGPackageHandler(GGNotifyReply.GG_NOTIFY_REPLY, new GGNotifyReplyPacketHandler());
		// registerGGPackageHandler(GGNotifyReply60.GG_NOTIFY_REPLY60, new GGNotifyReply60PacketHandler());
		// registerGGPackageHandler(GGRecvMsg.GG_RECV_MSG, new GGMessageReceivedPacketHandler());
		// registerGGPackageHandler(GGSendMsgAck.GG_SEND_MSG_ACK, new GGSentMessageAckPacketHandler());
		// registerGGPackageHandler(GGUserListReply.GG_USERLIST_REPLY, new GGUserListReplyHandler());
		// registerGGPackageHandler(GGPubdirReply.GG_PUBDIR50_REPLY, new GGPubdirReplyPacketHandler());
		// registerGGPackageHandler(GGDisconnecting.GG_DISCONNECTING, new GGDisconnectingPacketHandler());
		// registerGGPackageHandler(GGPong.GG_PONG, new GGPongPacketHandler());
	}
}
