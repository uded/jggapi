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

/**
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGListEmpty.java,v 1.2 2006/11/19 15:51:44 winnetou25 Exp $
 */
public class GGListEmpty implements GGOutgoingPackage {

	public static final int GG_EMPTY_LIST = 0x0012;

	private static GGListEmpty m_instance = null;

	private static byte[] m_data = null;

	private GGListEmpty() { // private constructor
		m_data = new byte[0];
	}

	// this method is not thread-safe, because this is check and act
	// and it is not protected against race-condition
	public static GGListEmpty getInstance() {
		if (m_instance == null) {
			m_instance = new GGListEmpty();
		}
		return m_instance;
	}

	/**
	 * @see pl.radical.open.gg.packet.out.GGOutgoingPackage#getPacketType()
	 */
	public int getPacketType() {
		return GG_EMPTY_LIST;
	}

	/**
	 * @see pl.radical.open.gg.packet.out.GGOutgoingPackage#getLength()
	 */
	public int getLength() {
		return m_data.length;
	}

	/**
	 * @see pl.radical.open.gg.packet.out.GGOutgoingPackage#getContents()
	 */
	public byte[] getContents() {
		return m_data;
	}

}
