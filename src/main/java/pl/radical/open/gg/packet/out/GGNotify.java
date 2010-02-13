package pl.radical.open.gg.packet.out;

import pl.radical.open.gg.IUser;
import pl.radical.open.gg.packet.GGConversion;
import pl.radical.open.gg.packet.GGUser;
import pl.radical.open.gg.packet.GGUtils;

/**
 * @see pl.radical.open.gg.packet.in.GGNotifyReply
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class GGNotify implements GGOutgoingPackage, GGUser {

	public static final int GG_NOTIFY_FIRST = 0x0F;
	public static final int GG_NOTIFY_LAST = 0x10;

	private IUser[] m_users = new IUser[0];

	public GGNotify(final IUser[] users) {
		if (users == null) {
			throw new NullPointerException("users cannot be null");
		}
		m_users = users;
	}

	/**
	 * @see pl.radical.open.gg.packet.out.GGOutgoingPackage#getPacketType()
	 */
	public int getPacketType() {
		return GG_NOTIFY_LAST;
	}

	/**
	 * @see pl.radical.open.gg.packet.out.GGOutgoingPackage#getLength()
	 */
	public int getLength() {
		return m_users.length * 5;
	}

	/**
	 * @see pl.radical.open.gg.packet.out.GGOutgoingPackage#getContents()
	 */
	public byte[] getContents() {
		final byte[] toSend = new byte[getLength()];

		for (int i = 0; i < m_users.length; i++) {
			final IUser user = m_users[i];
			final byte[] uinByte = GGUtils.intToByte(user.getUin());

			for (int j = 0; j < uinByte.length; j++) {
				toSend[i * 5 + j] = uinByte[j];
			}

			final byte protocolUserMode = GGConversion.getProtocolUserMode(user.getUserMode());

			toSend[i * 5 + 4] = protocolUserMode;
		}

		return toSend;
	}

}
