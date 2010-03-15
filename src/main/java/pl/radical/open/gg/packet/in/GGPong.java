package pl.radical.open.gg.packet.in;

import pl.radical.open.gg.packet.GGIncomingPackage;
import pl.radical.open.gg.packet.GGBaseIncomingPacket;
import pl.radical.open.gg.packet.IncomingPacket;
import pl.radical.open.gg.packet.handlers.GGPongPacketHandler;

/**
 * Created on 2004-11-28
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @author <a href="mailto:lukasz.rzanek@radical.com.pl>Łukasz Rżanek</a>
 */
@IncomingPacket(type = 0x0007, handler = GGPongPacketHandler.class)
@Deprecated
public class GGPong extends GGBaseIncomingPacket implements GGIncomingPackage {

	private static GGPong m_instance = null;

	private GGPong() {
		// private contructor
	}

	public static GGPong getInstance() {
		if (m_instance == null) {
			m_instance = new GGPong();
		}
		return m_instance;
	}

}
