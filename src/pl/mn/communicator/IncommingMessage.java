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

import pl.mn.communicator.gadu.GGUtils;

/**
 * Created on 2004-11-21
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: IncommingMessage.java,v 1.1 2004-12-11 16:25:57 winnetou25 Exp $
 */
public class IncommingMessage extends AbstractMessage {

	private Date m_messageDate;
	private int m_messageID;
	
    /**
	 * @param toUser
	 * @param text
	 */
	public IncommingMessage(int fromUser, String text, int messageID, long messageDate, int protocolMessageClass) {
		super(fromUser, text, GGUtils.getClientMessageClass(protocolMessageClass));
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
