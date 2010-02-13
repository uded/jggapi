package pl.radical.open.gg.packet.in;

/**
 * Created on 2004-12-11
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class GGLoginFailed implements GGIncomingPackage {

	public final static int GG_LOGIN_FAILED = 9;

	private static GGLoginFailed m_instance = null;

	private GGLoginFailed() {
		// prevent instant
	}

	public static GGLoginFailed getInstance() {
		if (m_instance == null) {
			m_instance = new GGLoginFailed();
		}
		return m_instance;
	}

	/**
	 * @see pl.radical.open.gg.packet.in.GGIncomingPackage#getPacketType()
	 */
	public int getPacketType() {
		return GG_LOGIN_FAILED;
	}

}
