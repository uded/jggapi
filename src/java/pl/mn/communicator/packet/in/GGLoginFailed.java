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
 * Created on 2004-12-11
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGLoginFailed.java,v 1.1 2004-12-14 21:53:52 winnetou25 Exp $
 */
public class GGLoginFailed implements GGIncomingPackage {

	public final static int GG_LOGIN_FAILED = 9;

	private static GGLoginFailed m_instance = null;
	
	private GGLoginFailed() {
		//prevent instant
	}
	
	/**
	 * @see pl.mn.communicator.packet.in.GGIncomingPackage#getPacketType()
	 */
	public int getPacketType() {
		return GG_LOGIN_FAILED;
	}
	
	public static GGLoginFailed getInstance() {
		if (m_instance == null) {
			m_instance = new GGLoginFailed();
		}
		return m_instance;
	}

}