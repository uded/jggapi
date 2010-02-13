package pl.radical.open.gg.packet.in;

/**
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class GGDisconnecting implements GGIncomingPackage {

	public static final int GG_DISCONNECTING = 0x000B;

	private static GGDisconnecting m_instance = null;

	private GGDisconnecting() {
		// private constructor
	}

	public static GGDisconnecting getInstance() {
		if (m_instance == null) {
			m_instance = new GGDisconnecting();
		}
		return m_instance;
	}

	/**
	 * @see pl.radical.open.gg.packet.in.GGIncomingPackage#getPacketType()
	 */
	public int getPacketType() {
		return GG_DISCONNECTING;
	}

}
