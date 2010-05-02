package pl.radical.open.gg.packet.out;

import pl.radical.open.gg.GGNullPointerException;
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
	private int m_uin = -1;

	private User.UserMode m_userMode = User.UserMode.BUDDY;

	private byte m_userType = GG_USER_BUDDY;

	public GGAddNotify(final int uin, final User.UserMode userMode) {
		if (userMode == null) {
			throw new GGNullPointerException("userMode cannot be null");
		}
		if (uin < 0) {
			throw new IllegalArgumentException("uin cannot be less than 0");
		}
		m_uin = uin;
		m_userMode = userMode;
		m_userType = GGConversion.getProtocolUserMode(userMode);
	}

	public int getUin() {
		return m_uin;
	}

	public User.UserMode getUserMode() {
		return m_userMode;
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

		final byte[] uin = GGUtils.intToByte(m_uin);
		System.arraycopy(uin, 0, data, 0, uin.length);

		data[4] = m_userType;
		return data;
	}

}
