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
import java.util.ArrayList;
import java.util.Enumeration;

import pl.mn.communicator.GGException;
import pl.mn.communicator.IChat;
import pl.mn.communicator.IGroupChat;
import pl.mn.communicator.IncomingMessage;
import pl.mn.communicator.MessageStatus;
import pl.mn.communicator.OutgoingMessage;
import pl.mn.communicator.event.MessageListener;
import pl.mn.communicator.packet.out.GGSendMsg;

/**
 * Created on 2005-01-29
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GroupChat.java,v 1.3 2005-01-29 15:44:17 winnetou25 Exp $
 */
public class GroupChat extends AbstractChat implements IGroupChat {
	
	private ArrayList m_recipientUins = new ArrayList(); //users with whom we chat
	
	//friendly
	GroupChat(Session session) {
		this(session, new int[0]);
	}

	GroupChat(Session session, int[] recipientUins) {
		super(session);
		for (int i=0; i<recipientUins.length; i++) {
			addRecipient(recipientUins[i]);
		}
	}

	public IChat sendMessage(String messageBody) throws GGException {
		if (messageBody == null) throw new NullPointerException("messageBody cannot be less than 0");

		if (m_recipientUins.isEmpty()) {
			throw new GGException("Unable to send message, at least one recipient is required");
		}
		
		int recipientUin = ((Integer)m_recipientUins.get(0)).intValue();
		GGSendMsg sendMsg = new GGSendMsg(OutgoingMessage.createChatMessage(recipientUin, messageBody));

		for (int i=1; i<m_recipientUins.size(); i++) {
			int recipient = ((Integer)m_recipientUins.get(i)).intValue();
			sendMsg.addRecipient(recipient);
		}
		
		try {
			m_session.getSessionAccessor().sendPackage(sendMsg);
		} catch (IOException ex) {
			throw new GGException("Unable to send group chat message");
		}
		
		return this;
	}

	public void addRecipient(int recipientUin) {
		m_recipientUins.add(new Integer(recipientUin));
	}
	
	public void removeRecipient(int recipientUin) {
		m_recipientUins.remove(new Integer(recipientUin));
	}

	public int[] getRecipientUins() {
		int[] recipientUins = new int[m_recipientUins.size()];
		for (int i=0; i<m_recipientUins.size(); i++) {
			recipientUins[i] = ((Integer)m_recipientUins.get(i)).intValue();
		}
		return recipientUins;
	}

	//	/**
//	 * @see pl.mn.communicator.IChat#nextMessage()
//	 */
//	public IMessage nextMessage() {
//		// TODO Auto-generated method stub
//		return null;
//	}

	/**
	 * @see pl.mn.communicator.packet.handlers.AbstractChat#getMessageHandler()
	 */
	protected MessageListener getMessageHandler() {
		return new MessageHandler();
	}
	
	protected void fireChatMessageArrived(IncomingMessage message) {
		for (Enumeration e = m_listeners.elements(); e.hasMoreElements();) {
			MessageListener listener = (MessageListener) e.nextElement();
			listener.messageArrived(message);
		}
	}
	
	protected void fireChatMessageDelivered(int uin, int messageID, MessageStatus deliveryStatus) {
		for (Enumeration e = m_listeners.elements(); e.hasMoreElements();) {
			MessageListener listener = (MessageListener) e.nextElement();
			listener.messageDelivered(uin, messageID, deliveryStatus);
		}
	}
	
	private class MessageHandler implements MessageListener {
		
		/**
		 * @see pl.mn.communicator.event.MessageListener#messageArrived(pl.mn.communicator.IncomingMessage)
		 */
		public void messageArrived(IncomingMessage incommingMessage) {
			if (isInThisGroupChat(incommingMessage.getUin())) {
				fireChatMessageArrived(incommingMessage);
			}
		}
		
		/**
		 * @see pl.mn.communicator.event.MessageListener#messageDelivered(int, int, pl.mn.communicator.MessageStatus)
		 */
		public void messageDelivered(int uin, int messageID, MessageStatus deliveryStatus) {
			if (isInThisGroupChat(uin)) {
				fireChatMessageDelivered(uin, messageID, deliveryStatus);
			}
		}
		
		private boolean isInThisGroupChat(int uin) {
			for (int i=0; i<m_recipientUins.size(); i++) {
				if (uin == ((Integer)m_recipientUins.get(i)).intValue()) {
					return true;
				}
			}
			
			return false;
		}
		
	}
	
}
