package pl.radical.open.gg;

import pl.radical.open.gg.utils.GGConversion;

import java.util.Date;

/**
 * The class that represents message that is received from Gadu-Gadu server.
 * <p>
 * Created on 2004-11-21
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class IncomingMessage extends AbstractMessage implements IIncommingMessage {

	/** The time the message was sent */
	private Date m_messageDate = null;

	/**
	 * Constructor for <code>IncomingMessage</code>.
	 * 
	 * @param uin
	 *            Gadu-Gadu number of the user that sent the message.
	 * @param messageBody
	 *            the body of the message.
	 * @throws IllegalArgumentException
	 *             if the uin or messageID or messageDate is a negative value.
	 * @throws NullPointerException
	 *             if the messageBody is null.
	 */
	public IncomingMessage(final int uin, final String messageBody, final int messageID, final long messageDate, final int protocolMessageClass) {
		super(uin, messageBody, GGConversion.getClientMessageClass(protocolMessageClass));
		if (messageDate < 0) {
			throw new IllegalArgumentException("messageDate cannot be less than 0");
		}
		if (messageID < 0) {
			throw new IllegalArgumentException("messageID cannot be less than 0");
		}
		m_messageDate = new Date(messageDate);
		m_messageID = messageID;
	}

	/**
	 * Returns the time this message was sent.
	 * 
	 * @return Date the time this message was sent.
	 */
	public Date getMessageDate() {
		return m_messageDate;
	}

}
