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
package pl.mn.communicator.gadu.in;

import pl.mn.communicator.gadu.GGIncomingPackage;
import pl.mn.communicator.gadu.GGMessage;
import pl.mn.communicator.gadu.GGUtils;

/**
 * Class representing Gadu-Gadu received message packet.
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGRecvMsg.java,v 1.2 2004-12-13 23:44:02 winnetou25 Exp $
 */
public class GGRecvMsg implements GGIncomingPackage, GGMessage {

	public static final int GG_RECV_MSG  = 0x000A;

    private int m_sender = -1;
    private int m_seq = -1;
    private long m_time = -1;
    private int m_msgClass = -1;
    private String m_message = "";

    public GGRecvMsg(byte[] data) {
        m_sender = GGUtils.byteToInt(data);
        m_seq = GGUtils.byteToInt(data, 4);
        m_time = GGUtils.secondsToMillis(GGUtils.byteToInt(data, 8));
        m_msgClass = GGUtils.byteToInt(data, 12);
        m_message = GGUtils.byteToString(data,16);
    }
    
    /**
	 * @see pl.mn.communicator.gadu.GGIncomingPackage#getPacketType()
	 */
	public int getPacketType() {
		return GG_RECV_MSG;
	}
    
    /**
     * Returns the message.
     * @return String
     */
    public String getMessage() {
        return m_message;
    }

    /**
     * Returns the msgClass.
     * @return int msgClass
     */
    public int getMsgClass() {
        return m_msgClass;
    }

    /**
     * Returns the sender uin number.
     * @return int the sender uin.
     */
    public int getSenderUin() {
        return m_sender;
    }

    /**
     * Returns the unique message sequence number.
     * @return int message sequence number.
     */
    public int getMessageSeq() {
        return m_seq;
    }

    /**
     * Time in seconds.
     * @return int the time in seconds.
     */
    public long getTime() {
        return m_time;
    }
    
}