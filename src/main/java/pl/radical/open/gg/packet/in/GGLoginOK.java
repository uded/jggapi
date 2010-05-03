package pl.radical.open.gg.packet.in;

import pl.radical.open.gg.packet.AbstractGGIncomingPacket;
import pl.radical.open.gg.packet.GGIncomingPackage;
import pl.radical.open.gg.packet.IncomingPacket;
import pl.radical.open.gg.packet.handlers.GGLoginOKPacketHandler;

/**
 * Created on 2004-12-11
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @author <a href="mailto:lukasz.rzanek@radical.com.pl>Łukasz Rżanek</a>
 */
@IncomingPacket(type = 0x0003, label = "GG_LOGIN_OK", handler = GGLoginOKPacketHandler.class)
@Deprecated
public class GGLoginOK extends AbstractGGIncomingPacket implements GGIncomingPackage {
	private static GGLoginOK instance = null;

	private GGLoginOK() {
		// prevent instant
	}

	public static GGLoginOK getInstance() {
		if (instance == null) {
			instance = new GGLoginOK();
		}
		return instance;
	}

}
