/*
 * Copyright (c) 2003-2005 JGGApi Development Team. All Rights Reserved. This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version. This program is distributed in
 * the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a
 * copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package pl.radical.open.gg.packet.in;

import pl.radical.open.gg.IRemoteStatus;
import pl.radical.open.gg.IUser;
import pl.radical.open.gg.RemoteStatus;
import pl.radical.open.gg.User;
import pl.radical.open.gg.packet.GGConversion;
import pl.radical.open.gg.packet.GGStatuses;
import pl.radical.open.gg.packet.GGUtils;

/**
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGStatus.java,v 1.1 2005/11/05 23:34:52 winnetou25 Exp $
 */
public class GGStatus implements GGIncomingPackage, GGStatuses {

	public static final int GG_STATUS = 0x02;

	private IUser m_user = null;
	private RemoteStatus m_status = null;

	public GGStatus(final byte[] data) {
		handleUser(data);
		handleStatus(data);
	}

	public int getPacketType() {
		return GG_STATUS;
	}

	public IUser getUser() {
		return m_user;
	}

	public IRemoteStatus getStatus() {
		return m_status;
	}

	private void handleUser(final byte[] data) {
		final int uin = GGUtils.byteToInt(data);
		final int protocolStatus = GGUtils.unsignedByteToInt(data[4]);
		final User.UserMode userMode = GGConversion.getUserMode(protocolStatus);
		m_user = new User(uin, userMode);
	}

	private void handleStatus(final byte[] data) {
		final int protocolStatus = GGUtils.unsignedByteToInt(data[4]);
		String description = null;
		long timeInMillis = -1;
		if (protocolStatus == GGStatuses.GG_STATUS_AVAIL_DESCR || protocolStatus == GGStatuses.GG_STATUS_BUSY_DESCR || protocolStatus == GGStatuses.GG_STATUS_INVISIBLE_DESCR || protocolStatus == GGStatuses.GG_STATUS_NOT_AVAIL_DESCR) {
			description = GGUtils.byteToString(data, 8);
			if (data.length > 8 + description.length()) {
				final int timeInSeconds = GGUtils.byteToInt(data, data.length - 4);
				timeInMillis = GGUtils.secondsToMillis(timeInSeconds);
			}
		}
		m_status = GGConversion.getClientRemoteStatus(protocolStatus, description, timeInMillis);
	}

}
