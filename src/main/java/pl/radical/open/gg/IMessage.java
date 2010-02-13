package pl.radical.open.gg;

/**
 * This is a basic interface for all Gadu-Gadu related messages.
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public interface IMessage {

	/**
	 * Gadu-Gadu number of user to whom this message is addressed or was received.
	 * 
	 * @return int Gadu-Gadu uin.
	 */
	int getRecipientUin();

	/**
	 * Gets message body.
	 * 
	 * @return String message body.
	 */
	String getMessageBody();

	/**
	 * Gets unique message id.
	 * 
	 * @return unique message id.
	 */
	int getMessageID();

	/**
	 * MessageClass associated with this message.
	 * 
	 * @return MessageClass related to this message.
	 */
	MessageClass getMessageClass();

}
