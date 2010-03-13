package pl.radical.open.gg.packet.in;

import pl.radical.open.gg.packet.handlers.GGLogin80OKPacketHandler;

/**
 * Created on 2004-12-11
 * 
 * @author <a href="mailto:lukasz.rzanek@radical.com.pl>Łukasz Rżanek</a>
 */
@IncomingPacketHandler(GGLogin80OKPacketHandler.class)
public class GGLogin80OK implements GGIncomingPackage {

	private static GGLogin80OK m_instance = null;

	public final static int GG_LOGIN80_OK = 0x0035;

	private GGLogin80OK() {
		// prevent instant
	}

	/**
	 * @see pl.radical.open.gg.packet.in.GGIncomingPackage#getPacketType()
	 */
	public int getPacketType() {
		return GG_LOGIN80_OK;
	}

	public static GGLogin80OK getInstance() {
		if (m_instance == null) {
			m_instance = new GGLogin80OK();
		}
		return m_instance;
	}

}
