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
package pl.mn.communicator.event;

import java.util.Date;
import java.util.EventObject;

import pl.mn.communicator.IMessage;

/**
 * Message sent event.
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: MessageSentEvent.java,v 1.1 2004-12-12 00:29:34 winnetou25 Exp $
 */
public class MessageSentEvent extends EventObject {

	private IMessage m_message = null;
	private Date m_sentDate = null;
	
	public MessageSentEvent(Object source, IMessage message, Date sentDate) {
		super(source);
		m_message = message;
		m_sentDate = sentDate;
	}

	public IMessage getMessage() {
		return m_message;
	}
	
	public Date getSentDate() {
		return m_sentDate;
	}
	
}
