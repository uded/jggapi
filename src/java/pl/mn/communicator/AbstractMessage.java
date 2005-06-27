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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * An abstract implementation of <code>IMessage</code> that
 * is common for incoming and outgoing messages.
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @version $Id: AbstractMessage.java,v 1.12 2005-06-27 15:48:47 winnetou25 Exp $
 */
public abstract class AbstractMessage implements IMessage {

	private static final Log LOGGER = LogFactory.getLog(AbstractMessage.class);

    /** Uin of the Gadu-Gadu user */
    protected int m_recipientUin = -1;

    /** The body of the message */
    protected String m_messageBody = null;
    
    /** The message class associated with this message */
    protected MessageClass m_messageClass = null;
    
    protected int m_messageID = -1;

    protected AbstractMessage(int recepientUin, String messageBody, MessageClass messageClass) {
    	if (recepientUin < 0) throw new IllegalArgumentException("uin cannot be less than 0");
    	if (messageBody == null) throw new NullPointerException("messageBody cannot be null");
    	if (messageClass == null) throw new NullPointerException("messageClass cannot be null");
    	m_recipientUin = recepientUin;
    	m_messageBody = messageBody;
        m_messageClass = messageClass;
    }

    /**
     * @see pl.mn.communicator.IMessage#getRecipientUin()
     */
    public int getRecipientUin() {
        return m_recipientUin;
    }

    /**
     * @see pl.mn.communicator.IMessage#getMessageBody()
     */
    public String getMessageBody() {
        return m_messageBody;
    }
    
    /**
	 * @see pl.mn.communicator.IMessage#getMessageID()
	 */
	public int getMessageID() {
		return m_messageID;
	}

    /**
     * @see pl.mn.communicator.IMessage#getMessageClass()
     */
    public MessageClass getMessageClass() {
    	return m_messageClass;
    }
    
    /**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "[RecipientUin: "+m_recipientUin+", messageBody: "+m_messageBody+", messageClass: "+m_messageClass+"]";
	}

}
