/*
 * Copyright (c) 2003-2005 JGGApi Development Team. All Rights Reserved. This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version. This program is distributed in
 * the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a
 * copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package pl.mn.communicator;

import pl.mn.communicator.event.MessageListener;

import java.util.HashSet;
import java.util.Set;

/**
 * Created on 2005-01-29
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: AbstractChat.java,v 1.1 2005/11/05 23:34:52 winnetou25 Exp $
 */
public abstract class AbstractChat implements IChat {

	protected Set<MessageListener> m_listeners = new HashSet<MessageListener>();
	protected Session m_session = null;

	protected AbstractChat(final Session session) {
		if (session == null) {
			throw new NullPointerException("session cannot be null");
		}
		m_session = session;
		session.getMessageService().addMessageListener(new MessageHandler());
	}

	public void addChatListener(final MessageListener messageListener) {
		if (messageListener == null) {
			throw new NullPointerException("messageListener cannot be null");
		}
		m_listeners.add(messageListener);
	}

	public void removeChatListener(final MessageListener messageListener) {
		if (messageListener == null) {
			throw new NullPointerException("messageListener cannot be null");
		}
		m_listeners.remove(messageListener);
	}

	protected void fireChatMessageArrived(final IIncommingMessage message) {
		for (final Object element : m_listeners) {
			final MessageListener listener = (MessageListener) element;
			listener.messageArrived(message);
		}
	}

	protected void fireChatMessageDelivered(final int uin, final int messageID, final MessageStatus deliveryStatus) {
		for (final Object element : m_listeners) {
			final MessageListener listener = (MessageListener) element;
			listener.messageDelivered(uin, messageID, deliveryStatus);
		}
	}

	protected abstract boolean acceptsIncoming(IIncommingMessage incomingMessage);

	protected abstract boolean acceptsOutgoing(int uin, int messageID, MessageStatus deliveryStatus);

	private class MessageHandler extends MessageListener.Stub {

		/**
		 * @see pl.mn.communicator.event.MessageListener#messageArrived(pl.mn.communicator.IncomingMessage)
		 */
		@Override
		public void messageArrived(final IIncommingMessage incommingMessage) {
			if (acceptsIncoming(incommingMessage)) {
				fireChatMessageArrived(incommingMessage);
			}
		}

		/**
		 * @see pl.mn.communicator.event.MessageListener#messageDelivered(int, int, pl.mn.communicator.MessageStatus)
		 */
		@Override
		public void messageDelivered(final int uin, final int messageID, final MessageStatus deliveryStatus) {
			if (acceptsOutgoing(uin, messageID, deliveryStatus)) {
				fireChatMessageDelivered(uin, messageID, deliveryStatus);
			}
		}

	}

}
