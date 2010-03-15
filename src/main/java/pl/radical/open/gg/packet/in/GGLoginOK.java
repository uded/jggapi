package pl.radical.open.gg.packet.in;

import pl.radical.open.gg.packet.GGIncomingPackage;
import pl.radical.open.gg.packet.GGBaseIncomingPacket;
import pl.radical.open.gg.packet.IncomingPacket;
import pl.radical.open.gg.packet.handlers.GGLoginOKPacketHandler;

/**
 * Created on 2004-12-11
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @author <a href="mailto:lukasz.rzanek@radical.com.pl>Łukasz Rżanek</a>
 */
@IncomingPacket(type = 0x0003, handler = GGLoginOKPacketHandler.class)
@Deprecated
public class GGLoginOK extends GGBaseIncomingPacket implements GGIncomingPackage {
	private static GGLoginOK m_instance = null;

	private GGLoginOK() {
		// prevent instant
	}

	public static GGLoginOK getInstance() {
		if (m_instance == null) {
			m_instance = new GGLoginOK();
		}
		return m_instance;
	}

}
