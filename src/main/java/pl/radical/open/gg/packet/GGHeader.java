package pl.radical.open.gg.packet;

import pl.radical.open.gg.utils.GGUtils;

/**
 * Gadu-Gadu packet header.
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class GGHeader {

	/** Gadu-Gadu packet type */
	private int m_type = -1;

	/** Gadu-Gadu packet length */
	private int m_length = -1;

	/**
	 * Constructor for Header.
	 * 
	 * @param data
	 *            dane naglowka
	 */
	public GGHeader(final byte[] data) {
		m_type = GGUtils.byteToInt(data, 0);
		m_length = GGUtils.byteToInt(data, 4);
	}

	/**
	 * Returns the type.
	 * 
	 * @return int packet type.
	 */
	public int getType() {
		return m_type;
	}

	/**
	 * Returns the length of packet.
	 * 
	 * @return int packet length.
	 */
	public int getLength() {
		return m_length;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[PacketType: " + m_type + ", packetLength: " + m_length + "]";
	}

}
