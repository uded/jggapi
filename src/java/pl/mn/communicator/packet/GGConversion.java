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
package pl.mn.communicator.packet;

import java.util.Date;

import pl.mn.communicator.IStatus;
import pl.mn.communicator.MessageClass;
import pl.mn.communicator.MessageStatus;
import pl.mn.communicator.RemoteStatus;
import pl.mn.communicator.StatusType;
import pl.mn.communicator.User;
import pl.mn.communicator.packet.in.GGSendMsgAck;
import pl.mn.communicator.packet.in.GGStatus;

/**
 * Created on 2005-01-31
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGConversion.java,v 1.2 2005-10-08 23:49:29 winnetou25 Exp $
 */
public class GGConversion {

	public static User.UserMode getUserMode(int protocolStatus) {
		if ((protocolStatus & GGStatus.GG_STATUS_FRIENDS_MASK) == GGStatus.GG_STATUS_FRIENDS_MASK) {
			return User.UserMode.FRIEND;
		}
		if ((protocolStatus & GGStatus.GG_STATUS_BLOCKED) == GGStatus.GG_STATUS_BLOCKED) {
			return User.UserMode.BLOCKED;
		}
		return User.UserMode.UNKNOWN;
	}
	
	public static byte getProtocolUserMode(User.UserMode userMode) {
		if (userMode == User.UserMode.BUDDY) return GGUser.GG_USER_BUDDY;
		if (userMode == User.UserMode.BLOCKED) return GGUser.GG_USER_BLOCKED;
		if (userMode == User.UserMode.FRIEND) return GGUser.GG_USER_FRIEND;
		
		return GGUser.GG_USER_UNKNOWN;
	}
	
	public static RemoteStatus getClientRemoteStatus(int status, String description, long returnTimeInMillis) {
		RemoteStatus remoteStatus = null;
		
		switch (status) {
		case GGStatus.GG_STATUS_AVAIL: 
			remoteStatus = new RemoteStatus(StatusType.ONLINE);
			break;
			
		case GGStatus.GG_STATUS_AVAIL_DESCR:
			remoteStatus = new RemoteStatus(StatusType.ONLINE_WITH_DESCRIPTION);
			remoteStatus.setDescription(description);
			break;
			
		case GGStatus.GG_STATUS_BUSY:
			remoteStatus = new RemoteStatus(StatusType.BUSY);
			break;
			
		case GGStatus.GG_STATUS_BUSY_DESCR:
			remoteStatus = new RemoteStatus(StatusType.BUSY_WITH_DESCRIPTION);
			remoteStatus.setDescription(description);
			break;
			
		case GGStatus.GG_STATUS_INVISIBLE:
			remoteStatus = new RemoteStatus(StatusType.INVISIBLE);
			break;

		case GGStatus.GG_STATUS_INVISIBLE_DESCR:
			remoteStatus = new RemoteStatus(StatusType.INVISIBLE_WITH_DESCRIPTION);
			remoteStatus.setDescription(description);
			break;
		
		case GGStatus.GG_STATUS_NOT_AVAIL:
			remoteStatus = new RemoteStatus(StatusType.OFFLINE);
			break;

		case GGStatus.GG_STATUS_NOT_AVAIL_DESCR:
			remoteStatus = new RemoteStatus(StatusType.OFFLINE);
			remoteStatus.setDescription(description);
			break;
		}
		
		if (remoteStatus != null && returnTimeInMillis != -1) {
			remoteStatus.setReturnDate(new Date(returnTimeInMillis));
		}
		
		if ((status & GGStatus.GG_STATUS_BLOCKED) == GGStatus.GG_STATUS_BLOCKED) {
			remoteStatus.setBlocked(true);
		} else {
			remoteStatus.setBlocked(false);
		}
		
		return remoteStatus;
	}
	
	public static int getProtocolStatus(IStatus clientStatus, boolean isFriendsOnly, boolean isBlocked) {
		if (clientStatus == null) throw new NullPointerException("clientStatus cannot be null.");

		int protocolStatus = -1;
		
		if (clientStatus.getStatusType() == StatusType.ONLINE) {
			protocolStatus = GGStatus.GG_STATUS_AVAIL;
		} else if (clientStatus.getStatusType() == StatusType.ONLINE_WITH_DESCRIPTION) {
			protocolStatus = GGStatus.GG_STATUS_AVAIL_DESCR;
		} else if (clientStatus.getStatusType() == StatusType.BUSY) {
			protocolStatus = GGStatus.GG_STATUS_BUSY;
		} else if (clientStatus.getStatusType() == StatusType.BUSY_WITH_DESCRIPTION) {
			protocolStatus = GGStatus.GG_STATUS_BUSY_DESCR;
		} else if (clientStatus.getStatusType() == StatusType.OFFLINE) {
			protocolStatus = GGStatus.GG_STATUS_NOT_AVAIL;
		} else if (clientStatus.getStatusType() == StatusType.OFFLINE_WITH_DESCRIPTION) {
			protocolStatus = GGStatus.GG_STATUS_NOT_AVAIL_DESCR;
		} else if (clientStatus.getStatusType() == StatusType.INVISIBLE) {
			protocolStatus = GGStatus.GG_STATUS_INVISIBLE;
		} else if (clientStatus.getStatusType() == StatusType.INVISIBLE_WITH_DESCRIPTION) {
			protocolStatus = GGStatus.GG_STATUS_INVISIBLE_DESCR;
		}

		if (protocolStatus != -1) {
			if (isFriendsOnly) protocolStatus |= GGStatus.GG_STATUS_FRIENDS_MASK;
			if (isBlocked) 	protocolStatus |= GGStatus.GG_STATUS_BLOCKED;
			return protocolStatus;
		}

		return GGStatus.GG_STATUS_UNKNOWN;
	}
	
	public static MessageStatus getClientMessageStatus(int protocolMessageStatus) {
		switch (protocolMessageStatus) {
			case GGSendMsgAck.GG_ACK_DELIVERED: return MessageStatus.DELIVERED;
			case GGSendMsgAck.GG_ACK_NOT_DELIVERED: return MessageStatus.NOT_DELIVERED;
			case GGSendMsgAck.GG_ACK_BLOCKED: return MessageStatus.BLOCKED;
			case GGSendMsgAck.GG_ACK_MBOXFULL: return MessageStatus.BLOCKED_MBOX_FULL;
			case GGSendMsgAck.GG_ACK_QUEUED: return MessageStatus.QUEUED;
			
			default: return MessageStatus.UNKNOWN;
		}
	}
	
	public static MessageClass getClientMessageClass(int protocolMessageClass) {
		switch (protocolMessageClass) {
			case GGMessageClass.GG_CLASS_ACK: return MessageClass.DO_NOT_CONFIRM;
			case GGMessageClass.GG_CLASS_CHAT: return MessageClass.CHAT;
			case GGMessageClass.GG_CLASS_CTCP: return MessageClass.PING;
			case GGMessageClass.GG_CLASS_MSG: return MessageClass.MESSAGE;
			case GGMessageClass.GG_CLASS_QUEUED: return MessageClass.QUEUED;
			case GGMessageClass.GG_CLASS_QUEUED2: return MessageClass.QUEUED;
			
			default: return MessageClass.UNKNOWN;
		}
	}
	
	public static int getProtocolMessageClass(MessageClass clientMessageClass) {
		
		if (clientMessageClass == MessageClass.CHAT) return GGMessageClass.GG_CLASS_CHAT;
		if (clientMessageClass == MessageClass.DO_NOT_CONFIRM) return GGMessageClass.GG_CLASS_ACK;
		if (clientMessageClass == MessageClass.MESSAGE) return GGMessageClass.GG_CLASS_MSG;
		if (clientMessageClass == MessageClass.QUEUED) return GGMessageClass.GG_CLASS_QUEUED;
		if (clientMessageClass == MessageClass.PING) return GGMessageClass.GG_CLASS_CTCP;
		
		return GGMessageClass.GG_CLASS_UNKNOWN;
	}

}
