/*
 * Copyright (c) 2003 Marcin Naglik (mnaglik@gazeta.pl)
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
package pl.mn.communicator;

/**
 * Listener wiadomo¶ci.<BR>
 * Obs³uguje zdarzenia zwi±zane z wiadomo¶ciami.<BR>
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @version $Id: MessageListener.java,v 1.11 2004-11-11 18:42:26 winnetou25 Exp $
 */
public interface MessageListener {
	
	/**
     * Message arrived
     * @param MessageArrivedEvent
     */
	void messageArrived(MessageArrivedEvent arrivedEvent);

	/**
	 * Message sent.
	 * @param sentEvent
	 */
	void messageSent(MessageSentEvent sentEvent);
	
	/**
	 * Message delivered
	 * @param event object 
	 */
    void messageDelivered(MessageDeliveredEvent sentEvent);
    
    public final static class MessageStub implements MessageListener {

		/**
		 * @see pl.mn.communicator.MessageListener#messageArrived(pl.mn.communicator.MessageArrivedEvent)
		 */
		public void messageArrived(MessageArrivedEvent arrivedEvent) { }

		/**
		 * @see pl.mn.communicator.MessageListener#messageSent(pl.mn.communicator.MessageSentEvent)
		 */
		public void messageSent(MessageSentEvent sentEvent) { }

		/**
		 * @see pl.mn.communicator.MessageListener#messageDelivered(pl.mn.communicator.MessageDeliveredEvent)
		 */
		public void messageDelivered(MessageDeliveredEvent sentEvent) { }

    }
        
}
