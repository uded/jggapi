/*
 * Copyright (c) 2003 Marcin Naglik (mnaglik@gazeta.pl)
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package pl.mn.communicator.packet.in;

/**
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGDisconnecting.java,v 1.2 2004-12-18 16:47:14 winnetou25 Exp $
 */
public class GGDisconnecting implements GGIncomingPackage {

	public static final int GG_DISCONNECTING = 0x000B;

	private static GGDisconnecting m_instance = null;
	
	private GGDisconnecting() {
		//private constructor
	}

	public static GGDisconnecting getInstance() {
		if (m_instance == null) {
			m_instance = new GGDisconnecting();
		}
		return m_instance;
	}

	/**
	 * @see pl.mn.communicator.packet.in.GGIncomingPackage#getPacketType()
	 */
	public int getPacketType() {
		return GG_DISCONNECTING;
	}
	
}
