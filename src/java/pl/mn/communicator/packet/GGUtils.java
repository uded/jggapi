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
package pl.mn.communicator.packet;

import java.util.Date;

import pl.mn.communicator.IStatus;
import pl.mn.communicator.MessageClass;
import pl.mn.communicator.MessageStatus;
import pl.mn.communicator.Status;
import pl.mn.communicator.StatusType;
import pl.mn.communicator.User;
import pl.mn.communicator.packet.in.GGSendMsgAck;
import pl.mn.communicator.packet.out.GGNotify;

/**
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGUtils.java,v 1.3 2004-12-19 11:49:14 winnetou25 Exp $
 */
public class GGUtils {

	public static User.UserMode getUserMode(int protocolStatus) {
		if ((protocolStatus & GGStatusEnabled.GG_STATUS_FRIENDS_MASK) == GGStatusEnabled.GG_STATUS_FRIENDS_MASK) {
			return User.UserMode.FRIEND;
		}
		if ((protocolStatus & GGStatusEnabled.GG_STATUS_BLOCKED) == GGStatusEnabled.GG_STATUS_BLOCKED) {
			return User.UserMode.BLOCKED;
		}
		return User.UserMode.FRIEND;
	}
	
	public static byte getProtocolUserMode(User.UserMode userMode) {
		if (userMode == User.UserMode.BUDDY) return GGNotify.GG_USER_BUDDY;
		if (userMode == User.UserMode.BLOCKED) return GGNotify.GG_USER_BLOCKED;
		if (userMode == User.UserMode.FRIEND) return GGNotify.GG_USER_FRIEND;
		throw new RuntimeException("Unable to convert userMode: "+userMode);
	}
	
	public static Status getClientStatus(int status, String description, long returnTimeInMillis) {
		Status localStatus = null;
		
		switch (status) {
		case GGStatusEnabled.GG_STATUS_AVAIL: 
			localStatus = new Status(StatusType.ONLINE);
			break;
			
		case GGStatusEnabled.GG_STATUS_AVAIL_DESCR:
			localStatus = new Status(StatusType.ONLINE_WITH_DESCRIPTION);
		localStatus.setDescription(description);
			break;
			
		case GGStatusEnabled.GG_STATUS_BUSY:
			localStatus = new Status(StatusType.BUSY);
			break;
			
		case GGStatusEnabled.GG_STATUS_BUSY_DESCR:
			localStatus = new Status(StatusType.BUSY_WITH_DESCRIPTION);
			localStatus.setDescription(description);
			break;
			
		case GGStatusEnabled.GG_STATUS_INVISIBLE:
			localStatus = new Status(StatusType.INVISIBLE);
			break;

		case GGStatusEnabled.GG_STATUS_INVISIBLE_DESCR:
			localStatus = new Status(StatusType.INVISIBLE_WITH_DESCRIPTION);
		localStatus.setDescription(description);
			break;
		
		case GGStatusEnabled.GG_STATUS_NOT_AVAIL:
			localStatus = new Status(StatusType.OFFLINE);
			break;

		case GGStatusEnabled.GG_STATUS_NOT_AVAIL_DESCR:
			localStatus = new Status(StatusType.OFFLINE);
			localStatus.setDescription(description);
			break;
		}
		
		if (localStatus != null && returnTimeInMillis != -1) {
			localStatus.setReturnDate(new Date(returnTimeInMillis));
		}
		
		if (localStatus == null) throw new RuntimeException("Conversion error");
		return localStatus;
	}
	
	public static int getProtocolStatus(IStatus clientStatus, boolean isFriendsOnly, boolean isBlocked) {
		if (clientStatus == null) throw new NullPointerException("clientStatus cannot be null.");

		int protocolStatus = -1;
		
		if (clientStatus.getStatusType() == StatusType.ONLINE) {
			protocolStatus = GGStatusEnabled.GG_STATUS_AVAIL;
		} else if (clientStatus.getStatusType() == StatusType.ONLINE_WITH_DESCRIPTION) {
			protocolStatus = GGStatusEnabled.GG_STATUS_AVAIL_DESCR;
		} else if (clientStatus.getStatusType() == StatusType.BUSY) {
			protocolStatus = GGStatusEnabled.GG_STATUS_BUSY;
		} else if (clientStatus.getStatusType() == StatusType.BUSY_WITH_DESCRIPTION) {
			protocolStatus = GGStatusEnabled.GG_STATUS_BUSY_DESCR;
		} else if (clientStatus.getStatusType() == StatusType.OFFLINE) {
			protocolStatus = GGStatusEnabled.GG_STATUS_NOT_AVAIL;
		} else if (clientStatus.getStatusType() == StatusType.OFFLINE_WITH_DESCRIPTION) {
			protocolStatus = GGStatusEnabled.GG_STATUS_NOT_AVAIL_DESCR;
		} else if (clientStatus.getStatusType() == StatusType.INVISIBLE) {
			protocolStatus = GGStatusEnabled.GG_STATUS_INVISIBLE;
		} else if (clientStatus.getStatusType() == StatusType.INVISIBLE_WITH_DESCRIPTION) {
			protocolStatus = GGStatusEnabled.GG_STATUS_INVISIBLE_DESCR;
		}

		if (protocolStatus != -1) {
			if (isFriendsOnly) protocolStatus |= GGStatusEnabled.GG_STATUS_FRIENDS_MASK;
			if (isBlocked) 	protocolStatus |= GGStatusEnabled.GG_STATUS_BLOCKED;
			return protocolStatus;
		}
		throw new RuntimeException("Incorrect status: "+clientStatus);
	}
	
	public static MessageStatus getClientMessageStatus(int protocolMessageStatus) {
		switch (protocolMessageStatus) {
			case GGSendMsgAck.GG_ACK_DELIVERED: return MessageStatus.DELIVERED;
			case GGSendMsgAck.GG_ACK_NOT_DELIVERED: return MessageStatus.NOT_DELIVERED;
			case GGSendMsgAck.GG_ACK_BLOCKED: return MessageStatus.BLOCKED;
			case GGSendMsgAck.GG_ACK_MBOXFULL: return MessageStatus.BLOCKED_MBOX_FULL;
			case GGSendMsgAck.GG_ACK_QUEUED: return MessageStatus.QUEUED;
			default: throw new RuntimeException("Unable to convert protocol message status to client message status.");
		}
	}
	
	public static MessageClass getClientMessageClass(int protocolMessageClass) {
		switch (protocolMessageClass) {
			case GGMessageEnabled.GG_CLASS_ACK: return MessageClass.DO_NOT_CONFIRM;
			case GGMessageEnabled.GG_CLASS_CHAT: return MessageClass.CHAT;
			case GGMessageEnabled.GG_CLASS_CTCP: return MessageClass.PING;
			case GGMessageEnabled.GG_CLASS_MSG: return MessageClass.IN_NEW_WINDOW;
			case GGMessageEnabled.GG_CLASS_QUEUED: return MessageClass.QUEUED;
			default: throw new RuntimeException("Unable to convert, messageClass: "+protocolMessageClass);
		}
	}
	
	public static int getProtocolMessageClass(MessageClass clientMessageClass) {
		if (clientMessageClass == MessageClass.CHAT) return GGMessageEnabled.GG_CLASS_CHAT;
		if (clientMessageClass == MessageClass.DO_NOT_CONFIRM) return GGMessageEnabled.GG_CLASS_ACK;
		if (clientMessageClass == MessageClass.IN_NEW_WINDOW) return GGMessageEnabled.GG_CLASS_MSG;
		if (clientMessageClass == MessageClass.QUEUED) return GGMessageEnabled.GG_CLASS_QUEUED;
		if (clientMessageClass == MessageClass.PING) return GGMessageEnabled.GG_CLASS_CTCP;
		throw new RuntimeException("Unable to convert, messageClass: "+clientMessageClass);
	}

	/**
	 * Konwertuj bajty do stringa w postaci czytelnej.
	 * @param bytes bajty do konwersji
	 * @return String
	 */
	public static String bytesToString(byte[] bytes) {
	    StringBuffer odebrane = new StringBuffer();
	    odebrane.append("{");
	
	    for (int i = 0; i < bytes.length; i++) {
	        odebrane.append("'" + bytes[i] + "',");
	    }
	
	    odebrane.append("}");
	
	    return odebrane.toString();
	}

	/**
	 * Zamien tabice bajt�w na integer.
	 * @param buf tablica bajt�w
	 * @return int
	 */
	public static int byteToInt(byte[] buf) {
	    return byteToInt(buf, 0);
	}

	/**
	 * Zamien tablice bajt�w na integer zaczynaj�c od pozycji start.
	 * @param buf tablica bajt�w
	 * @param start pozycja od kt�rej tablica jest czytana
	 * @return int
	 */
	public static int byteToInt(byte[] buf, int start) {
	    int i = 0;
	    int pos = start;
	
	    int tmp;
	    int plus = 0;
	    tmp = unsignedByteToInt(buf[pos++]) << 0;
	    i += tmp;
	    tmp = unsignedByteToInt(buf[pos++]) << 8;
	    i += tmp;
	    tmp = unsignedByteToInt(buf[pos++]) << 16;
	    i += tmp;
	    tmp = unsignedByteToInt(buf[pos++]) << 24;
	    i += tmp;
	
	    return i;
	}
	
	public static short byteToShort(byte[] buf, int start) {
	    int i = 0;
	    int pos = start;
	
	    int tmp;
	    int plus = 0;
	    tmp = unsignedByteToInt(buf[pos++]) << 0;
	    i += tmp;
	    tmp = unsignedByteToInt(buf[pos++]) << 8;
	    i += tmp;
	
	    return (short) i;
	}
	
	public static long secondsToMillis(int seconds) {
		return seconds*1000L;
	}
	
	public static int millisToSeconds(long millis) {
		return (int) (millis/1000L);
	}

	/**
	 *
	 * @param i
	 * @return
	 */
	public static int unsignedByteToInt(byte i) {
	    if (i < 0) {
	        return (i & 0x7F) + 0x80;
	    } else {
	        return i;
	    }
	}

	public static byte[] intToByte(int buf) {
	    byte[] toSend = new byte[4];
	
	    toSend[3] = (byte) ((buf >> 24) & 0xFF);
	    toSend[2] = (byte) ((buf >> 16) & 0xFF);
	    toSend[1] = (byte) ((buf >> 8) & 0xFF);
	    toSend[0] = (byte) (buf & 0xFF);
	
	    return toSend;
	}

	public static long unsignedIntToLong(int i) {
	    long plus = 0;
	    plus -= (i & 0x80000000);
	    i &= 0x7FFFFFFF;
	
	    return (long) (i + plus);
	}

	public static String byteToString(byte[] data, int startIndex) {
	    int counter = 0;
	
	    while (((counter + startIndex) < data.length) &&
	            (data[counter + startIndex] != 0)) {
	        counter++;
	    }
	
	    byte[] desc = new byte[counter];
	    System.arraycopy(data, startIndex, desc, 0, counter);
	
	    return new String(desc);
	}
	
    public static int getLoginHash(char[] password, int seed) {
        long x;
        long y;
        long z;

        y = seed;

        int i;

        for (x = 0, i = 0; i < password.length; i++) {
            x = (x & 0xffffff00) | password[i];
            y ^= x;

            int k = (int) y;
            k += x;
            y = GGUtils.unsignedIntToLong(k);

            k = (int) x;
            k <<= 8;
            x = GGUtils.unsignedIntToLong(k);

            y ^= x;

            k = (int) x;
            k <<= 8;
            x = GGUtils.unsignedIntToLong(k);

            k = (int) y;
            k -= x;
            y = GGUtils.unsignedIntToLong(k);

            k = (int) x;
            k <<= 8;
            x = GGUtils.unsignedIntToLong(k);

            y ^= x;

            z = y & 0x1f;
            y = GGUtils.unsignedIntToLong((int) ((y << z)
                    | (y >> (32 - z))));
        }

        return (int) y;
    }
	
}
