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

/**
 * Created on 2004-12-11
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: MessageClass.java,v 1.7 2004-12-27 13:58:05 winnetou25 Exp $
 */
public class MessageClass {

	private String m_messageClass = null;
	
	private MessageClass(String messageClass) {
		m_messageClass = messageClass;
	}
	
	/** the message has been queued because the user it not available */
	public final static MessageClass QUEUED = new MessageClass("message_class_queued");
	
	/** The message will popup ip new window */
	public final static MessageClass IN_NEW_WINDOW = new MessageClass("message_class_in_new_window");
	
	/** the message is a part of conversation */
	public final static MessageClass CHAT  = new MessageClass("message_class_chat");
	
	/** this means that we do not want to receive confirmation from Gadu-Gadu server of delivery of this message */
	public final static MessageClass DO_NOT_CONFIRM = new MessageClass("message_class_do_not_confirm");
	
	/** this means that message is ping only and the user will not see anyhow whether or not we are sending this message */
	public final static MessageClass PING = new MessageClass("message_class_ping");
	
	/**
	 * @see java.lang.Object#toString()
	 */
    public String toString() {
        return m_messageClass;
    }
    
    /**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return m_messageClass.hashCode();
	}
	
}
