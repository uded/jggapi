package pl.radical.open.gg;

import pl.radical.open.gg.packet.out.GGSendMsg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2005-01-29
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class GroupChat extends AbstractChat implements IGroupChat {

	private final List<Integer> m_recipientUins = new ArrayList<Integer>(); // users with whom we chat

	// friendly
	GroupChat(final Session session) {
		this(session, new int[0]);
	}

	// friendly
	GroupChat(final Session session, final int[] recipientUins) {
		super(session);
		for (final int recipientUin : recipientUins) {
			addRecipient(recipientUin);
		}
	}

	public IChat sendMessage(final String messageBody) throws GGException {
		if (messageBody.isEmpty()) {
			throw new IllegalArgumentException("messageBody cannot be less than 0");
		}

		if (m_recipientUins.isEmpty()) {
			throw new GGException("Unable to send message, at least one recipient is required");
		}

		final int recipientUin = m_recipientUins.get(0).intValue();
		final GGSendMsg sendMsg = new GGSendMsg(OutgoingMessage.createChatMessage(recipientUin, messageBody));

		for (int i = 1; i < m_recipientUins.size(); i++) {
			final int recipient = m_recipientUins.get(i).intValue();
			sendMsg.addAdditionalRecipient(recipient);
		}

		try {
			m_session.getSessionAccessor().sendPackage(sendMsg);
		} catch (final IOException ex) {
			throw new GGException("Unable to send group chat message");
		}

		return this;
	}

	public void addRecipient(final int recipientUin) {
		if (recipientUin < 0) {
			throw new IllegalArgumentException("recipientUin cannot be less than 0");
		}
		m_recipientUins.add(Integer.valueOf(recipientUin));
	}

	public void removeRecipient(final int recipientUin) {
		if (recipientUin < 0) {
			throw new IllegalArgumentException("recipientUin cannot be less than 0");
		}
		m_recipientUins.remove(Integer.valueOf(recipientUin));
	}

	public int[] getRecipientUins() {
		final int[] recipientUins = new int[m_recipientUins.size()];
		for (int i = 0; i < m_recipientUins.size(); i++) {
			recipientUins[i] = m_recipientUins.get(i).intValue();
		}
		return recipientUins;
	}

	/**
	 * @see pl.radical.open.gg.AbstractChat#acceptsIncoming(pl.radical.open.gg.IncomingMessage)
	 */
	@Override
	protected boolean acceptsIncoming(final IIncommingMessage incomingMessage) {
		return isRegisteredRecipient(incomingMessage.getMessageID());
	}

	/**
	 * @see pl.radical.open.gg.AbstractChat#acceptsOutgoing(int, int, pl.radical.open.gg.MessageStatus)
	 */
	@Override
	protected boolean acceptsOutgoing(final int uin, final int messageID, final MessageStatus deliveryStatus) {
		return isRegisteredRecipient(uin);
	}

	private boolean isRegisteredRecipient(final int uin) {
		for (int i = 0; i < m_recipientUins.size(); i++) {
			if (uin == m_recipientUins.get(i).intValue()) {
				return true;
			}
		}

		return false;
	}

}
