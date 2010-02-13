package pl.radical.open.gg.packet.out;

/**
 * Outgoing packet, ping type that is from time to time being send to Gadu-Gadu server.
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public final class GGPing implements GGOutgoingPackage {

	public static final int GG_PING = 0x08;

	private static byte[] m_data = null;

	private static GGPing m_ping = null;

	/**
	 * Prywatny konstruktor.
	 */
	private GGPing() {
		m_data = new byte[0];
	}

	public static GGPing getPing() {
		if (m_ping == null) {
			m_ping = new GGPing();
		}
		return m_ping;
	}

	/**
	 * @see pl.radical.open.gg.packet.out.GGOutgoingPackage#getPacketType()
	 */
	public int getPacketType() {
		return GG_PING;
	}

	/**
	 * @see pl.radical.open.gg.packet.out.GGOutgoingPackage#getLength()
	 */
	public int getLength() {
		return m_data.length;
	}

	/**
	 * @see pl.radical.open.gg.packet.out.GGOutgoingPackage#getContents()
	 */
	public byte[] getContents() {
		return m_data;
	}

}
