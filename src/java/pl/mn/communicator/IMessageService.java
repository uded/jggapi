/*
 * Copyright (c) 2003-2005 <a href="http://jggapi.sourceforge.net/team-list.html">JGGApi Development Team</a> All Rights Reserved.
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

import pl.mn.communicator.event.MessageListener;

/**
 * The client should use this interface if it is interested in sending message to
 * Gadu-Gadu server or if it wants to be interested in message related events.
 * <p>
 * Created on 2004-11-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: IMessageService.java,v 1.5 2004-12-19 18:50:44 winnetou25 Exp $
 */
public interface IMessageService {

	/**
	 * Invocation of this method sends the message
	 * to the Gadu-Gadu server.
	 * 
	 * @param outgoingMessage the message that will be sent to the server.
	 * @throws GGException if there is an error while sending message to server.
	 * @throws GGSessionException if user is not logged in.
	 * @throws NullPointerException if the outgoingMessage is null.
	 */
	void sendMessage(OutgoingMessage outgoingMessage) throws GGException;

	/** 
	 * Adds <code>MessageListener</code> object to the list
	 * that will be notified of message related events.
	 * 
	 * @see pl.mn.communicator.event.MessageListener
	 * @param messageListener the <code>MessageListener</code> instance to be notified.
	 * @throws NullPointerException if the messageListener is null.
	 */
	void addMessageListener(MessageListener messageListener);
	
	/**
	 * Remove <code>MessageListener</code> from the list that
	 * will be notified of message related events.
	 * 
	 * @see pl.mn.communicator.event.MessageListener
	 * @param messageListener the <code>MessageListener</code> instance that will no longer be notified.
	 * @throws NullPointerException if the messageListener is null.
	 */
	void removeMessageListener(MessageListener messageListener);
	
}
