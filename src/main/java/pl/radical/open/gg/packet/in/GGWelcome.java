package pl.radical.open.gg.packet.in;

import pl.radical.open.gg.packet.GGUtils;

/**
 * The packet is retrieved from the Gadu-Gadu server just after we connect to it. The class parses package and gets seed
 * from server.
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class GGWelcome implements GGIncomingPackage {

	public static final int GG_PACKAGE_WELCOME = 0x1;

	private int m_seed = -1;

	public GGWelcome(final byte[] data) {
		if (data == null) {
			throw new NullPointerException("data cannot be null");
		}
		m_seed = GGUtils.byteToInt(data);
	}

	/**
	 * @see pl.radical.open.gg.packet.in.GGIncomingPackage#getPacketType()
	 */
	public int getPacketType() {
		return GG_PACKAGE_WELCOME;
	}

	public int getSeed() {
		return m_seed;
	}

}
