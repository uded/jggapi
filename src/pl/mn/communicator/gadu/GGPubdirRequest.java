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
 * Pakiet wychodz±cy typu ping, okresowo wysy³any do serwera.
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @version $Id: GGPubdirRequest.java,v 1.2 2004-10-26 23:56:40 winnetou25 Exp $
 */
class GGPubdirRequest implements GGOutgoingPackage {

	/* (non-Javadoc)
     * @see pl.mn.communicator.gadu.GGOutgoingPackage#getHeader()
     */
    public int getHeader() {
        // TODO Auto-generated method stub
        return 0;
    }

    /* (non-Javadoc)
     * @see pl.mn.communicator.gadu.GGOutgoingPackage#getLength()
     */
    public int getLength() {
        // TODO Auto-generated method stub
        return 0;
    }

    /* (non-Javadoc)
     * @see pl.mn.communicator.gadu.GGOutgoingPackage#getContents()
     */
    public byte[] getContents() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
