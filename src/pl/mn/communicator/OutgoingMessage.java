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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Created on 2004-11-21
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: OutgoingMessage.java,v 1.1 2004-12-11 16:25:57 winnetou25 Exp $
 */
public class OutgoingMessage extends AbstractMessage {

	private static Log logger = LogFactory.getLog(OutgoingMessage.class);

    public OutgoingMessage(int toUser, String text, MessageClass messageClass) {
    	super(toUser, text, messageClass);
    }
    
	public static OutgoingMessage createMessage(int uin, String messageText) {
		return new OutgoingMessage(uin, messageText, MessageClass.IN_NEW_WINDOW);
	}

	/**
	 * Creates an outgoing message that is just a ping.
	 * @param toUser
	 */
	public static OutgoingMessage createPingMessage(int toUser) {
		return new OutgoingMessage(toUser, "", MessageClass.PING);
	}

	/**
	 * Creates an outgoing message and sends information that we do not
	 * want to receive confirmation whether this message was successfuly delivered or not.
	 * 
	 * @param toUser
	 * @param text
	 * @return OutgoingMessage
	 */
	public static OutgoingMessage createMessageWithoutConfirmation(int toUser, String text) {
		return new OutgoingMessage(toUser, text, MessageClass.DO_NOT_CONFIRM);
	}
	
}
