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

import java.util.EventObject;

/**
 * The event class that describes sent message ack.
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: MessageDeliveredEvent.java,v 1.1 2004-11-11 18:42:07 winnetou25 Exp $
 */
public class MessageDeliveredEvent extends EventObject {

	/** Message has not been delivered. */
	public final static int MESSAGE_BLOCKED = 1;

	/** Message has been successfuly delivered. */
	public final static int MESSAGE_DELIVERED = 2;

	/** Message has been queued for later delivery. */
	public final static int MESSAGE_QUEUED = 3;

	/** Message has not been delivered because remote queue is full (max. 20 messages). */
	public final static int MESSAGE_BLOCKED_MBOX_FULL = 4;

	/** Message has not been delivered. This status is only in case of GG_CLASS_CTCP */
	public final static int MESSAGE_NOT_DELIVERED = 6;

	private User m_recipient = null;
	private int m_messageStatus = -1;
	private int m_messageID = -1;
	
	public MessageDeliveredEvent(Object source, User recipient, int messageSeq, int messageStatus) {
		super(source);
		m_recipient = recipient;
		m_messageStatus = messageStatus;
		m_messageID = messageSeq;
	}
	
	/** 
	 * @return the user to whom message was sucessfuly delivered.
	 */
	public User getRecipient() {
		return m_recipient;
	}
	
	public int getMessageID() {
		return m_messageID;
	}
	
	/**
	 * @return message delivery status.
	 */
	public int getMessageDeliveryStatus() {
		return m_messageStatus;
	}
	
}
