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
package pl.mn.communicator.packet.handlers;

import pl.mn.communicator.GGException;
import pl.mn.communicator.IChat;
import pl.mn.communicator.ISingleChat;
import pl.mn.communicator.IncomingMessage;
import pl.mn.communicator.MessageStatus;
import pl.mn.communicator.OutgoingMessage;

/**
 * Created on 2005-01-29
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: SingleChat.java,v 1.5 2005-01-30 18:37:42 winnetou25 Exp $
 */
public class SingleChat extends AbstractChat implements ISingleChat {
	
	private int m_recipientUin = -1; //user with whom we chat
	
	//friendly
	SingleChat(Session session, int recipientUin) {
		super(session);
		if (recipientUin < 0) throw new IllegalArgumentException("recipientUin cannot be less than 0");
		m_recipientUin = recipientUin;
	}
	
	public IChat sendMessage(String messageBody) throws GGException {
		if (messageBody == null) throw new NullPointerException("messageBody cannot be less than 0");

		m_session.getMessageService().sendMessage(OutgoingMessage.createChatMessage(m_recipientUin, messageBody));
		
		return this;
	}

	/**
	 * @see pl.mn.communicator.ISingleChat#getRecipientUin()
	 */
	public int getRecipientUin() {
		return m_recipientUin;
	}
	
	public void setRecipientUin(int recipientUin) {
		if (recipientUin < 0) throw new IllegalArgumentException("recipientUin cannot be null");
		m_recipientUin = recipientUin;
	}
	
	/**
	 * @see pl.mn.communicator.packet.handlers.AbstractChat#acceptsIncoming(pl.mn.communicator.IncomingMessage)
	 */
	protected boolean acceptsIncoming(IncomingMessage incomingMessage) {
		return (incomingMessage.getRecipientUin() == m_recipientUin);
	}

	/**
	 * @see pl.mn.communicator.packet.handlers.AbstractChat#acceptsOutgoing(int, int, pl.mn.communicator.MessageStatus)
	 */
	protected boolean acceptsOutgoing(int uin, int messageID, MessageStatus deliveryStatus) {
		return (uin == m_recipientUin);
	}
	
}
