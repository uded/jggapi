package pl.radical.open.gg.packet.in;

/**
 * Created on 2004-12-11
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class GGNeedEmail implements GGIncomingPackage {

	private static GGNeedEmail m_instance = null;

	public final static int GG_NEED_EMAIL = 0x14;

	private GGNeedEmail() {
		// prevent instant
	}

	/**
	 * @see pl.radical.open.gg.packet.in.GGIncomingPackage#getPacketType()
	 */
	public int getPacketType() {
		return GG_NEED_EMAIL;
	}

	public static GGNeedEmail getInstance() {
		if (m_instance == null) {
			m_instance = new GGNeedEmail();
		}
		return m_instance;
	}

}
