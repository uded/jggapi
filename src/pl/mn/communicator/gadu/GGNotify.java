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
package pl.mn.communicator.gadu;

import pl.mn.communicator.IUser;

/**
 * Pakiet informuj±cy serwer rozmów o monitorowanym u¿ytkowniku.
 * 
 * @see pl.mn.communicator.gadu.GGNotifyReply
 * @version $Revision: 1.7 $
 * @author mnaglik
 */
class GGNotify implements GGOutgoingPackage {
	public static final int GG_USER_OFFLINE = 0x01;
	public static final int GG_USER_NORMAL = 0x03;
	public static final int GG_USER_BLOCKED = 0x04;
	
	private IUser[] users;
	
	GGNotify(IUser[] users) {
		this.users = users;
	}

	GGNotify(IUser users) {
		this.users = new IUser[1];
		this.users[0] = users;
	}
		
	/**
	 * @see pl.mn.communicator.gadu.GGOutgoingPackage#getHeader()
	 */
	public int getHeader() {
		return 0x10;
	}

	/**
	 * @see pl.mn.communicator.gadu.GGOutgoingPackage#getLength()
	 */
	public int getLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @see pl.mn.communicator.gadu.GGOutgoingPackage#getContents()
	 */
	public byte[] getContents() {
		// TODO Auto-generated method stub
		return null;
	}

}
