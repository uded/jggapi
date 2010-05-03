package pl.radical.open.gg.packet.out;

import pl.radical.open.gg.User;
import pl.radical.open.gg.packet.GGOutgoingPackage;
import pl.radical.open.gg.packet.OutgoingPacket;
import pl.radical.open.gg.packet.dicts.GGUser;
import pl.radical.open.gg.utils.GGConversion;
import pl.radical.open.gg.utils.GGUtils;

/**
 * <pre>
 * struct gg_add_notify {
 * 	int uin;	&laquo; numerek &raquo;
 * 	char type;	&laquo; rodzaj użytkownika &raquo;
 * };
 * </pre>
 * 
 * @see pl.radical.open.gg.packet.in.GGNotifyReply
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
@OutgoingPacket(type = 0x00d, label = "GG_ADD_NOTIFY")
public class GGAddNotify implements GGOutgoingPackage, GGUser {

	public final int GG_ADD_NOTIFY = 0x000d;

	/** Gadu-Gadu uin number */
	private int uin = -1;

	private User.UserMode userMode = User.UserMode.BUDDY;

	private byte userType = GG_USER_BUDDY;

	public GGAddNotify(final int uin, final User.UserMode userMode) {
		if (userMode == null) {
			throw new IllegalArgumentException("userMode cannot be null");
		}
		if (uin < 0) {
			throw new IllegalArgumentException("uin cannot be less than 0");
		}
		this.uin = uin;
		this.userMode = userMode;
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
		return GG_ADD_NOTIFY;
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
		final byte[] data = new byte[getLength()];

		final byte[] uinRaw = GGUtils.intToByte(this.uin);
		System.arraycopy(uinRaw, 0, data, 0, uinRaw.length);

		data[4] = userType;
		return data;
	}

}
