/*
 * Copyright (c) 2003-2005 JGGApi Development Team. All Rights Reserved.
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

import pl.mn.communicator.packet.GGUtils;

/**
 * The packet is retrieved from the Gadu-Gadu server just after we connect to it.
 * The class parses package and gets seed from server.
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGWelcome.java,v 1.2 2004-12-19 17:14:43 winnetou25 Exp $
 */
public class GGWelcome implements GGIncomingPackage {
    
	public static final int GG_PACKAGE_WELCOME = 0x1;

	private int m_seed = -1;

    public GGWelcome(byte[] data) {
    	if (data == null) throw new NullPointerException("data cannot be null");
        m_seed = GGUtils.byteToInt(data);
    }
    
    /**
	 * @see pl.mn.communicator.packet.in.GGIncomingPackage#getPacketType()
	 */
	public int getPacketType() {
		return GG_PACKAGE_WELCOME;
	}

    public int getSeed() {
        return m_seed;
    }
    
}
