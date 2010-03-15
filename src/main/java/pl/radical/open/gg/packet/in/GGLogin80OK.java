package pl.radical.open.gg.packet.in;

import pl.radical.open.gg.packet.GGIncomingPackage;
import pl.radical.open.gg.packet.GGBaseIncomingPacket;
import pl.radical.open.gg.packet.IncomingPacket;
import pl.radical.open.gg.packet.handlers.GGLogin80OKPacketHandler;

/**
 * Created on 2004-12-11
 * 
 * @author <a href="mailto:lukasz.rzanek@radical.com.pl>Łukasz Rżanek</a>
 * @since 1.6.9.0
 */
@IncomingPacket(type = 0x0035, handler = GGLogin80OKPacketHandler.class)
public class GGLogin80OK extends GGBaseIncomingPacket implements GGIncomingPackage {

	private static GGLogin80OK m_instance = null;

	private GGLogin80OK() {
		// prevent instant
	}

	public static GGLogin80OK getInstance() {
		if (m_instance == null) {
			m_instance = new GGLogin80OK();
		}
		return m_instance;
	}

}
