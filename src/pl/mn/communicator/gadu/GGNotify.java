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
 * Zawiera do 409 struktur jak poni¿ej:
 * 
 *  struct gg_notify {
 *      int uin;    // numerek danej osoby
 *      char type;  // rodzaj u¿ytkownika
 *     };
 *
 * @see pl.mn.communicator.gadu.GGNotifyReply
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGNotify.java,v 1.13 2004-12-11 16:25:58 winnetou25 Exp $
 */
public class GGNotify implements GGOutgoingPackage {
	
	public static final int GG_NOTIFY = 0x10;

    /** U¿ytkownik dla którego bêdziemy niedostêpni */
    public static final int GG_USER_OFFLINE = 0x01;

    /** Zwyk³y u¿ytkownik w naszej li¶cie kontaktów */
    public static final int GG_USER_NORMAL = 0x03;

    /** U¿ytkownik, którego wiadomo¶ci nie chcemy otrzymywaæ */
    public static final int GG_USER_BLOCKED = 0x04;
    
    private IUser[] users;

    /**
     * Tworzy pakiet <code>GGNotify</code> na podstawie listy u¿ytkowników.
     * @param users lista u¿ytkowników
     */
    public GGNotify(IUser[] users) {
        this.users = users;
    }

    /**
     * Tworzy pakiet <code>GGNotyfy</code> na podstawie u¿ytkownika.
     * @param users u¿ytkownik
     */
    GGNotify(IUser users) {
        this.users = new IUser[1];
        this.users[0] = users;
    }

    /**
     * @see pl.mn.communicator.gadu.GGOutgoingPackage#getHeader()
     */
    public int getHeader() {
        return GG_NOTIFY;
    }

    /**
     * @see pl.mn.communicator.gadu.GGOutgoingPackage#getLength()
     */
    public int getLength() {
        return users.length * 5;
    }

    /**
     * @see pl.mn.communicator.gadu.GGOutgoingPackage#getContents()
     */
    public byte[] getContents() {
        //      4 dla int'a i jeden dla char'a
        byte[] data = new byte[users.length * 5];

        for (int i = 0; i < users.length; i++) {
            byte[] userNo = GGUtils.intToByte(users[i].getNumber());

            for (int j = 0; j < userNo.length; j++) {
                // skopiuj nr uzytkownika do data
                data[(i * 5) + j] = userNo[j];
            }

            // sprawdzic czy sie dobrze konwertuje z char do unsigned byte
            data[(i * 5) + 4] = GG_USER_NORMAL;
        }

        return data;
    }
    
}
