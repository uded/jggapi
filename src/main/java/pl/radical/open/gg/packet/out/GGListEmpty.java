package pl.radical.open.gg.packet.out;

/**
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class GGListEmpty implements GGOutgoingPackage {

	public static final int GG_EMPTY_LIST = 0x0012;

	private static GGListEmpty m_instance = null;

	private static byte[] m_data = null;

	private GGListEmpty() { // private constructor
		m_data = new byte[0];
	}

	// this method is not thread-safe, because this is check and act
	// and it is not protected against race-condition
	public static GGListEmpty getInstance() {
		if (m_instance == null) {
			m_instance = new GGListEmpty();
		}
		return m_instance;
	}

	/**
	 * @see pl.radical.open.gg.packet.out.GGOutgoingPackage#getPacketType()
	 */
	public int getPacketType() {
		return GG_EMPTY_LIST;
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
