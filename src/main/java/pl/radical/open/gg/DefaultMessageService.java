package pl.radical.open.gg;

import pl.radical.open.gg.event.MessageListener;
import pl.radical.open.gg.packet.out.GGSendMsg;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created on 2004-11-28 The default implementation of <code>IMessageService</code>.
 * <p>
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class DefaultMessageService implements IMessageService {

	private Session m_session = null;
	private final Set<MessageListener> m_messageListeners = new HashSet<MessageListener>();

	// friendly
	DefaultMessageService(final Session session) {
		if (session == null) {
			throw new IllegalArgumentException("session cannot be null");
		}
		m_session = session;
	}

	/**
	 * @see pl.radical.open.gg.IMessageService#sendMessage(pl.radical.open.gg.OutgoingMessage)
	 */
	public void sendMessage(final IOutgoingMessage outgoingMessage) throws GGException {
		if (outgoingMessage == null) {
			throw new IllegalArgumentException("outgoingMessage cannot be null");
		}
		checkSessionState();
		try {
			final GGSendMsg messageOut = new GGSendMsg(outgoingMessage);
			final int[] additionalRecipients = outgoingMessage.getAdditionalRecipients();
			for (final int uin : additionalRecipients) {
				messageOut.addAdditionalRecipient(uin);
			}
			m_session.getSessionAccessor().sendPackage(messageOut);
			notifyMessageSent(outgoingMessage);
		} catch (final IOException ex) {
			throw new GGException("Error occured while sending message: " + outgoingMessage, ex);
		}
	}

	/**
	 * @see pl.radical.open.gg.IMessageService#createSingleChat(int)
	 */
	public ISingleChat createSingleChat(final int uin) {
		if (uin < 0) {
			throw new IllegalArgumentException("uin cannot be less than 0");
		}
		return new SingleChat(m_session, uin);
	}

	/**
	 * @see pl.radical.open.gg.IMessageService#createGroupChat()
	 */
	public IGroupChat createGroupChat() {
		return new GroupChat(m_session, new int[0]);
	}

	/**
	 * @see pl.radical.open.gg.IMessageService#createGroupChat(int[])
	 */
	public IGroupChat createGroupChat(final int[] uins) {
		if (uins == null) {
			throw new IllegalArgumentException("uins cannot be null");
		}
		return new GroupChat(m_session, uins);
	}

	/**
	 * @see pl.radical.open.gg.IMessageService#addMessageListener(pl.radical.open.gg.event.MessageListener)
	 */
	public void addMessageListener(final MessageListener messageListener) {
		if (messageListener == null) {
			throw new IllegalArgumentException("messageListener cannot be null");
		}
		m_messageListeners.add(messageListener);
	}

	/**
	 * @see pl.radical.open.gg.IMessageService#removeMessageListener(pl.radical.open.gg.event.MessageListener)
	 */
	public void removeMessageListener(final MessageListener messageListener) {
		if (messageListener == null) {
			throw new IllegalArgumentException("messageListener cannot be null");
		}
		m_messageListeners.remove(messageListener);
	}

	protected void notifyMessageSent(final IOutgoingMessage outgoingMessage) {
		if (outgoingMessage == null) {
			throw new IllegalArgumentException("outgoingMessage cannot be null");
		}
		for (final Object element : m_messageListeners) {
			final MessageListener messageListener = (MessageListener) element;
			messageListener.messageSent(outgoingMessage);
		}
	}

	protected void notifyMessageArrived(final IIncommingMessage incommingMessage) {
		if (incommingMessage == null) {
			throw new IllegalArgumentException("incommingMessage cannot be null");
		}
		for (final Object element : m_messageListeners) {
			final MessageListener messageListener = (MessageListener) element;
			messageListener.messageArrived(incommingMessage);
		}
	}

	protected void notifyMessageDelivered(final int uin, final int messageID, final MessageStatus messageStatus) {
		if (uin < 0) {
			throw new IllegalArgumentException("uin cannot be less than 0");
		}
		if (messageID < 0) {
			throw new IllegalArgumentException("messageID cannot be less than 0");
		}
		if (messageStatus == null) {
			throw new IllegalArgumentException("messageStatus cannot be less than 0");
		}
		for (final Object element : m_messageListeners) {
			final MessageListener messageListener = (MessageListener) element;
			messageListener.messageDelivered(uin, messageID, messageStatus);
		}
	}

	private void checkSessionState() throws GGSessionException {
		if (m_session.getSessionState() != SessionState.LOGGED_IN) {
			throw new GGSessionException(m_session.getSessionState());
		}
	}

}
