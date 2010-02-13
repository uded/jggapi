package pl.radical.open.gg.packet.in;

/**
 * Created on 2004-11-28
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class GGPong implements GGIncomingPackage {

	public final static int GG_PONG = 0x0007;

	private static GGPong m_instance = null;

	private GGPong() {
		// private contructor
	}

	public static GGPong getInstance() {
		if (m_instance == null) {
			m_instance = new GGPong();
		}
		return m_instance;
	}

	/**
	 * @see pl.radical.open.gg.packet.in.GGIncomingPackage#getPacketType()
	 */
	public int getPacketType() {
		return GG_PONG;
	}

}
