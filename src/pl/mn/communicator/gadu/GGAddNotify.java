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
 * Pakiet dodaj±cy konkretnego u¿ytkownika do listy montorowancyh u¿ytkowników.
 * @see pl.mn.communicator.gadu.GGNotifyReply
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @version $Id: GGAddNotify.java,v 1.10 2004-10-26 23:56:40 winnetou25 Exp $
 */
class GGAddNotify implements GGOutgoingPackage {
    /** Numer u¿ytkownika */
    private int userNo;

    /**
     * Tworz pakiet dodanie do monitorowania dla konkretnego u¿ytkownika.
     * @param userNo numer u¿ytkownika
     */
    GGAddNotify(int userNo) {
        this.userNo = userNo;
    }

    /**
     * @see pl.mn.communicator.gadu.GGOutgoingPackage#getHeader()
     */
    public int getHeader() {
        return 0x000d;
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
        System.arraycopy(userNo, 0, dane, 0, userNo.length);
        dane[4] = GGNotify.GG_USER_NORMAL;

        return dane;
    }
}
