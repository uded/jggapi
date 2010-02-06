/*
 * Copyright (c) 2003-2005 JGGApi Development Team. All Rights Reserved. This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version. This program is distributed in
 * the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a
 * copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package pl.mn.communicator.packet.out;

import pl.mn.communicator.ILocalStatus;
import pl.mn.communicator.packet.GGConversion;
import pl.mn.communicator.packet.GGStatuses;
import pl.mn.communicator.packet.GGUtils;

/**
 * Packet that sets new status of the Gadu-Gadu user.
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGNewStatus.java,v 1.1 2005/11/05 23:34:53 winnetou25 Exp $
 */
public class GGNewStatus implements GGOutgoingPackage, GGStatuses {

	public static final int GG_NEW_STATUS = 0x0002;

	private static final int MAX_DESCRIPTION = 70;

	private ILocalStatus m_localStatus = null;

	/**
	 * The protocol status constructor.
	 */
	public GGNewStatus(final ILocalStatus localStatus) {
		if (localStatus == null) {
			throw new NullPointerException("status cannot be null");
		}
		m_localStatus = localStatus;
	}

	/**
	 * @see pl.mn.communicator.packet.out.GGOutgoingPackage#getPacketType()
	 */
	public int getPacketType() {
		return GG_NEW_STATUS;
	}

	/**
	 * @see pl.mn.communicator.packet.out.GGOutgoingPackage#getLength()
	 */
	public int getLength() {
		int length = 4;

		if (m_localStatus.getStatusType().isDescriptionStatus() && m_localStatus.isDescriptionSet()) {
			length += m_localStatus.getDescription().length() + 1;
			if (m_localStatus.isReturnDateSet()) {
				length += 4;
			}
		}

		return length;
	}

	/**
	 * @see pl.mn.communicator.packet.out.GGOutgoingPackage#getContents()
	 */
	public byte[] getContents() {
		final int statusToSend = GGConversion.getProtocolStatus(m_localStatus, m_localStatus.isFriendsOnly(), false);

		final byte[] toSend = new byte[getLength()];

		toSend[0] = (byte) (statusToSend & 0xFF);
		toSend[1] = (byte) (statusToSend >> 8 & 0xFF);
		toSend[2] = (byte) (statusToSend >> 16 & 0xFF);
		toSend[3] = (byte) (statusToSend >> 24 & 0xFF);

		if (m_localStatus.getStatusType().isDescriptionStatus() && m_localStatus.isDescriptionSet()) {
			final String description = trimDescription(m_localStatus.getDescription());
			final byte[] descBytes = description.getBytes();
			for (int i = 0; i < descBytes.length; i++) {
				toSend[4 + i] = descBytes[i];
			}
			if (m_localStatus.isReturnDateSet()) {
				final int timeInSeconds = GGUtils.millisToSeconds(m_localStatus.getReturnDate().getTime());
				toSend[4 + description.length() + 1] = (byte) (timeInSeconds & 0xFF);
				toSend[4 + description.length() + 2] = (byte) (timeInSeconds >> 8 & 0xFF);
				toSend[4 + description.length() + 3] = (byte) (timeInSeconds >> 16 & 0xFF);
				toSend[4 + description.length() + 4] = (byte) (timeInSeconds >> 24 & 0xFF);
			}
		}
		return toSend;
	}

	private String trimDescription(String description) {
		if (description == null) {
			return null;
		}
		if (description.length() > MAX_DESCRIPTION) {
			description = description.substring(0, MAX_DESCRIPTION - 1);
		}
		return description;
	}

}
