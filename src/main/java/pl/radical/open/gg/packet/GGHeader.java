/*
 * Copyright (c) 2003-2005 JGGApi Development Team. All Rights Reserved. This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version. This program is distributed in
 * the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a
 * copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package pl.radical.open.gg.packet;

/**
 * Gadu-Gadu packet header.
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGHeader.java,v 1.1 2005/11/05 23:34:52 winnetou25 Exp $
 */
public class GGHeader {

	/** Gadu-Gadu packet type */
	private int m_type = -1;

	/** Gadu-Gadu packet length */
	private int m_length = -1;

	/**
	 * Constructor for Header.
	 * 
	 * @param data
	 *            dane naglowka
	 */
	public GGHeader(final byte[] data) {
		m_type = GGUtils.byteToInt(data, 0);
		m_length = GGUtils.byteToInt(data, 4);
	}

	/**
	 * Returns the type.
	 * 
	 * @return int packet type.
	 */
	public int getType() {
		return m_type;
	}

	/**
	 * Returns the length of packet.
	 * 
	 * @return int packet length.
	 */
	public int getLength() {
		return m_length;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[PacketType: " + m_type + ", packetLength: " + m_length + "]";
	}

}
