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
 * Abstract implementation of <code>IMessage</code> that
 * is common for incomming and outgoing messages.
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @version $Id: AbstractMessage.java,v 1.2 2004-12-14 22:52:11 winnetou25 Exp $
 */
public abstract class AbstractMessage implements IMessage {

	private static Log logger = LogFactory.getLog(AbstractMessage.class);

    /** Uin of the Gadu-Gadu user */
    protected int m_uin;

    /** The body of the message */
    protected String m_text;
    
    /** The message class associated with this message */
    protected MessageClass m_messageClass;

    public AbstractMessage(int uin, String text, MessageClass messageClass) {
    	if (uin < 0) throw new IllegalArgumentException("uin cannot be less than 0");
    	if (text == null) throw new NullPointerException("text cannot be null");
    	if (messageClass == null) throw new NullPointerException("messageClass cannot be null");
    	m_uin = uin;
        m_text = text;
        m_messageClass = messageClass;
    }

    /**
     * @see pl.mn.communicator.IMessage#getUin()
     */
    public int getUin() {
        return m_uin;
    }

    /**
     * @see pl.mn.communicator.IMessage#setText(java.lang.String)
     */
    public void setUin(int uin) {
    	if (uin < 0) throw new IllegalArgumentException("uin cannot be less than 0");
    	m_uin = uin;
    }

    /**
     * @see pl.mn.communicator.IMessage#getText()
     */
    public String getText() {
        return m_text;
    }

    /**
     * @see pl.mn.communicator.IMessage#setText(java.lang.String)
     */
    public void setText(String text) {
    	if (text == null) throw new NullPointerException("text cannot be null");
    	m_text = text;
    }

    /**
     * @see pl.mn.communicator.IMessage#getMessageClass();
     */
    public MessageClass getMessageClass() {
    	return m_messageClass;
    }

}
