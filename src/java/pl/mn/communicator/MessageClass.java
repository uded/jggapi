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
 * @version $Id: MessageClass.java,v 1.4 2004-12-19 21:14:06 winnetou25 Exp $
 */
public class MessageClass {

	private String m_messageClass = null;
	
	private MessageClass(String messageClass) {
		m_messageClass = messageClass;
	}
	
	public final static MessageClass QUEUED = new MessageClass("message_class_queued");
	public final static MessageClass IN_NEW_WINDOW = new MessageClass("message_class_in_new_window");
	public final static MessageClass CHAT  = new MessageClass("message_class_chat");
	public final static MessageClass DO_NOT_CONFIRM = new MessageClass("message_class_do_not_confirm");
	public final static MessageClass PING = new MessageClass("message_class_ping");
	
	
    public String toString() {
        return m_messageClass;
    }

    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
	
}
