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
package pl.mn.communicator.gadu.out;

import pl.mn.communicator.gadu.GGOutgoingPackage;

/**
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGPubdirRequest.java,v 1.1 2004-12-12 16:21:54 winnetou25 Exp $
 */
public class GGPubdirRequest implements GGOutgoingPackage {

	public static final int GG_PUBDIR50_REQUEST = 0x0014;
	
//#define GG_PUBDIR50_REQUEST 0x0014
//	
//struct gg_pubdir50 {
//	char type;
//	int seq;
//	char request[];
//};

	/**
     * @see pl.mn.communicator.gadu.GGOutgoingPackage#getPacketType()
     */
    public int getPacketType() {
    	return GG_PUBDIR50_REQUEST;
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
