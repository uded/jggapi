package pl.radical.open.gg.packet.out;

import pl.radical.open.gg.ILocalStatus;
import pl.radical.open.gg.packet.GGOutgoingPackage;
import pl.radical.open.gg.packet.OutgoingPacket;
import pl.radical.open.gg.packet.dicts.GGStatuses;
import pl.radical.open.gg.utils.GGConversion;
import pl.radical.open.gg.utils.GGUtils;

import org.apache.commons.collections.primitives.ArrayByteList;
import org.apache.commons.collections.primitives.ByteList;

/**
 * Packet that sets new status of the Gadu-Gadu user.
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
@OutgoingPacket(type = 0x002, label = "GG_NEW_STATUS")
public class GGNewStatus implements GGOutgoingPackage, GGStatuses {

	public static final int GG_NEW_STATUS = 0x0002;

	private static final int MAX_DESCRIPTION = 70;

	private ILocalStatus localStatus = null;

	/**
	 * The protocol status constructor.
	 */
	public GGNewStatus(final ILocalStatus localStatus) {
		if (localStatus == null) {
			throw new IllegalArgumentException("status cannot be null");
		}
		this.localStatus = localStatus;
	}

	/**
	 * @see pl.radical.open.gg.packet.GGOutgoingPackage#getPacketType()
	 */
	public int getPacketType() {
		return GG_NEW_STATUS;
	}

	/**
	 * @see pl.radical.open.gg.packet.GGOutgoingPackage#getLength()
	 */
	public int getLength() {
		int length = 4;

		if (localStatus.getStatusType().isDescriptionStatus() && localStatus.isDescriptionSet()) {
			length += localStatus.getDescription().length() + 1;
			if (localStatus.isReturnDateSet()) {
				length += 4;
			}
		}

		return length;
	}

	/**
	 * @see pl.radical.open.gg.packet.GGOutgoingPackage#getContents()
	 */
	public byte[] getContents() {
		final int statusToSend = GGConversion.getProtocolStatus(localStatus, localStatus.isFriendsOnly(), false);

		final ByteList byteList = new ArrayByteList(getLength());

		byteList.add((byte) (statusToSend & 0xFF));
		byteList.add((byte) (statusToSend >> 8 & 0xFF));
		byteList.add((byte) (statusToSend >> 16 & 0xFF));
		byteList.add((byte) (statusToSend >> 24 & 0xFF));

		if (localStatus.getStatusType().isDescriptionStatus() && localStatus.isDescriptionSet()) {
			final String description = trimDescription(localStatus.getDescription());
			final byte[] descBytes = description.getBytes();
			for (final byte descByte : descBytes) {
				byteList.add(descByte);
			}
			if (localStatus.isReturnDateSet()) {
				final int timeInSeconds = GGUtils.millisToSeconds(localStatus.getReturnDate().getTime());
				byteList.add((byte) (timeInSeconds & 0xFF));
				byteList.add((byte) (timeInSeconds >> 8 & 0xFF));
				byteList.add((byte) (timeInSeconds >> 16 & 0xFF));
				byteList.add((byte) (timeInSeconds >> 24 & 0xFF));
			}
		}
		return byteList.toArray();
	}

	private String trimDescription(String description) {
		if (description != null && description.length() > MAX_DESCRIPTION) {
			return description.substring(0, MAX_DESCRIPTION - 1);
		} else {
		return description;
		}
	}

}
