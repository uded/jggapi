package pl.radical.open.gg.event;

import pl.radical.open.gg.IIncommingMessage;
import pl.radical.open.gg.IOutgoingMessage;
import pl.radical.open.gg.IncomingMessage;
import pl.radical.open.gg.packet.dicts.MessageStatus;

import java.util.EventListener;

/**
 * The listener interface that is notified of message related events.
 * <p>
 * The classes that implement this interface are notified whether message has arrived from Gadu-Gadu server or if the
 * message the client sent was successully delivered to the user to whom it was addressed.
 * <p>
 * Created on 2004-11-27
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public interface MessageListener extends EventListener {

	void messageSent(IOutgoingMessage outgoingMessage);

	/**
	 * Notification that a message arrived from Gadu-Gadu server.
	 * 
	 * @param incommingMessage
	 *            the message that arrived from Gadu-Gadu user.
	 */
	void messageArrived(IIncommingMessage incommingMessage);

	/**
	 * Notification that the message was successfully delivered to the recipient.
	 * 
	 * @param uin
	 *            the Gadu-Gadu number of the user to whom the message was addressed.
	 * @param messageID
	 *            the unique message id that was generated before sending message.
	 * @param deliveryStatus
	 *            the delivery status of the message.
	 */
	void messageDelivered(int uin, int messageID, MessageStatus deliveryStatus);

	class Stub implements MessageListener {

		/**
		 * @see pl.radical.open.gg.event.MessageListener#messageArrived(IncomingMessage)
		 */
		public void messageArrived(final IIncommingMessage incommingMessage) {
		}

		/**
		 * @see pl.radical.open.gg.event.MessageListener#messageDelivered(int, int, MessageStatus)
		 */
		public void messageDelivered(final int uin, final int messageID, final MessageStatus deliveryStatus) {
		}

		/**
		 * @see pl.radical.open.gg.event.MessageListener#messageSent(pl.radical.open.gg.OutgoingMessage)
		 */
		public void messageSent(final IOutgoingMessage outgoingMessage) {
		}

	}

}
