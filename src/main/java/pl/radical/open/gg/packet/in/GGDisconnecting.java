package pl.radical.open.gg.packet.in;

import pl.radical.open.gg.packet.handlers.GGDisconnectingPacketHandler;

/**
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @author <a href="mailto:lukasz.rzanek@radical.com.pl>Łukasz Rżanek</a>
 */
@IncomingPacket(type = 0x000b, handler = GGDisconnectingPacketHandler.class)
public class GGDisconnecting {

	private static GGDisconnecting m_instance = null;

	private GGDisconnecting() {
		// private constructor
	}

	public static GGDisconnecting getInstance() {
		if (m_instance == null) {
			m_instance = new GGDisconnecting();
		}
		return m_instance;
	}
}
