package pl.radical.open.gg.packet.out;

import pl.radical.open.gg.IUser;
import pl.radical.open.gg.packet.GGOutgoingPackage;
import pl.radical.open.gg.packet.dicts.GGUser;
import pl.radical.open.gg.utils.GGConversion;
import pl.radical.open.gg.utils.GGUtils;

import java.util.Arrays;

/**
 * @see pl.radical.open.gg.packet.in.GGNotifyReply
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class GGNotify implements GGOutgoingPackage, GGUser {

	public static final int GG_NOTIFY_FIRST = 0x0F;
	public static final int GG_NOTIFY_LAST = 0x10;

	private IUser[] users = new IUser[0];

	public GGNotify(final IUser[] users) {
		if (users == null) {
			throw new IllegalArgumentException("users cannot be null");
		}
		this.users = Arrays.copyOf(users, users.length);
	}

	/**
	 * @see pl.radical.open.gg.packet.GGOutgoingPackage#getPacketType()
	 */
	public int getPacketType() {
		return GG_NOTIFY_LAST;
	}

	/**
	 * @see pl.radical.open.gg.packet.GGOutgoingPackage#getLength()
	 */
	public int getLength() {
		return users.length * 5;
	}

	/**
	 * @see pl.radical.open.gg.packet.GGOutgoingPackage#getContents()
	 */
	public byte[] getContents() {
		final byte[] toSend = new byte[getLength()];

		for (int i = 0; i < users.length; i++) {
			final IUser user = users[i];
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
