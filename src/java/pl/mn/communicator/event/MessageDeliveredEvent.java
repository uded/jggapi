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
package pl.mn.communicator.event;

import java.util.EventObject;

import pl.mn.communicator.MessageStatus;
import pl.mn.communicator.packet.GGUtils;

/**
 * The event class that describes sent message ack.
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: MessageDeliveredEvent.java,v 1.1 2004-12-14 21:53:50 winnetou25 Exp $
 */
public class MessageDeliveredEvent extends EventObject {

	private int m_recipientUin = -1;
	private MessageStatus m_messageStatus = null;
	private int m_messageID = -1;
	
	public MessageDeliveredEvent(Object source, int recipientUin, int messageSeq, int protocolMessageStatus) {
		super(source);
		if (recipientUin < 0) throw new IllegalArgumentException("recipientUin cannot be less than 0");
		m_recipientUin = recipientUin;
		m_messageStatus = GGUtils.getClientMessageStatus(protocolMessageStatus);
		m_messageID = messageSeq;
	}
	
	/** 
	 * @return the user to whom message was sucessfuly delivered.
	 */
	public int getRecipientUin() {
		return m_recipientUin;
	}
	
	public int getMessageID() {
		return m_messageID;
	}
	
	/**
	 * @return message delivery status.
	 */
	public MessageStatus getDeliveryStatus() {
		return m_messageStatus;
	}
	
}
