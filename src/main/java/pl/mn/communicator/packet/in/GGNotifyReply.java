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
 * @version $Id: GGNotifyReply.java,v 1.1 2005/11/05 23:34:52 winnetou25 Exp $
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
