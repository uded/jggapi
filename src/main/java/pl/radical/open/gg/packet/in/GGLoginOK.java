package pl.radical.open.gg.packet.in;

/**
 * Created on 2004-12-11
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class GGLoginOK implements GGIncomingPackage {

	private static GGLoginOK m_instance = null;

	public final static int GG_LOGIN_OK = 0x03;

	private GGLoginOK() {
		// prevent instant
	}

	/**
	 * @see pl.radical.open.gg.packet.in.GGIncomingPackage#getPacketType()
	 */
	public int getPacketType() {
		return GG_LOGIN_OK;
	}

	public static GGLoginOK getInstance() {
		if (m_instance == null) {
			m_instance = new GGLoginOK();
		}
		return m_instance;
	}

}
