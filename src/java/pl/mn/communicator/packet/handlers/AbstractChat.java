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
package pl.mn.communicator.packet.handlers;

import java.util.Vector;

import pl.mn.communicator.IChat;
import pl.mn.communicator.ISession;
import pl.mn.communicator.event.MessageListener;

/**
 * Created on 2005-01-29
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: AbstractChat.java,v 1.1 2005-01-29 13:20:05 winnetou25 Exp $
 */
public abstract class AbstractChat implements IChat {

	protected Vector m_listeners = new Vector();
	protected ISession m_session = null;

	protected AbstractChat(ISession session) {
		if (session == null) throw new NullPointerException("session cannot be null");
		m_session = session;
		session.getMessageService().addMessageListener(getMessageHandler());
	}

	public void addChatListener(MessageListener messageListener) {
		if (messageListener == null) throw new NullPointerException("messageListener cannot be null");
		m_listeners.add(messageListener);
	}
	
	public void removeChatListener(MessageListener messageListener) {
		if (messageListener == null) throw new NullPointerException("messageListener cannot be null");
		m_listeners.remove(messageListener);
	}
	
	protected abstract MessageListener getMessageHandler();

}
