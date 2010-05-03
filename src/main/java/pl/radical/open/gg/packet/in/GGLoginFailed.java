package pl.radical.open.gg.packet.in;

import pl.radical.open.gg.packet.GGBaseIncomingPacket;
import pl.radical.open.gg.packet.GGIncomingPackage;
import pl.radical.open.gg.packet.IncomingPacket;
import pl.radical.open.gg.packet.handlers.GGLoginFailedPacketHandler;

/**
 * Created on 2004-12-11
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @author <a href="mailto:lukasz.rzanek@radical.com.pl>Łukasz Rżanek</a>
 */
@IncomingPacket(type = 0x0009, label = "GG_LOGIN_FAILED", handler = GGLoginFailedPacketHandler.class)
public class GGLoginFailed extends GGBaseIncomingPacket implements GGIncomingPackage {
	private static GGLoginFailed instance = null;

	private GGLoginFailed() {
		// prevent instant
	}

	public static GGLoginFailed getInstance() {
		if (instance == null) {
			instance = new GGLoginFailed();
		}
		return instance;
	}
}
