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
package pl.mn.communicator;

import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The class that represents message that will be sent to the Gadu-Gadu server.
 * <p>
 * Created on 2004-11-21
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: OutgoingMessage.java,v 1.9 2004-12-25 17:32:07 winnetou25 Exp $
 */
public class OutgoingMessage extends AbstractMessage {

	private final static Random RANDOM = new Random();
	
	private static Log logger = LogFactory.getLog(OutgoingMessage.class);

	//private constructor
    private OutgoingMessage(int uin, String text, MessageClass messageClass) {
    	super(uin, text, messageClass);
        m_uin = RANDOM.nextInt(999999);
    }
    
    /** 
     * Creates an outgoing message that will be poped up in a new window.
     * 
     * @param uin Gadu-Gadu number to of the user to whom this message is addressed.
     * @param messageBody the body of the message.
     * @return OutgoingMessage outgoing message.
     */
	public static OutgoingMessage createNewMessage(int uin, String messageBody) {
		return new OutgoingMessage(uin, messageBody, MessageClass.IN_NEW_WINDOW);
	}

    /** 
     * Creates an outgoing message that is a part of a previous conversation
     * 
     * @param uin Gadu-Gadu number to of the user to whom this message is addressed.
     * @param messageBody the body of the message.
     * @return OutgoingMessage outgoing message.
     */
	public static OutgoingMessage createChatMessage(int uin, String messageBody) {
		return new OutgoingMessage(uin, messageBody, MessageClass.CHAT);
	}

    /** 
     * Creates an outgoing message that only pings the user.
     * 
     * @param uin Gadu-Gadu number to of the user to whom this ping is addressed.
     * @return OutgoingMessage outgoing message.
     */
	public static OutgoingMessage createPingMessage(int uin) {
		return new OutgoingMessage(uin, "", MessageClass.PING);
	}

    /** 
     * Creates an outgoing message and sets a special flag
     * that will notify Gadu-Gadu server that we do not want to receive the
     * confirmation from the server that this message was delivered to the user
     * it is addressed.
     * 
     * @param uin Gadu-Gadu number to of the user to whom this message is addressed.
     * @param messageBody the body of the message.
     * @return OutgoingMessage outgoing message.
     */
	public static OutgoingMessage createMessageWithoutConfirmation(int uin, String messageBody) {
		return new OutgoingMessage(uin, messageBody, MessageClass.DO_NOT_CONFIRM);
	}
	
	/**
	 * Use this method if you want to set new message body on this message.
	 * 
	 * @param messageBody the new message body.
	 * @throws NullPointerException if the messageBody object is null.
	 */
	public void setMessageBody(String messageBody) {
		if (messageBody == null) throw new NullPointerException("messageBody cannot be null");
		m_messageBody = messageBody;
	}
	
	/**
	 * Use this method if you want to set new uin on this message.
	 * 
	 * @param uin the new Gadu-Gadu number to whom this message will be addressed.
	 * @throws IllegalArgumentException if the uin is a negative value.
	 */
	public void setUin(int uin) {
		if (uin < 0) throw new IllegalArgumentException("uin cannot be less than 0");
		m_uin = uin;
	}
	
}
