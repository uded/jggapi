package pl.radical.open.gg.packet.in;

import pl.radical.open.gg.packet.GGUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The packet is retrieved from the Gadu-Gadu server just after we connect to it. The class parses package and gets seed
 * from server.
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @author <a href="mailto:lukasz.rzanek@radical.com.pl>Łukasz Rżanek</a>
 */
public class GGWelcome implements GGIncomingPackage {
	private final Logger log = LoggerFactory.getLogger(getClass());

	public static final int GG_PACKAGE_WELCOME = 0x1;

	private int m_seed = -1;

	public GGWelcome(final byte[] data) {
		if (data == null) {
			log.error("Was expecting a data in constructor, got null");
			throw new IllegalArgumentException("data cannot be null");
		}
		if (log.isTraceEnabled()) {
			log.trace("Seed to be used in this connection: {}", m_seed);
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
