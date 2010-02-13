package pl.radical.open.gg;

import pl.radical.open.gg.event.MessageListener;

/**
 * The client should use this interface if it is interested in sending message to Gadu-Gadu server or if it wants to be
 * interested in message related events.
 * <p>
 * Created on 2004-11-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public interface IMessageService {

	/**
	 * Invocation of this method sends the message to the Gadu-Gadu server.
	 * 
	 * @param outgoingMessage
	 *            the message that will be sent to the server.
	 * @throws GGException
	 *             if there is an error while sending message to server.
	 * @throws GGSessionException
	 *             if user is not logged in.
	 * @throws NullPointerException
	 *             if the outgoingMessage is null.
	 */
	void sendMessage(IOutgoingMessage outgoingMessage) throws GGException;

	/**
	 * Create chat with some Gadu-Gadu user.
	 * 
	 * @param recipientUin
	 *            the Gadu-Gadu number of the user one wants to chat with
	 * @return chat object that represents chat with some user.
	 */
	ISingleChat createSingleChat(int recipientUin);

	IGroupChat createGroupChat();

	IGroupChat createGroupChat(int[] recipientUins);

	/**
	 * Adds <code>MessageListener</code> object to the list that will be notified of message related events.
	 * 
	 * @see pl.radical.open.gg.event.MessageListener
	 * @param messageListener
	 *            the <code>MessageListener</code> instance to be notified.
	 * @throws NullPointerException
	 *             if the messageListener is null.
	 */
	void addMessageListener(MessageListener messageListener);

	/**
	 * Remove <code>MessageListener</code> from the list that will be notified of message related events.
	 * 
	 * @see pl.radical.open.gg.event.MessageListener
	 * @param messageListener
	 *            the <code>MessageListener</code> instance that will no longer be notified.
	 * @throws NullPointerException
	 *             if the messageListener is null.
	 */
	void removeMessageListener(MessageListener messageListener);

}
