package pl.radical.open.gg;

/**
 * Created on 2005-01-29
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class SingleChat extends AbstractChat implements ISingleChat {

	private int m_recipientUin = -1; // user with whom we chat

	// friendly
	SingleChat(final Session session, final int recipientUin) {
		super(session);
		if (recipientUin < 0) {
			throw new IllegalArgumentException("recipientUin cannot be less than 0");
		}
		m_recipientUin = recipientUin;
	}

	public IChat sendMessage(final String messageBody) throws GGException {
		if (messageBody == null) {
			throw new NullPointerException("messageBody cannot be less than 0");
		}

		m_session.getMessageService().sendMessage(OutgoingMessage.createChatMessage(m_recipientUin, messageBody));

		return this;
	}

	/**
	 * @see pl.radical.open.gg.ISingleChat#getRecipientUin()
	 */
	public int getRecipientUin() {
		return m_recipientUin;
	}

	public void setRecipientUin(final int recipientUin) {
		if (recipientUin < 0) {
			throw new IllegalArgumentException("recipientUin cannot be null");
		}
		m_recipientUin = recipientUin;
	}

	/**
	 * @see pl.radical.open.gg.AbstractChat#acceptsIncoming(pl.radical.open.gg.IncomingMessage)
	 */
	@Override
	protected boolean acceptsIncoming(final IIncommingMessage incomingMessage) {
		return incomingMessage.getRecipientUin() == m_recipientUin;
	}

	/**
	 * @see pl.radical.open.gg.AbstractChat#acceptsOutgoing(int, int, pl.radical.open.gg.MessageStatus)
	 */
	@Override
	protected boolean acceptsOutgoing(final int uin, final int messageID, final MessageStatus deliveryStatus) {
		return uin == m_recipientUin;
	}

}
