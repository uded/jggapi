package pl.radical.open.gg.utils;

import pl.radical.open.gg.IStatus;
import pl.radical.open.gg.MessageClass;
import pl.radical.open.gg.RemoteStatus;
import pl.radical.open.gg.User;
import pl.radical.open.gg.packet.dicts.GGMessageClass;
import pl.radical.open.gg.packet.dicts.GGStatuses;
import pl.radical.open.gg.packet.dicts.GGUser;
import pl.radical.open.gg.packet.dicts.MessageStatus;
import pl.radical.open.gg.packet.dicts.StatusType;
import pl.radical.open.gg.packet.in.GGSendMsgAck;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on 2005-01-31
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public final class GGConversion {
	private static final Logger LOG = LoggerFactory.getLogger(GGConversion.class);

	public static User.UserMode getUserMode(final int protocolStatus) {
		if ((protocolStatus & GGStatuses.GG_STATUS_FRIENDS_MASK) == GGStatuses.GG_STATUS_FRIENDS_MASK) {
			return User.UserMode.FRIEND;
		}
		if ((protocolStatus & GGStatuses.GG_STATUS_BLOCKED) == GGStatuses.GG_STATUS_BLOCKED) {
			return User.UserMode.BLOCKED;
		}

		LOG.warn("Unable to convert from protocolUserMode, falling back to unknown.");

		return User.UserMode.UNKNOWN;
	}

	public static byte getProtocolUserMode(final User.UserMode userMode) {
		if (userMode == User.UserMode.BUDDY) {
			return GGUser.GG_USER_BUDDY;
		}
		if (userMode == User.UserMode.BLOCKED) {
			return GGUser.GG_USER_BLOCKED;
		}
		if (userMode == User.UserMode.FRIEND) {
			return GGUser.GG_USER_FRIEND;
		}

		LOG.warn("Unable to convert userMode to protocolUserMode, falling back to unknown.");

		return GGUser.GG_USER_UNKNOWN;
	}

	public static RemoteStatus getClientRemoteStatus(final int status, final String description, final long returnTimeInMillis) {
		RemoteStatus remoteStatus = null;

		switch (status) {
			case GGStatuses.GG_STATUS_AVAIL:
				remoteStatus = new RemoteStatus(StatusType.ONLINE);
				break;

			case GGStatuses.GG_STATUS_AVAIL_DESCR:
				remoteStatus = new RemoteStatus(StatusType.ONLINE_WITH_DESCRIPTION);
				remoteStatus.setDescription(description);
				break;

			case GGStatuses.GG_STATUS_BUSY:
				remoteStatus = new RemoteStatus(StatusType.BUSY);
				break;

			case GGStatuses.GG_STATUS_BUSY_DESCR:
				remoteStatus = new RemoteStatus(StatusType.BUSY_WITH_DESCRIPTION);
				remoteStatus.setDescription(description);
				break;

			case GGStatuses.GG_STATUS_INVISIBLE:
				remoteStatus = new RemoteStatus(StatusType.INVISIBLE);
				break;

			case GGStatuses.GG_STATUS_INVISIBLE_DESCR:
				remoteStatus = new RemoteStatus(StatusType.INVISIBLE_WITH_DESCRIPTION);
				remoteStatus.setDescription(description);
				break;

			case GGStatuses.GG_STATUS_NOT_AVAIL:
				remoteStatus = new RemoteStatus(StatusType.OFFLINE);
				break;

			case GGStatuses.GG_STATUS_NOT_AVAIL_DESCR:
				remoteStatus = new RemoteStatus(StatusType.OFFLINE);
				remoteStatus.setDescription(description);
				break;

				// FIXME Default??
		}

		if (remoteStatus != null && returnTimeInMillis != -1) {
			remoteStatus.setReturnDate(new Date(returnTimeInMillis));
		}

		if ((status & GGStatuses.GG_STATUS_BLOCKED) == GGStatuses.GG_STATUS_BLOCKED) {
			remoteStatus.setBlocked(true);
		} else if (remoteStatus != null) { // Zupełnie bezsensowny null był
			remoteStatus.setBlocked(false);
		}

		return remoteStatus;
	}

	public static int getProtocolStatus(final IStatus clientStatus, final boolean isFriendsOnly, final boolean isBlocked) {
		if (clientStatus == null) {
			throw new IllegalArgumentException("clientStatus cannot be null.");
		}

		int protocolStatus = -1;

		if (clientStatus.getStatusType() == StatusType.ONLINE) {
			protocolStatus = GGStatuses.GG_STATUS_AVAIL;
		} else if (clientStatus.getStatusType() == StatusType.ONLINE_WITH_DESCRIPTION) {
			protocolStatus = GGStatuses.GG_STATUS_AVAIL_DESCR;
		} else if (clientStatus.getStatusType() == StatusType.BUSY) {
			protocolStatus = GGStatuses.GG_STATUS_BUSY;
		} else if (clientStatus.getStatusType() == StatusType.BUSY_WITH_DESCRIPTION) {
			protocolStatus = GGStatuses.GG_STATUS_BUSY_DESCR;
		} else if (clientStatus.getStatusType() == StatusType.OFFLINE) {
			protocolStatus = GGStatuses.GG_STATUS_NOT_AVAIL;
		} else if (clientStatus.getStatusType() == StatusType.OFFLINE_WITH_DESCRIPTION) {
			protocolStatus = GGStatuses.GG_STATUS_NOT_AVAIL_DESCR;
		} else if (clientStatus.getStatusType() == StatusType.INVISIBLE) {
			protocolStatus = GGStatuses.GG_STATUS_INVISIBLE;
		} else if (clientStatus.getStatusType() == StatusType.INVISIBLE_WITH_DESCRIPTION) {
			protocolStatus = GGStatuses.GG_STATUS_INVISIBLE_DESCR;
		}

		if (protocolStatus != -1) {
			if (isFriendsOnly) {
				protocolStatus |= GGStatuses.GG_STATUS_FRIENDS_MASK;
			}
			if (isBlocked) {
				protocolStatus |= GGStatuses.GG_STATUS_BLOCKED;
			}
			return protocolStatus;
		}

		LOG.warn("Unable to convert status, falling back to unknown.");

		return GGStatuses.GG_STATUS_UNKNOWN;
	}

	public static MessageStatus getClientMessageStatus(final int protocolMsgStatus) {
		MessageStatus msgStatus;
		switch (protocolMsgStatus) {
			case GGSendMsgAck.GG_ACK_DELIVERED:
				msgStatus = MessageStatus.DELIVERED;
				break;
			case GGSendMsgAck.GG_ACK_NOT_DELIVERED:
				msgStatus = MessageStatus.NOT_DELIVERED;
				break;
			case GGSendMsgAck.GG_ACK_BLOCKED:
				msgStatus = MessageStatus.BLOCKED;
				break;
			case GGSendMsgAck.GG_ACK_MBOXFULL:
				msgStatus = MessageStatus.BLOCKED_MBOX_FULL;
				break;
			case GGSendMsgAck.GG_ACK_QUEUED:
				msgStatus = MessageStatus.QUEUED;
				break;
			default: {
				LOG.warn("Unable to convert message status, falling back to unknown.");
				msgStatus = MessageStatus.UNKNOWN;
			}
		}
		return msgStatus;
	}

	public static MessageClass getClientMessageClass(final int protocolMsgClass) {
		MessageClass msgClass;
		switch (protocolMsgClass) {
			case GGMessageClass.GG_CLASS_ACK:
				msgClass = MessageClass.DO_NOT_CONFIRM;
				break;
			case GGMessageClass.GG_CLASS_CHAT:
				msgClass = MessageClass.CHAT;
				break;
			case GGMessageClass.GG_CLASS_CTCP:
				msgClass = MessageClass.PING;
				break;
			case GGMessageClass.GG_CLASS_MSG:
				msgClass = MessageClass.MESSAGE;
				break;
			case GGMessageClass.GG_CLASS_QUEUED:
				msgClass = MessageClass.QUEUED;
				break;
			case GGMessageClass.GG_CLASS_QUEUED2:
				msgClass = MessageClass.QUEUED;
				break;
			default: {
				LOG.warn("Unable to convert message class, falling back to unknown.");
				msgClass = MessageClass.UNKNOWN;
			}
		}
		return msgClass;
	}

	public static int getProtocolMessageClass(final MessageClass clientMsgClass) {
		int result;
		switch (clientMsgClass) {
			case CHAT:
				result = GGMessageClass.GG_CLASS_CHAT;
				break;
			case DO_NOT_CONFIRM:
				result = GGMessageClass.GG_CLASS_ACK;
				break;
			case MESSAGE:
				result = GGMessageClass.GG_CLASS_MSG;
				break;
			case QUEUED:
				result = GGMessageClass.GG_CLASS_QUEUED;
				break;
			case PING:
				result = GGMessageClass.GG_CLASS_CTCP;
				break;
			default:
				LOG.warn("Unable to convert protocol message class, falling back to unknown.");
				result = GGMessageClass.GG_CLASS_UNKNOWN;
		}
		return result;
	}

}
