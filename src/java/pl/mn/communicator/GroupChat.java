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

import java.io.IOException;
import java.util.ArrayList;

import pl.mn.communicator.packet.out.GGSendMsg;

/**
 * Created on 2005-01-29
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GroupChat.java,v 1.1 2005-05-08 14:49:26 winnetou25 Exp $
 */
public class GroupChat extends AbstractChat implements IGroupChat {
	
	private ArrayList m_recipientUins = new ArrayList(); //users with whom we chat
	
	//friendly
	GroupChat(Session session) {
		this(session, new int[0]);
	}

	//friendly
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
			int recipient = ((Integer) m_recipientUins.get(i)).intValue();
			sendMsg.addAdditionalRecipient(recipient);
		}
		
		try {
			m_session.getSessionAccessor().sendPackage(sendMsg);
		} catch (IOException ex) {
			throw new GGException("Unable to send group chat message");
		}
		
		return this;
	}

	public void addRecipient(int recipientUin) {
		if (recipientUin < 0) throw new IllegalArgumentException("recipientUin cannot be less than 0");
		m_recipientUins.add(new Integer(recipientUin));
	}
	
	public void removeRecipient(int recipientUin) {
		if (recipientUin < 0) throw new IllegalArgumentException("recipientUin cannot be less than 0");
		m_recipientUins.remove(new Integer(recipientUin));
	}

	public int[] getRecipientUins() {
		int[] recipientUins = new int[m_recipientUins.size()];
		for (int i=0; i<m_recipientUins.size(); i++) {
			recipientUins[i] = ((Integer)m_recipientUins.get(i)).intValue();
		}
		return recipientUins;
	}
	
	/**
	 * @see pl.mn.communicator.AbstractChat#acceptsIncoming(pl.mn.communicator.IncomingMessage)
	 */
	protected boolean acceptsIncoming(IncomingMessage incomingMessage) {
		return isRegisteredRecipient(incomingMessage.getMessageID());
	}

	/**
	 * @see pl.mn.communicator.AbstractChat#acceptsOutgoing(int, int, pl.mn.communicator.MessageStatus)
	 */
	protected boolean acceptsOutgoing(int uin, int messageID, MessageStatus deliveryStatus) {
		return isRegisteredRecipient(uin);
	}
	
	private boolean isRegisteredRecipient(int uin) {
		for (int i=0; i<m_recipientUins.size(); i++) {
			if (uin == ((Integer) m_recipientUins.get(i)).intValue()) {
				return true;
			}
		}
		
		return false;
	}

}
