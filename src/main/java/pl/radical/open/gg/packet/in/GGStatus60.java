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
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGStatus60.java,v 1.1 2005/11/05 23:34:52 winnetou25 Exp $
 */
public class GGStatus60 implements GGIncomingPackage, GGStatuses {

	// #define GG_STATUS60 0x000F

	// struct gg_status60 {
	// int uin; /* numer plus flagi w najstarszym bajcie */ 4
	// char status; /* nowy stan */ 1
	// int remote_ip; /* adres IP bezpo�rednich po��cze� */ 4
	// short remote_port; /* port bezpo�rednich po��cze� */ 2
	// char version; /* wersja klienta */ 1
	// char image_size; /* maksymalny rozmiar grafiki */ 2
	// char unknown1; /* 0x00 * 1
	// char description[]; /* opis, nie musi wyst�pi� */ n
	// int time; /* czas, nie musi wyst�pi� */ 1
	// };

	public static final int GG_STATUS60 = 0x0F;

	private IUser m_user = null;

	private RemoteStatus m_status60 = null;

	public GGStatus60(final byte[] data) {
		handleUser(data);
		handleStatus60(data);
	}

	/**
	 * @see pl.radical.open.gg.packet.GGPacket#getPacketType()
	 */
	public int getPacketType() {
		return GG_STATUS60;
	}

	public IUser getUser() {
		return m_user;
	}

	public IRemoteStatus getStatus60() {
		return m_status60;
	}

	private void handleUser(final byte[] data) {
		final byte flag = data[3]; // cache flag
		data[3] = GGUtils.intToByte(0)[0]; // remove flag
		final int uin = GGUtils.byteToInt(data);
		data[3] = flag;
		final int protocolStatus = GGUtils.unsignedByteToInt(data[4]);
		final User.UserMode userMode = GGConversion.getUserMode(protocolStatus);
		m_user = new User(uin, userMode);
	}

	private void handleStatus60(final byte[] data) {
		final byte flag = data[3]; // cache flag

		final int protocolStatus = GGUtils.unsignedByteToInt(data[4]);
		final int remoteIP = GGUtils.byteToInt(data, 5);
		final byte[] remoteIPArray = GGUtils.convertIntToByteArray(remoteIP);
		final int remotePort = GGUtils.byteToShort(data, 9);
		final int version = GGUtils.unsignedByteToInt(data[11]);
		final int imageSize = GGUtils.unsignedByteToInt(data[12]);

		String description = null;
		long timeInMillis = -1;
		if (protocolStatus == GGStatuses.GG_STATUS_AVAIL_DESCR || protocolStatus == GGStatuses.GG_STATUS_BUSY_DESCR || protocolStatus == GGStatuses.GG_STATUS_INVISIBLE_DESCR || protocolStatus == GGStatuses.GG_STATUS_NOT_AVAIL_DESCR) {
			description = GGUtils.byteToString(data, 14);
			if (data.length > 14 + description.length()) {
				final int timeInSeconds = GGUtils.byteToInt(data, data.length - 4);
				timeInMillis = GGUtils.secondsToMillis(timeInSeconds);
			}
		}

		m_status60 = GGConversion.getClientRemoteStatus(protocolStatus, description, timeInMillis);
		m_status60.setRemoteIP(remoteIPArray);
		m_status60.setImageSize(imageSize);
		m_status60.setGGVersion(version);

		if (remotePort == 0) {
			m_status60.setSupportsDirectCommunication(false);
		} else if (remotePort == 1) {
			m_status60.setUserBehindFirewall(true);
		} else if (remotePort == 2) {
			m_status60.setAreWeInRemoteUserBuddyList(false);
		} else {
			m_status60.setRemotePort(remotePort);
		}

		if (flag == 0x40) {
			m_status60.setSupportsVoiceCommunication(true);
		}
	}

}