/*
 * Copyright (c) 2003-2005 JGGApi Development Team. All Rights Reserved.
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package pl.mn.communicator.event;

import java.util.EventListener;

import pl.mn.communicator.IncomingMessage;
import pl.mn.communicator.MessageStatus;

/**
 * The listener interface that is notified of message related events.
 * <p>
 * The classes that implement this interface are notified whether
 * message has arrived from Gadu-Gadu server or if the message the client sent
 * was successully delivered to the user to whom it was addressed.
 * <p>
 * Created on 2004-11-27
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: MessageListener.java,v 1.8 2004-12-19 21:19:58 winnetou25 Exp $
 */
public interface MessageListener extends EventListener {
	
	/**
     * Notification that a message arrived from
     * Gadu-Gadu server.
     * 
     * @param <code>IncomingMessage</code> object.
     */
	void messageArrived(IncomingMessage incommingMessage);

	/**
	 * Notification that the message was successfully delivered to the recipient.
	 * 
	 * @param uin the Gadu-Gadu number of the user to whom the message was addressed.
	 * @param messageID the unique message id that was generated before sending message.
	 * @param deliveryStatus the delivery status of the message.
	 */
    void messageDelivered(int uin, int messageID, MessageStatus deliveryStatus);
    
    public final static class Stub implements MessageListener {

		/**
		 * @see pl.mn.communicator.event.MessageListener#messageArrived(pl.mn.communicator.MessageArrivedEvent)
		 */
		public void messageArrived(IncomingMessage incommingMessage) { }

		/**
		 * @see pl.mn.communicator.event.MessageListener#messageDelivered(pl.mn.communicator.MessageDeliveredEvent)
		 */
		public void messageDelivered(int uin, int messageID, MessageStatus deliveryStatus) { }

    }
        
}
