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
package pl.mn.communicator.packet.out;

import java.util.Random;

import pl.mn.communicator.IMessage;
import pl.mn.communicator.packet.GGMessageEnabled;
import pl.mn.communicator.packet.GGUtils;

/**
 * Class representing packet that will send Gadu-Gadu message.
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGSendMsg.java,v 1.5 2004-12-19 17:14:42 winnetou25 Exp $
 */
public class GGSendMsg implements GGOutgoingPackage, GGMessageEnabled {
	
	public static final int GG_SEND_MSG = 0x0B;
	
	private final static Random RANDOM = new Random();
	
    private static int m_seq = -1;
    private int m_user = -1;
    private String m_text = "";
    private	int m_protocolMessageClass = GG_CLASS_MSG;

    public GGSendMsg(IMessage message) {
    	if (message == null) throw new NullPointerException("message cannot be null");
        m_user = message.getUin();
        m_text = message.getMessageBody();
        m_seq = RANDOM.nextInt(999999);
        m_protocolMessageClass = GGUtils.getProtocolMessageClass(message.getMessageClass());
    }

    /**
     * @see pl.mn.communicator.packet.out.GGOutgoingPackage#getPacketType()
     */
    public int getPacketType() {
        return GG_SEND_MSG;
    }

    /**
     * @see pl.mn.communicator.packet.out.GGOutgoingPackage#getLength()
     */
    public int getLength() {
        return 4+4+4+m_text.length()+1;
    }

    /**
     * @see pl.mn.communicator.packet.out.GGOutgoingPackage#getContents()
     */
    public byte[] getContents() {
        byte[] toSend = new byte[getLength()];

        toSend[3] = (byte) ((m_user >> 24) & 0xFF);
        toSend[2] = (byte) ((m_user >> 16) & 0xFF);
        toSend[1] = (byte) ((m_user >> 8) & 0xFF);
        toSend[0] = (byte) (m_user & 0xFF);

        toSend[7] = (byte) ((m_seq >> 24) & 0xFF);
        toSend[6] = (byte) ((m_seq >> 16) & 0xFF);
        toSend[5] = (byte) ((m_seq >> 8) & 0xFF);
        toSend[4] = (byte) (m_seq & 0xFF);

        toSend[11] = (byte) ((m_protocolMessageClass >> 24) & 0xFF);
        toSend[10] = (byte) ((m_protocolMessageClass >> 16) & 0xFF);
        toSend[9] = (byte) ((m_protocolMessageClass >> 8) & 0xFF);
        toSend[8] = (byte) (m_protocolMessageClass & 0xFF);

        //TODO check if this conversion needs charset
        byte[] textBytes = m_text.getBytes();

        for (int i = 0; i < m_text.length(); i++) {
            toSend[12+i] = textBytes[i];
        }
        return toSend;
    }
    
}
