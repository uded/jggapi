package pl.radical.open.gg.packet.in;

import pl.radical.open.gg.packet.handlers.GGLoginFailedPacketHandler;

/**
 * Created on 2004-12-11
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @author <a href="mailto:lukasz.rzanek@radical.com.pl>Łukasz Rżanek</a>
 */
@IncomingPacket(type = 0x0009, handler = GGLoginFailedPacketHandler.class)
public class GGLoginFailed {
	private static GGLoginFailed m_instance = null;

	private GGLoginFailed() {
		// prevent instant
	}

	public static GGLoginFailed getInstance() {
		if (m_instance == null) {
			m_instance = new GGLoginFailed();
		}
		return m_instance;
	}
}
