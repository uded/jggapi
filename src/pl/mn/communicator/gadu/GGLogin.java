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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * An outgoing packet that will be send after connecting to Gadu-Gadu server.
 *
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGLogin.java,v 1.20 2004-12-11 16:25:58 winnetou25 Exp $
 */
public class GGLogin implements GGOutgoingPackage, GGStatusEnabled {

    private static Log logger = LogFactory.getLog(GGLogin.class);

	public final static int GG_LOGIN = 0x000C;
	
	public final static int VERSION_60 = 0x20; //6.0
	public final static int VERSION_5_7_beta_build121 = 0x1e;		//5.7 beta (build 121)
	public final static int VERSION_5_7_beta = 0x1c;	//5.7 beta
	public final static int VERSION_5_0_5 = 0x1b;	//5.0.5
	public final static int VERSION_5_0_3 = 0x19;	//5.0.3
	public final static int VERSION_5_0 = 0x18;	//5.0.1, 5.0.0, 4.9.3
	public final static int VERSION_4_9_2 = 0x17;	//4.9.2
	public final static int VERSION_4_9_1 = 0x16;	//4.9.1
	public final static int VERSION_4_8_9 = 0x15;	//4.8.9
	public final static int VERSION_4_8_3 = 0x14;	//4.8.3, 4.8.1
	public final static int VERSION_4_6_10 = 0x11;	//4.6.10, 4.6.1
	public final static int VERSION_4_5_22 = 0x10;	//4.5.22, 4.5.21, 4.5.19, 4.5.17, 4.5.15
	public final static int VERSION_4_5_12 = 0x0f;	//4.5.12
	public final static int VERSION_4_0_30 = 0x0b;	//4.0.30, 4.0.29, 4.0.28, 4.0.25
	
    private int m_uin = -1;
    private int m_hash = -1;
    private int m_status = GG_STATUS_BUSY;
    private int m_version = VERSION_60; //wersja 6.00
    private int m_localIp;
    private short m_localPort;

    public GGLogin(byte[] localIP, int localPort, int uin, char[] password, int seed) {
        //m_localIp = GGConversion.byteToInt(localIP);
        //m_localPort = (short) localPort;
        m_uin = uin;
        m_hash = getLoginHash(password, seed);
        //m_status = GGUtils.getStatus(loginContext.getStatus());
    }

    /**
     * @see pl.mn.communicator.gadu.GGOutgoingPackage#getHeader()
     */
    public int getHeader() {
        return GG_LOGIN;
    }

    /**
     * @see pl.mn.communicator.gadu.GGOutgoingPackage#getLength()
     */
    public int getLength() {
        return 0x16;
    }

    /**
     * @see pl.mn.communicator.gadu.GGOutgoingPackage#getContents()
     */
    public byte[] getContents() {
        byte[] toSend = new byte[22];

        toSend[3] = (byte) ((m_uin >> 24) & 0xFF);
        toSend[2] = (byte) ((m_uin >> 16) & 0xFF);
        toSend[1] = (byte) ((m_uin >> 8) & 0xFF);
        toSend[0] = (byte) (m_uin & 0xFF);

        toSend[7] = (byte) ((m_hash >> 24) & 0xFF);
        toSend[6] = (byte) ((m_hash >> 16) & 0xFF);
        toSend[5] = (byte) ((m_hash >> 8) & 0xFF);
        toSend[4] = (byte) (m_hash & 0xFF);

        toSend[11] = (byte) ((m_status >> 24) & 0xFF);
        toSend[10] = (byte) ((m_status >> 16) & 0xFF);
        toSend[9] = (byte) ((m_status >> 8) & 0xFF);
        toSend[8] = (byte) (m_status & 0xFF);

        toSend[15] = (byte) ((m_version >> 24) & 0xFF);
        toSend[14] = (byte) ((m_version >> 16) & 0xFF);
        toSend[13] = (byte) ((m_version >> 8) & 0xFF);
        toSend[12] = (byte) (m_version & 0xFF);

        //TODO
        toSend[19] = (byte) 1;
        toSend[18] = (byte) 0;
        toSend[17] = (byte) 168;
        toSend[16] = (byte) 192;

//      toSend[19] = (byte) ((m_localIp >> 24) & 0xFF);
//      toSend[18] = (byte) ((m_localIp >> 16) & 0xFF);
//      toSend[17] = (byte) ((m_localIp >> 8) & 0xFF);
//      toSend[16] = (byte) (m_localIp & 0xFF);

//		toSend[21] = (byte) ((m_localPort >> 8) & 0xFF);
//      toSend[20] = (byte) (m_localPort & 0xFF);

		toSend[21] = (byte) ((32000 >> 8) & 0xFF);
		toSend[20] = (byte) (32000 & 0xFF);

        return toSend;
    }
    
    private int getLoginHash(char[] password, int seed) {
        long x;
        long y;
        long z;

        y = seed;

        int i;

        for (x = 0, i = 0; i < password.length; i++) {
            x = (x & 0xffffff00) | password[i];
            y ^= x;

            int k = (int) y;
            k += x;
            y = GGUtils.unsignedIntToLong(k);

            k = (int) x;
            k <<= 8;
            x = GGUtils.unsignedIntToLong(k);

            y ^= x;

            k = (int) x;
            k <<= 8;
            x = GGUtils.unsignedIntToLong(k);

            k = (int) y;
            k -= x;
            y = GGUtils.unsignedIntToLong(k);

            k = (int) x;
            k <<= 8;
            x = GGUtils.unsignedIntToLong(k);

            y ^= x;

            z = y & 0x1f;
            y = GGUtils.unsignedIntToLong((int) ((y << z)
                    | (y >> (32 - z))));
        }

        return (int) y;
    }
    
}
