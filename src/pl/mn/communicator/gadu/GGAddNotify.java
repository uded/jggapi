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
 * 
 * @see pl.mn.communicator.gadu.GGNotifyReply
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGAddNotify.java,v 1.14 2004-12-11 19:40:50 winnetou25 Exp $
 */
public class GGAddNotify implements GGOutgoingPackage {

	public final int GG_ADD_NOTIFY = 0x000D;
	
    /** Gadu-Gadu uin number */
    private int m_uin;

    public GGAddNotify(int uin) {
    	if (uin < 0) throw new IllegalArgumentException("uin cannot be less than 0");
        m_uin = uin;
    }
    
    public int getUin(){
    	return m_uin;
    }

    /**
     * @see pl.mn.communicator.gadu.GGOutgoingPackage#getHeader()
     */
    public int getHeader() {
        return GG_ADD_NOTIFY;
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
