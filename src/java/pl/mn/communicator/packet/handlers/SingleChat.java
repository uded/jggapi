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

import java.util.Enumeration;

import pl.mn.communicator.GGException;
import pl.mn.communicator.IChat;
import pl.mn.communicator.ISingleChat;
import pl.mn.communicator.IncomingMessage;
import pl.mn.communicator.MessageStatus;
import pl.mn.communicator.OutgoingMessage;
import pl.mn.communicator.event.MessageListener;

/**
 * Created on 2005-01-29
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: SingleChat.java,v 1.4 2005-01-29 17:09:48 winnetou25 Exp $
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
	
//	/**
//	 * @see pl.mn.communicator.IChat#nextMessage()
//	 */
//	public IMessage nextMessage() {
//		// TODO Auto-generated method stub
//		//TODO implement messageQueue
//		
//		Queue
//		return null;
//	}	
	
	/**
	 * @see pl.mn.communicator.packet.handlers.AbstractChat#getMessageHandler()
	 */
	protected MessageListener getMessageHandler() {
		return new MessageHandler();
	}
	
	protected void fireChatMessageArrived(IncomingMessage message) {
		for (Enumeration e = m_listeners.elements(); e.hasMoreElements();) {
			MessageListener listener = (MessageListener) e.nextElement();
			listener.messageArrived(message);
		}
	}

	protected void fireChatMessageDelivered(int uin, int messageID, MessageStatus deliveryStatus) {
		for (Enumeration e = m_listeners.elements(); e.hasMoreElements();) {
			MessageListener listener = (MessageListener) e.nextElement();
			listener.messageDelivered(uin, messageID, deliveryStatus);
		}
	}

	private class MessageHandler implements MessageListener {

		/**
		 * @see pl.mn.communicator.event.MessageListener#messageArrived(pl.mn.communicator.IncomingMessage)
		 */
		public void messageArrived(IncomingMessage incommingMessage) {
			if (incommingMessage.getRecipientUin() == m_recipientUin) {
				fireChatMessageArrived(incommingMessage);
			}
		}

		/**
		 * @see pl.mn.communicator.event.MessageListener#messageDelivered(int, int, pl.mn.communicator.MessageStatus)
		 */
		public void messageDelivered(int uin, int messageID, MessageStatus deliveryStatus) {
			if (uin == m_recipientUin) {
				fireChatMessageDelivered(uin, messageID, deliveryStatus);
			}
		}
		
	}
	
}
