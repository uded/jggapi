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
import java.util.Vector;

import pl.mn.communicator.IChat;
import pl.mn.communicator.IncomingMessage;
import pl.mn.communicator.MessageStatus;
import pl.mn.communicator.event.MessageListener;

/**
 * Created on 2005-01-29
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: AbstractChat.java,v 1.4 2005-01-30 18:36:35 winnetou25 Exp $
 */
public abstract class AbstractChat implements IChat {

	protected Vector m_listeners = new Vector();
	protected Session m_session = null;

	protected AbstractChat(Session session) {
		if (session == null) throw new NullPointerException("session cannot be null");
		m_session = session;
		session.getMessageService().addMessageListener(new MessageHandler());
	}

	public void addChatListener(MessageListener messageListener) {
		if (messageListener == null) throw new NullPointerException("messageListener cannot be null");
		m_listeners.add(messageListener);
	}
	
	public void removeChatListener(MessageListener messageListener) {
		if (messageListener == null) throw new NullPointerException("messageListener cannot be null");
		m_listeners.remove(messageListener);
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
	
	protected abstract boolean acceptsIncoming(IncomingMessage incomingMessage);

	protected abstract boolean acceptsOutgoing(int uin, int messageID, MessageStatus deliveryStatus);

	private class MessageHandler implements MessageListener {
		
		/**
		 * @see pl.mn.communicator.event.MessageListener#messageArrived(pl.mn.communicator.IncomingMessage)
		 */
		public void messageArrived(IncomingMessage incommingMessage) {
			if (acceptsIncoming(incommingMessage)) {
				fireChatMessageArrived(incommingMessage);
			}
		}
		
		/**
		 * @see pl.mn.communicator.event.MessageListener#messageDelivered(int, int, pl.mn.communicator.MessageStatus)
		 */
		public void messageDelivered(int uin, int messageID, MessageStatus deliveryStatus) {
			if (acceptsOutgoing(uin, messageID, deliveryStatus)) {
				fireChatMessageDelivered(uin, messageID, deliveryStatus);
			}
		}
		
	}
	
}
