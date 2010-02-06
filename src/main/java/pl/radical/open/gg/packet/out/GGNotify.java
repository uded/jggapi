/*
 * Copyright (c) 2003-2005 JGGApi Development Team. All Rights Reserved. This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version. This program is distributed in
 * the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a
 * copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package pl.radical.open.gg.packet.out;

import pl.radical.open.gg.IUser;
import pl.radical.open.gg.packet.GGConversion;
import pl.radical.open.gg.packet.GGUser;
import pl.radical.open.gg.packet.GGUtils;

/**
 * @see pl.radical.open.gg.packet.in.GGNotifyReply
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGNotify.java,v 1.1 2005/11/05 23:34:53 winnetou25 Exp $
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
