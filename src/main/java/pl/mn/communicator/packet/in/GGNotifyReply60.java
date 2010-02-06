/*
 * Copyright (c) 2003-2005 JGGApi Development Team. All Rights Reserved. This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version. This program is distributed in
 * the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a
 * copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package pl.mn.communicator.packet.in;

import pl.mn.communicator.IUser;
import pl.mn.communicator.RemoteStatus;
import pl.mn.communicator.User;
import pl.mn.communicator.packet.GGConversion;
import pl.mn.communicator.packet.GGStatuses;
import pl.mn.communicator.packet.GGUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGNotifyReply60.java,v 1.2 2007/05/12 13:54:48 winnetou25 Exp $
 */
public class GGNotifyReply60 implements GGIncomingPackage {

	public static final int GG_NOTIFY_REPLY60 = 0x0011;

	private final Map<IUser, RemoteStatus> m_statuses = new HashMap<IUser, RemoteStatus>();

	// struct gg_notify_reply60 {
	// int uin; /* numerek plus flagi w najstarszym bajcie */ - 4
	// char status; /* status danej osoby */ - 1
	// int remote_ip; /* adres IP bezpośrednich połączeń */ - 4
	// short remote_port; /* port bezpośrednich połączeń */ - 2
	// char version; /* wersja klienta */ - 1
	// char image_size; /* maksymalny rozmiar obrazków w KB */ -1
	// char unknown1; /* 0x00 */ -1
	// char description_size; /* rozmiar opisu i czasu, nie musi wystąpić */ -1
	// char description[]; /* opis, nie musi wystąpić */
	// int time; /* czas, nie musi wystąpić */ 4
	// };

	public GGNotifyReply60(final byte[] data) {
		handlePacket(data);
	}

	public int getPacketType() {
		return GG_NOTIFY_REPLY60;
	}

	public Map<IUser, RemoteStatus> getUsersStatus() {
		return m_statuses;
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
				status60.setAreWeInRemoteUserBuddyList(false);
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

			m_statuses.put(user, status60);
		}
	}

}
