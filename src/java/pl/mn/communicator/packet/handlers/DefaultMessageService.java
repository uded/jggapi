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

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import pl.mn.communicator.GGException;
import pl.mn.communicator.GGSessionException;
import pl.mn.communicator.IMessageService;
import pl.mn.communicator.IncomingMessage;
import pl.mn.communicator.MessageStatus;
import pl.mn.communicator.OutgoingMessage;
import pl.mn.communicator.SessionState;
import pl.mn.communicator.event.MessageListener;
import pl.mn.communicator.packet.out.GGSendMsg;

/**
 * The default implementation of <code>IMessageService</code>.
 * <p>
 * Created on 2004-11-28
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: DefaultMessageService.java,v 1.8 2004-12-19 21:14:12 winnetou25 Exp $
 */
public class DefaultMessageService implements IMessageService {

	private Session m_session = null;
	private Set m_messageListeners = null;
	
	//friendly
	DefaultMessageService(Session session) {
		if (session == null) throw new NullPointerException("session cannot be null");
		m_session = session;
		m_messageListeners = new HashSet();
	}
	
	/**
	 * @see pl.mn.communicator.IMessageService#sendMessage(pl.mn.communicator.OutgoingMessage)
	 */
	public void sendMessage(OutgoingMessage outgoingMessage) throws GGException {
		if (outgoingMessage == null) throw new NullPointerException("outgoingMessage cannot be null");
		checkSessionState();
		try {
			GGSendMsg messageOut = new GGSendMsg(outgoingMessage);
			m_session.getSessionAccessor().sendPackage(messageOut);
		} catch (IOException ex) {
			throw new GGException("Error occured while sending message: "+outgoingMessage, ex);
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

	//TODO clone list before notifing
	protected void notifyMessageArrived(IncomingMessage incommingMessage) {
		if (incommingMessage == null) throw new NullPointerException("incommingMessage cannot be null");
		for (Iterator it = m_messageListeners.iterator(); it.hasNext();) {
			MessageListener messageListener = (MessageListener) it.next();
			messageListener.messageArrived(incommingMessage);
		}
	}

	//TODO clone list before notifing
	protected void notifyMessageDelivered(int uin, int messageID, MessageStatus messageStatus) {
		if (uin < 0) throw new IllegalArgumentException("uin cannot be less than 0");
		if (messageID <0) throw new IllegalArgumentException("messageID cannot be less than 0");
		if (messageStatus == null) throw new NullPointerException("messageStatus cannot be less than 0");
		for (Iterator it = m_messageListeners.iterator(); it.hasNext();) {
			MessageListener messageListener = (MessageListener) it.next();
			messageListener.messageDelivered(uin, messageID, messageStatus);
		}
	}

	private void checkSessionState() {
		if (m_session.getSessionState() != SessionState.LOGGED_IN) {
			throw new GGSessionException(m_session.getSessionState());
		}
	}
	
}
