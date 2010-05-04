package pl.radical.open.gg.packet.in;

import pl.radical.open.gg.IUser;
import pl.radical.open.gg.RemoteStatus;
import pl.radical.open.gg.User;
import pl.radical.open.gg.packet.AbstractGGIncomingPacket;
import pl.radical.open.gg.packet.GGIncomingPackage;
import pl.radical.open.gg.packet.IncomingPacket;
import pl.radical.open.gg.packet.dicts.GGStatuses;
import pl.radical.open.gg.packet.handlers.GGNotifyReply60PacketHandler;
import pl.radical.open.gg.utils.GGConversion;
import pl.radical.open.gg.utils.GGUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
@IncomingPacket(type = 0x0011, label = "GG_NOTIFY_REPLY60", handler = GGNotifyReply60PacketHandler.class)
@Deprecated
public class GGNotifyReply60 extends AbstractGGIncomingPacket implements GGIncomingPackage {

	private final Map<IUser, RemoteStatus> statuses = new HashMap<IUser, RemoteStatus>();

	public GGNotifyReply60(final byte[] data) {
		handlePacket(data);
	}

	public Map<IUser, RemoteStatus> getUsersStatus() {
		return statuses;
	}

	private void handlePacket(final byte[] data) {
		int offset = 0;
		while (offset < data.length) {
			final int flag = data[offset + 3];
			data[offset + 3] = GGUtils.intToByte(0)[0];

			final int uin = GGUtils.byteToInt(data, offset);
			final int status = GGUtils.unsignedByteToInt(data[offset + 4]);
			final User.UserMode userMode = GGConversion.getUserMode(status);

			final int remoteIP = GGUtils.byteToInt(data, offset + 5);
			final byte[] remoteIPArray = GGUtils.convertIntToByteArray(remoteIP);
			final int remotePort = GGUtils.byteToShort(data, offset + 9);
			final int version = GGUtils.unsignedByteToInt(data[offset + 11]);
			final int imageSize = GGUtils.unsignedByteToInt(data[offset + 12]);
			String description = null;
			int descriptionSize = -1;
			long timeInMillis = -1;
			if (status == GGStatuses.GG_STATUS_AVAIL_DESCR || status == GGStatuses.GG_STATUS_BUSY_DESCR || status == GGStatuses.GG_STATUS_INVISIBLE_DESCR || status == GGStatuses.GG_STATUS_NOT_AVAIL_DESCR) {
				descriptionSize = GGUtils.unsignedByteToInt(data[offset + 14]);

				final byte[] descBytes = new byte[descriptionSize];
				System.arraycopy(data, offset + 15, descBytes, 0, descriptionSize);
				description = GGUtils.byteToString(descBytes, 0);

				final boolean isTimeSet = data[offset + 15 + descriptionSize - 5] == 0;

				if (isTimeSet) {
					final int timeInSeconds = GGUtils.byteToInt(data, offset + 15 + descriptionSize - 4);
					timeInMillis = GGUtils.secondsToMillis(timeInSeconds);
					descriptionSize -= 5;
				}

				offset += 15 + descriptionSize;

				if (isTimeSet) {
					offset += 5;
				}
			} else {
				offset += 14; // packet without description is only 14 bytes long
			}
			final IUser user = new User(uin, userMode);
			final RemoteStatus status60 = GGConversion.getClientRemoteStatus(status, description, timeInMillis);

			if (remotePort == 0) {
				status60.setSupportsDirectCommunication(false);
			} else if (remotePort == 1) {
				status60.setUserBehindFirewall(true);
			} else if (remotePort == 2) {
				status60.setInRemoteUserBuddyList(false);
			} else {
				status60.setRemotePort(remotePort);
			}

			status60.setRemoteIP(remoteIPArray);
			status60.setImageSize(imageSize);
			status60.setGGVersion(version);

			if (descriptionSize > 0) {
				status60.setDescriptionSize(descriptionSize);
			}

			if (flag == 0x40) {
				status60.setSupportsVoiceCommunication(true);
			}

			statuses.put(user, status60);
		}
	}

}
