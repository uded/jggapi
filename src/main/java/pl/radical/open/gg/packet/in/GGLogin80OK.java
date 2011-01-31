package pl.radical.open.gg.packet.in;

import pl.radical.open.gg.packet.AbstractGGIncomingPacket;
import pl.radical.open.gg.packet.GGIncomingPackage;
import pl.radical.open.gg.packet.IncomingPacket;
import pl.radical.open.gg.packet.handlers.GGLogin80OKPacketHandler;

/**
 * @author <a href="mailto:lukasz.rzanek@radical.com.pl>Łukasz Rżanek</a>
 * @since 1.6.9.0
 * @since 2010-03-13
 */
@IncomingPacket(type = 0x0035, label = "GG_LOGIN_OK80", handler = GGLogin80OKPacketHandler.class)
public final class GGLogin80OK extends AbstractGGIncomingPacket implements GGIncomingPackage {

	private static GGLogin80OK instance = null;

	private GGLogin80OK() {
		// prevent instant
	}

	public static GGLogin80OK getInstance() {
		if (instance == null) {
			instance = new GGLogin80OK();
		}
		return instance;
	}

}
