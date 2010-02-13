package pl.radical.open.gg.packet.in;

import pl.radical.open.gg.RemoteStatus;
import pl.radical.open.gg.User;
import pl.radical.open.gg.packet.GGConversion;
import pl.radical.open.gg.packet.GGStatuses;
import pl.radical.open.gg.packet.GGUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class GGNotifyReply implements GGIncomingPackage {

	public static final int GG_NOTIFY_REPLY = 0x000C;

	// struct gg_notify_reply {
	// int uin; /* numer */
	// char status; /* status danej osoby */
	// int remote_ip; /* adres ip delikwenta */
	// short remote_port; /* port, na którym słucha klient */
	// int version; /* wersja klienta */
	// short unknown1; /* znowu port? */
	// char description[]; /* opis, nie musi wystąpić */
	// int time; /* czas, nie musi wystąpić */
	// };

	private final Map<User, RemoteStatus> m_statuses = new HashMap<User, RemoteStatus>();

	/**
	 * @param data
	 *            dane do utworzenia pakietu
	 */
	public GGNotifyReply(final byte[] data) {
		analize(data);
	}

	public int getPacketType() {
		return GG_NOTIFY_REPLY;
	}

	public Map<User, RemoteStatus> getUsersStatus() {
		return m_statuses;
	}

	private void analize(final byte[] data) {
		int offset = 0;

		while (offset < data.length) {
			data[offset + 3] = GGUtils.intToByte(0)[0];
			final int uin = GGUtils.byteToInt(data, offset);
			final int status = GGUtils.unsignedByteToInt(data[offset + 4]);
			final int remoteIP = GGUtils.byteToInt(data, offset + 5);
			final byte[] remoteIPByte = GGUtils.intToByte(remoteIP);
			final int remotePort = GGUtils.byteToShort(data, offset + 9);
			final int version = GGUtils.byteToInt(data, offset + 11);

			String description = null;
			long timeInMillis = -1;
			if (status == GGStatuses.GG_STATUS_AVAIL_DESCR || status == GGStatuses.GG_STATUS_BUSY_DESCR || status == GGStatuses.GG_STATUS_INVISIBLE_DESCR || status == GGStatuses.GG_STATUS_NOT_AVAIL_DESCR) {
				int descriptionSize = GGUtils.unsignedByteToInt(data[offset + 14]);

				final boolean isTime = data[offset + 15 + descriptionSize - 5] == 0;

				if (isTime) {
					final int timeInSeconds = GGUtils.byteToInt(data, offset + 15 + descriptionSize - 4);
					timeInMillis = GGUtils.secondsToMillis(timeInSeconds);
					descriptionSize -= 5;
				}

				final byte[] descriptionBytes = new byte[descriptionSize];
				System.arraycopy(data, offset + 15, descriptionBytes, 0, descriptionSize);
				description = new String(descriptionBytes);

				offset += 15 + descriptionSize;

				if (isTime) {
					offset += 5;
				}
			} else {
				offset += 14; // the packet without description is 14 bytes long.
			}

			final RemoteStatus statusBiz = GGConversion.getClientRemoteStatus(status, description, timeInMillis);

			statusBiz.setRemoteIP(remoteIPByte);
			statusBiz.setRemotePort(remotePort);
			statusBiz.setGGVersion(version);

			final User.UserMode userMode = GGConversion.getUserMode(status);
			final User user = new User(uin, userMode);
			m_statuses.put(user, statusBiz);
		}
	}

}
