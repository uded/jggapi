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
package pl.mn.communicator;

import java.util.Date;

import pl.mn.communicator.packet.GGUtils;

/**
 * Created on 2004-11-21
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: IncomingMessage.java,v 1.1 2004-12-19 13:42:31 winnetou25 Exp $
 */
public class IncomingMessage extends AbstractMessage {

	private Date m_messageDate;
	private int m_messageID;
	
    /**
	 * @param uin
	 * @param messageBody
	 */
	public IncomingMessage(int uin, String messageBody, int messageID, long messageDate, int protocolMessageClass) {
		super(uin, messageBody, GGUtils.getClientMessageClass(protocolMessageClass));
		if (messageDate < 0) throw new IllegalArgumentException("messageDate cannot be less than 0");
		if (messageID < 0) throw new IllegalArgumentException("messageID cannot be less than 0");
		m_messageDate = new Date(messageDate);
		m_messageID = messageID;
	}
	
	public int getMessageID() {
		return m_messageID;
	}

    public Date getMessageDate() {
    	return m_messageDate;
    }
    
}
