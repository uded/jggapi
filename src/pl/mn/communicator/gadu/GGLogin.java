///*
// * Copyright (c) 2003 Marcin Naglik (mnaglik@gazeta.pl)
// *
// * This program is free software; you can redistribute it and/or modify it
// * under the terms of the GNU General Public License as published by the Free
// * Software Foundation; either version 2 of the License, or (at your option)
// * any later version.
// *
// * This program is distributed in the hope that it will be useful, but WITHOUT
// * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
// * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
// * more details.
// *
// * You should have received a copy of the GNU General Public License along
// * with this program; if not, write to the Free Software Foundation, Inc.,
// * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
// */
//package pl.mn.communicator.gadu;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//
///**
// * An outgoing packet that will be send after connecting to Gadu-Gadu server.
// *
// * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
// * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
// * @version $Id: GGLogin.java,v 1.21 2004-12-11 19:40:50 winnetou25 Exp $
// */
//public class GGLogin implements GGOutgoingPackage, GGStatusEnabled {
//
//    private static Log logger = LogFactory.getLog(GGLogin.class);
//
//	public final static int GG_LOGIN = 0x000C;
//	
//    private int m_uin = -1;
//    private int m_hash = -1;
//    private int m_status = GG_STATUS_AVAIL;
//    private int m_version = 0x20; //wersja 6.00
//    private int m_localIp;
//    private short m_localPort;
//
//    public GGLogin(byte[] localIP, int localPort, int uin, char[] password, int seed) {
//        //m_localIp = GGConversion.byteToInt(localIP);
//        //m_localPort = (short) localPort;
//        m_uin = uin;
//        m_hash = GGUtils.getLoginHash(password, seed);
//        //m_status = GGUtils.getStatus(loginContext.getStatus());
//    }
//
//    /**
//     * @see pl.mn.communicator.gadu.GGOutgoingPackage#getHeader()
//     */
//    public int getHeader() {
//        return GG_LOGIN;
//    }
//
//    /**
//     * @see pl.mn.communicator.gadu.GGOutgoingPackage#getLength()
//     */
//    public int getLength() {
//        return 22;
//    }
//
//    /**
//     * @see pl.mn.communicator.gadu.GGOutgoingPackage#getContents()
//     */
//    public byte[] getContents() {
//        byte[] toSend = new byte[getLength()];
//
//        toSend[3] = (byte) ((m_uin >> 24) & 0xFF);
//        toSend[2] = (byte) ((m_uin >> 16) & 0xFF);
//        toSend[1] = (byte) ((m_uin >> 8) & 0xFF);
//        toSend[0] = (byte) (m_uin & 0xFF);
//
//        toSend[7] = (byte) ((m_hash >> 24) & 0xFF);
//        toSend[6] = (byte) ((m_hash >> 16) & 0xFF);
//        toSend[5] = (byte) ((m_hash >> 8) & 0xFF);
//        toSend[4] = (byte) (m_hash & 0xFF);
//
//        toSend[11] = (byte) ((m_status >> 24) & 0xFF);
//        toSend[10] = (byte) ((m_status >> 16) & 0xFF);
//        toSend[9] = (byte) ((m_status >> 8) & 0xFF);
//        toSend[8] = (byte) (m_status & 0xFF);
//
//        toSend[15] = (byte) ((m_version >> 24) & 0xFF);
//        toSend[14] = (byte) ((m_version >> 16) & 0xFF);
//        toSend[13] = (byte) ((m_version >> 8) & 0xFF);
//        toSend[12] = (byte) (m_version & 0xFF);
//
//        //TODO
//        toSend[19] = (byte) 1;
//        toSend[18] = (byte) 0;
//        toSend[17] = (byte) 168;
//        toSend[16] = (byte) 192;
//
////      toSend[19] = (byte) ((m_localIp >> 24) & 0xFF);
////      toSend[18] = (byte) ((m_localIp >> 16) & 0xFF);
////      toSend[17] = (byte) ((m_localIp >> 8) & 0xFF);
////      toSend[16] = (byte) (m_localIp & 0xFF);
//
////		toSend[21] = (byte) ((m_localPort >> 8) & 0xFF);
////      toSend[20] = (byte) (m_localPort & 0xFF);
//
//		toSend[21] = (byte) ((32000 >> 8) & 0xFF);
//		toSend[20] = (byte) (32000 & 0xFF);
//
//        return toSend;
//    }
//    
//}
