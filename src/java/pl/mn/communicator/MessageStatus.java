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

/**
 * Created on 2004-12-11
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: MessageStatus.java,v 1.1 2004-12-14 21:53:51 winnetou25 Exp $
 */
public class MessageStatus {

	private String m_messageStatus;
	
	private MessageStatus(String messageStatus) {
		m_messageStatus = messageStatus;
	}
	
	/** Message has not been delivered. */
	public final static MessageStatus BLOCKED = new MessageStatus("message_status_blocked");

	/** Message has been successfuly delivered. */
	public final static MessageStatus DELIVERED = new MessageStatus("message_status_delivered");

	/** Message has been queued for later delivery. */
	public final static MessageStatus QUEUED = new MessageStatus("message_status_queued");

	/** Message has not been delivered because remote queue is full (max. 20 messages). */
	public final static MessageStatus BLOCKED_MBOX_FULL = new MessageStatus("message_status_mbox_full");

	/** Message has not been delivered. This status is only in case of GG_CLASS_CTCP */
	public final static MessageStatus NOT_DELIVERED = new MessageStatus("message_status_not_delivered");

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return m_messageStatus;
	}
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return m_messageStatus.hashCode();
	}
	
}
