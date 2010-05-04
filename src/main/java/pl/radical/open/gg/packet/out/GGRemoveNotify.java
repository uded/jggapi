package pl.radical.open.gg.packet.out;

import pl.radical.open.gg.User;
import pl.radical.open.gg.packet.GGOutgoingPackage;
import pl.radical.open.gg.packet.OutgoingPacket;
import pl.radical.open.gg.packet.dicts.GGUser;
import pl.radical.open.gg.utils.GGConversion;
import pl.radical.open.gg.utils.GGUtils;

/**
 * Packet that deletes certain user from the list of monitored users.<BR>
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
@OutgoingPacket(type = 0x000e, label = "GG_REMOVE_NOTIFY")
public class GGRemoveNotify implements GGOutgoingPackage, GGUser {

	public static final int GG_REMOVE_NOTIFY = 0x000E;

	/**
	 * Gadu-Gadu uin
	 */
	private int uin = -1;

	private User.UserMode userMode = null;
	private byte userType = GG_USER_BUDDY;

	public GGRemoveNotify(final int uin, final User.UserMode userMode) {
		if (uin < 0) {
			throw new IllegalArgumentException("uin cannot be less than 0");
		}
		if (userMode == null) {
			throw new IllegalArgumentException("userMode cannot be null");
		}
		this.uin = uin;
		userType = GGConversion.getProtocolUserMode(userMode);
	}

	public int getUin() {
		return uin;
	}

	public User.UserMode getUserMode() {
		return userMode;
	}

	/**
	 * @see pl.radical.open.gg.packet.GGOutgoingPackage#getPacketType()
	 */
	public int getPacketType() {
		return GG_REMOVE_NOTIFY;
	}

	/**
	 * @see pl.radical.open.gg.packet.GGOutgoingPackage#getLength()
	 */
	public int getLength() {
		return 5;
	}

	/**
	 * @see pl.radical.open.gg.packet.GGOutgoingPackage#getContents()
	 */
	public byte[] getContents() {
		final byte[] dane = new byte[getLength()];

		final byte[] uinRaw = GGUtils.intToByte(uin);
		System.arraycopy(uinRaw, 0, dane, 0, uinRaw.length);

		dane[4] = userType;

		return dane;
	}

}
