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
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @version $Id: AbstractMessage.java,v 1.1 2004-12-14 21:53:51 winnetou25 Exp $
 */
public abstract class AbstractMessage implements IMessage {

	private static Log logger = LogFactory.getLog(AbstractMessage.class);

    /** Uin of the Gadu-Gadu user */
    protected int m_uin;

    /** The body of the message */
    protected String m_text;
    
    protected MessageClass m_messageClass;

    public AbstractMessage(int uin, String text, MessageClass messageClass) {
        m_uin = uin;
        m_text = text;
        m_messageClass = messageClass;
    }

    public int getUin() {
        return m_uin;
    }

    /**
     * @return String
     */
    public String getText() {
        return m_text;
    }

    public void setText(String text) {
    	if (text == null) throw new NullPointerException("text cannot be null");
    	m_text = text;
    }

    public void setUin(int uin) {
    	if (uin < 0) throw new IllegalArgumentException("uin cannot be less than 0");
    	m_uin = uin;
    }
    
    /**
     * Gets MessageClass of this message.
     * @return messageClass attached to this message.
     */
    public MessageClass getMessageClass() {
    	return m_messageClass;
    }

}
