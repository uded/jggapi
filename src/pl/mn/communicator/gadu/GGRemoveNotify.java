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
 * Packet that deletes certain user from the list of monitored users.<BR>
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGRemoveNotify.java,v 1.11 2004-12-11 16:25:58 winnetou25 Exp $
 */
public class GGRemoveNotify implements GGOutgoingPackage {
	
	public final static int GG_REMOVE_NOTIFY = 0x000E;
	
//		#define GG_REMOVE_NOTIFY 0x000e
//		
//		struct gg_remove_notify {
//			int uin;	/* numerek */
//			char type;	/* rodzaj u�ytkownika */
//		};
	
    /** Numer u�ytkownika */
    private int m_uin;

    /**
     * Tw�rz pakiet do usuni�cia u�ytkownika z listy monitorowanych
     * @param userNo numer u�ytkownika do usuni�cia
     */
    public GGRemoveNotify(int uin) {
    	if (uin < 0) throw new IllegalArgumentException("uin cannot be less than 0");
        m_uin = uin;
    }

    /**
     * @see pl.mn.communicator.gadu.GGOutgoingPackage#getHeader()
     */
    public int getHeader() {
        return GG_REMOVE_NOTIFY;
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
        byte[] dane = new byte[getLength()];

        byte[] uin = GGUtils.intToByte(m_uin);
        System.arraycopy(uin, 0, dane, 0, uin.length);
        dane[4] = GGNotify.GG_USER_NORMAL;

        return dane;
    }
    
}
