package pl.radical.open.gg;

import pl.radical.open.gg.event.MessageListener;

import java.util.HashSet;
import java.util.Set;

/**
 * Created on 2005-01-29
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public abstract class AbstractChat implements IChat {

	protected Set<MessageListener> m_listeners = new HashSet<MessageListener>();
	protected Session m_session = null;

	protected AbstractChat(final Session session) {
		if (session == null) {
			throw new IllegalArgumentException("session cannot be null");
		}
		m_session = session;
		session.getMessageService().addMessageListener(new MessageHandler());
	}

	public void addChatListener(final MessageListener messageListener) {
		if (messageListener == null) {
			throw new IllegalArgumentException("messageListener cannot be null");
		}
		m_listeners.add(messageListener);
	}

	public void removeChatListener(final MessageListener messageListener) {
		if (messageListener == null) {
			throw new IllegalArgumentException("messageListener cannot be null");
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
		 * @see pl.radical.open.gg.event.MessageListener#messageArrived(pl.radical.open.gg.IncomingMessage)
		 */
		@Override
		public void messageArrived(final IIncommingMessage incommingMessage) {
			if (acceptsIncoming(incommingMessage)) {
				fireChatMessageArrived(incommingMessage);
			}
		}

		/**
		 * @see pl.radical.open.gg.event.MessageListener#messageDelivered(int, int, pl.radical.open.gg.MessageStatus)
		 */
		@Override
		public void messageDelivered(final int uin, final int messageID, final MessageStatus deliveryStatus) {
			if (acceptsOutgoing(uin, messageID, deliveryStatus)) {
				fireChatMessageDelivered(uin, messageID, deliveryStatus);
			}
		}

	}

}
