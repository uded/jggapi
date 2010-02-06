/*
 * Copyright (c) 2003-2005 JGGApi Development Team. All Rights Reserved. This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version. This program is distributed in
 * the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a
 * copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package pl.radical.open.gg.event;

import pl.radical.open.gg.IIncommingMessage;
import pl.radical.open.gg.IOutgoingMessage;
import pl.radical.open.gg.IncomingMessage;
import pl.radical.open.gg.MessageStatus;

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
 * @version $Id: MessageListener.java,v 1.1 2005/11/05 23:34:53 winnetou25 Exp $
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

	public static class Stub implements MessageListener {

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
