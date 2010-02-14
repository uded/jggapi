package pl.radical.open.gg;

/**
 * An abstract implementation of <code>IMessage</code> that is common for incoming and outgoing messages.
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 */
public abstract class AbstractMessage implements IMessage {

	/** Uin of the Gadu-Gadu user */
	protected int m_recipientUin = -1;

	/** The body of the message */
	protected String m_messageBody = null;

	/** The message class associated with this message */
	protected MessageClass m_messageClass = null;

	protected int m_messageID = -1;

	// FIXME IllegalArgumentException
	protected AbstractMessage(final int recepientUin, final String messageBody, final MessageClass messageClass) {
		if (recepientUin < 0) {
			throw new IllegalArgumentException("uin cannot be less than 0");
		}
		if (messageBody == null) {
			throw new GGNullPointerException("messageBody cannot be null");
		}
		if (messageClass == null) {
			throw new GGNullPointerException("messageClass cannot be null");
		}
		m_recipientUin = recepientUin;
		m_messageBody = messageBody;
		m_messageClass = messageClass;
	}

	/**
	 * @see pl.radical.open.gg.IMessage#getRecipientUin()
	 */
	public int getRecipientUin() {
		return m_recipientUin;
	}

	/**
	 * @see pl.radical.open.gg.IMessage#getMessageBody()
	 */
	public String getMessageBody() {
		return m_messageBody;
	}

	/**
	 * @see pl.radical.open.gg.IMessage#getMessageID()
	 */
	public int getMessageID() {
		return m_messageID;
	}

	/**
	 * @see pl.radical.open.gg.IMessage#getMessageClass()
	 */
	public MessageClass getMessageClass() {
		return m_messageClass;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[RecipientUin: " + m_recipientUin + ", messageBody: " + m_messageBody + ", messageClass: " + m_messageClass + "]";
	}

}
