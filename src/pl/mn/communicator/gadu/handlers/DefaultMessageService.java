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
package pl.mn.communicator.gadu.handlers;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import pl.mn.communicator.GGException;
import pl.mn.communicator.GGSessionException;
import pl.mn.communicator.IMessageService;
import pl.mn.communicator.MessageArrivedEvent;
import pl.mn.communicator.MessageDeliveredEvent;
import pl.mn.communicator.MessageListener;
import pl.mn.communicator.OutgoingMessage;
import pl.mn.communicator.SessionState;
import pl.mn.communicator.gadu.GGSendMsg;

/**
 * Created on 2004-11-28
 * 
 * @author mateusz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DefaultMessageService implements IMessageService {

	private Session m_session = null;
	private Set m_messageListeners = null;
	
	public DefaultMessageService(Session session) {
		if (session == null) throw new NullPointerException("session cannot be null");
		m_session = session;
		m_messageListeners = new HashSet();
	}
	
	/**
	 * @see pl.mn.communicator.IMessageService#sendMessage(pl.mn.communicator.OutgoingMessage)
	 */
	public void sendMessage(OutgoingMessage outgoingMessage) throws GGException {
		if (m_session.getSessionState() == SessionState.AUTHENTICATED) {
			try {
				GGSendMsg messageOut = new GGSendMsg(outgoingMessage);
				m_session.getSessionAccessor().sendPackage(messageOut);
			} catch (IOException ex) {
				throw new GGException("Error occured while sending message: "+outgoingMessage, ex);
			}
		} else {
			throw new GGSessionException("Incorrect session state: "+SessionState.getState(m_session.getSessionState()));
		}
	}
	
	/**
	 * @see pl.mn.communicator.IMessageService#addMessageListener(pl.mn.communicator.MessageListener)
	 */
	public void addMessageListener(MessageListener messageListener) {
		if (messageListener == null) throw new NullPointerException("messageListener cannot be null");
		m_messageListeners.add(messageListener);
	}

	/**
	 * @see pl.mn.communicator.IMessageService#removeMessageListener(pl.mn.communicator.MessageListener)
	 */
	public void removeMessageListener(MessageListener messageListener) {
		if (messageListener == null) throw new NullPointerException("messageListener cannot be null");
		m_messageListeners.remove(messageListener);
	}
	
	protected void notifyMessageArrived(MessageArrivedEvent messageArrivedEvent) {
		if (messageArrivedEvent == null) throw new NullPointerException("messageArrivedEvent cannot be null");
		for (Iterator it = m_messageListeners.iterator(); it.hasNext();) {
			MessageListener messageListener = (MessageListener) it.next();
			messageListener.messageArrived(messageArrivedEvent);
		}
	}

	protected void notifyMessageDelivered(MessageDeliveredEvent messageDeliveredEvent) {
		if (messageDeliveredEvent == null) throw new NullPointerException("messageDeliveredEvent cannot be null");
		for (Iterator it = m_messageListeners.iterator(); it.hasNext();) {
			MessageListener messageListener = (MessageListener) it.next();
			messageListener.messageDelivered(messageDeliveredEvent);
		}
	}

}
