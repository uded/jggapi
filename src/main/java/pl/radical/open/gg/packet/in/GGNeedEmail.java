package pl.radical.open.gg.packet.in;

import pl.radical.open.gg.packet.GGBaseIncomingPacket;
import pl.radical.open.gg.packet.GGIncomingPackage;
import pl.radical.open.gg.packet.IncomingPacket;
import pl.radical.open.gg.packet.handlers.GGNeedEmailPacketHandler;

/**
 * Created on 2004-12-11
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @author <a href="mailto:lukasz.rzanek@radical.com.pl>Łukasz Rżanek</a>
 */
@IncomingPacket(type = 0x0014, label = "GG_NEED_EMAIL", handler = GGNeedEmailPacketHandler.class)
public class GGNeedEmail extends GGBaseIncomingPacket implements GGIncomingPackage {
	private static GGNeedEmail m_instance = null;

	private GGNeedEmail() {
		// prevent instant
	}

	public static GGNeedEmail getInstance() {
		if (m_instance == null) {
			m_instance = new GGNeedEmail();
		}
		return m_instance;
	}

}
