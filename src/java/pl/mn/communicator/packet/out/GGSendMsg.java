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

import java.util.ArrayList;

import pl.mn.communicator.OutgoingMessage;
import pl.mn.communicator.packet.GGMessageEnabled;
import pl.mn.communicator.packet.GGUtils;

/**
 * Class representing packet that will send Gadu-Gadu message.
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGSendMsg.java,v 1.13 2005-01-29 16:39:43 winnetou25 Exp $
 */
public class GGSendMsg implements GGOutgoingPackage, GGMessageEnabled {
	
	public static final int GG_SEND_MSG = 0x0B;
	
    private static int m_seq = -1;
    private ArrayList m_recipients = new ArrayList();
    private String m_text = "";
    private	int m_protocolMessageClass = GG_CLASS_MSG;

    public GGSendMsg(OutgoingMessage outgoingMessage) {
    	if (outgoingMessage == null) throw new NullPointerException("outgoingMessage cannot be null");
    	m_recipients.add(new Integer(outgoingMessage.getUin()));
        m_text = outgoingMessage.getMessageBody();
        m_seq = outgoingMessage.getMessageID();
        m_protocolMessageClass = GGUtils.getProtocolMessageClass(outgoingMessage.getMessageClass());
    }

    public void addRecipient(int uin) {
    	m_recipients.add(new Integer(uin));
    }
    
    public void removeRecipient(int uin) {
    	m_recipients.remove(new Integer(uin));
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
        return 4+4+4+m_text.length()+1+5+(m_recipients.size()*4);
    }

    /**
     * @see pl.mn.communicator.packet.out.GGOutgoingPackage#getContents()
     */
    public byte[] getContents() {
        byte[] toSend = new byte[getLength()];

        int recipient = ((Integer) m_recipients.get(0)).intValue();
        
        toSend[0] = (byte) (recipient & 0xFF);
        toSend[1] = (byte) ((recipient >> 8) & 0xFF);
        toSend[2] = (byte) ((recipient >> 16) & 0xFF);
        toSend[3] = (byte) ((recipient >> 24) & 0xFF);

        toSend[4] = (byte) (m_seq & 0xFF);
        toSend[5] = (byte) ((m_seq >> 8) & 0xFF);
        toSend[6] = (byte) ((m_seq >> 16) & 0xFF);
        toSend[7] = (byte) ((m_seq >> 24) & 0xFF);

        toSend[8] = (byte) (m_protocolMessageClass & 0xFF);
        toSend[9] = (byte) ((m_protocolMessageClass >> 8) & 0xFF);
        toSend[10] = (byte) ((m_protocolMessageClass >> 16) & 0xFF);
        toSend[11] = (byte) ((m_protocolMessageClass >> 24) & 0xFF);

        //TODO check if this conversion needs charset
        byte[] textBytes = m_text.getBytes();

        for (int i=0; i<m_text.length(); i++) {
            toSend[12+i] = textBytes[i];
        }

    	int recipientCount = m_recipients.size()-1;

        if (recipientCount > 0) {
        	toSend[12+m_text.length()+1] = 0x01;
        	
        	toSend[12+m_text.length()+2] = (byte) (recipientCount & 0xFF);
        	toSend[12+m_text.length()+3] = (byte) (recipientCount >> 8 & 0xFF);
        	toSend[12+m_text.length()+4] = (byte) (recipientCount >> 16 & 0xFF);
        	toSend[12+m_text.length()+5] = (byte) (recipientCount >> 24 & 0xFF);
        	
        	for (int i=1; i<=recipientCount; i++) {
        		int recipientUin = ((Integer)m_recipients.get(i)).intValue();
            	toSend[12+m_text.length()+6+i-1] = (byte) (recipientUin  & 0xFF);
            	toSend[12+m_text.length()+7+i-1] = (byte) (recipientUin >> 8 & 0xFF);
            	toSend[12+m_text.length()+8+i-1] = (byte) (recipientUin >> 16 & 0xFF);
            	toSend[12+m_text.length()+9+i-1] = (byte) (recipientUin >> 24 & 0xFF);
        	}
        }
        
        return toSend;
    }
    
}
