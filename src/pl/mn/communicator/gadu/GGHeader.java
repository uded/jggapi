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
 * Gadu-Gadu packet header.
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @version $Id: GGHeader.java,v 1.12 2004-12-12 00:29:34 winnetou25 Exp $
 */
public class GGHeader {

    private int m_type = -1;
    private int m_length = -1;

    /**
     * Constructor for Header.
     * @param data dane naglowka
     */
    public GGHeader(byte[] data) {
        m_type = GGUtils.byteToInt(data, 0);
        m_length = GGUtils.byteToInt(data, 4);
    }

    /**
     * Returns the type.
     * @return int
     */
    public int getType() {
        return m_type;
    }
    
    /**
     * Returns the length of packet.
     * @return int
     */
    public int getLength() {
        return m_length;
    }
    
    /**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "[PacketType: "+m_type+", PacketLength: "+m_length+"]";
	}

}
