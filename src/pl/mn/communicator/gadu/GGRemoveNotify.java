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


/**
 * Pakiet usuwaj±cy konkretnego u¿ytkownika z listy monitorowanych u¿ytkowników.
 * @version $Revision: 1.6 $
 * @author mnaglik
 */
class GGRemoveNotify implements GGOutgoingPackage {
    private int userNo;
	GGRemoveNotify(int userNo) {
    	this.userNo = userNo;
    }
    
    /**
     * @see pl.mn.communicator.gadu.GGOutgoingPackage#getHeader()
     */
    public int getHeader() {
        return 0x000e;
    }

    /**
     * @see pl.mn.communicator.gadu.GGOutgoingPackage#getLength()
     */
    public int getLength() {
        return 5;
    }

    /**
     * @see pl.mn.communicator.gadu.GGOutgoingPackage#getContents()
     */
    public byte[] getContents() {
    	byte[] dane = new byte[5];

        byte[] userNo = GGConversion.intToByte(this.userNo);
        System.arraycopy(userNo,0,dane,0,userNo.length);
        dane[4] = GGNotify.GG_USER_NORMAL;

    	return dane;
    }
}
