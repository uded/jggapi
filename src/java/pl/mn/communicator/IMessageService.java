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

import pl.mn.communicator.event.MessageListener;

/**
 * Created on 2004-11-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: IMessageService.java,v 1.2 2004-12-18 15:12:13 winnetou25 Exp $
 */
public interface IMessageService {

	/** 
	 * Sends the message to Gadu-Gadu server.
	 * 
	 * @param outgoingMessage
	 */
	void sendMessage(OutgoingMessage outgoingMessage) throws GGException;

	/** 
	 * Adds <code>MessageListener</code> to listen for message events.
	 * 
	 * @param messageListener
	 */
	void addMessageListener(MessageListener messageListener);
	
	/**
	 * Remove <code>MessageListener</code> that listened for message events.
	 * 
	 * @param messageListener
	 */
	void removeMessageListener(MessageListener messageListener);
	
}
