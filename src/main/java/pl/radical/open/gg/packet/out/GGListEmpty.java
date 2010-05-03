package pl.radical.open.gg.packet.out;

import pl.radical.open.gg.packet.GGOutgoingPackage;
import pl.radical.open.gg.packet.OutgoingPacket;

/**
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
@OutgoingPacket(type = 0x0012, label = "GG_LIST_EMPTY")
public class GGListEmpty implements GGOutgoingPackage {

	public static final int GG_LIST_EMPTY = 0x0012;

	private static GGListEmpty instance = null;

	private static byte[] data = new byte[0];

	private GGListEmpty() { // private constructor
	}

	// this method is not thread-safe, because this is check and act
	// and it is not protected against race-condition
	public static GGListEmpty getInstance() {
		if (instance == null) {
			instance = new GGListEmpty();
		}
		return instance;
	}

	/**
	 * @see pl.radical.open.gg.packet.GGOutgoingPackage#getPacketType()
	 */
	public int getPacketType() {
		return GG_LIST_EMPTY;
	}

	/**
	 * @see pl.radical.open.gg.packet.GGOutgoingPackage#getLength()
	 */
	public int getLength() {
		return data.length;
	}

	/**
	 * @see pl.radical.open.gg.packet.GGOutgoingPackage#getContents()
	 */
	public byte[] getContents() {
		return data;
	}

}
