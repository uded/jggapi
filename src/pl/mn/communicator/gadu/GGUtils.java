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
package pl.mn.communicator.gadu;

import java.util.Calendar;
import java.util.Date;

import pl.mn.communicator.IStatus;
import pl.mn.communicator.MessageClass;
import pl.mn.communicator.MessageStatus;
import pl.mn.communicator.Status;
import pl.mn.communicator.StatusConst;

/**
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGUtils.java,v 1.1 2004-12-11 16:25:58 winnetou25 Exp $
 */
public class GGUtils {

	//TODO return time
	public static IStatus getClientStatus(int status, String description, Date returnTime) {
		IStatus statusBiz = null;
		
		switch (status) {
		case GGStatusEnabled.GG_STATUS_AVAIL: 
			statusBiz = new Status(StatusConst.ONLINE);
			break;
			
		case GGStatusEnabled.GG_STATUS_AVAIL_DESCR:
			statusBiz = new Status(StatusConst.ONLINE_WITH_DESCRIPTION);
			statusBiz.setDescription(description);
			break;
			
		case GGStatusEnabled.GG_STATUS_BUSY:
			statusBiz = new Status(StatusConst.BUSY);
			break;
			
		case GGStatusEnabled.GG_STATUS_BUSY_DESCR:
			statusBiz = new Status(StatusConst.BUSY_WITH_DESCRIPTION);
			statusBiz.setDescription(description);
			break;
			
		case GGStatusEnabled.GG_STATUS_INVISIBLE:
			statusBiz = new Status(StatusConst.INVISIBLE);
			break;

		case GGStatusEnabled.GG_STATUS_INVISIBLE_DESCR:
			statusBiz = new Status(StatusConst.INVISIBLE_WITH_DESCRIPTION);
			statusBiz.setDescription(description);
			break;
		
		case GGStatusEnabled.GG_STATUS_NOT_AVAIL:
			statusBiz = new Status(StatusConst.OFFLINE);
			break;

		case GGStatusEnabled.GG_STATUS_NOT_AVAIL_DESCR:
			statusBiz = new Status(StatusConst.OFFLINE);
			statusBiz.setDescription(description);
			break;
		}
		
		if (statusBiz != null && returnTime != null) {
			statusBiz.setReturnDate(returnTime);
		}
		
		if (statusBiz == null) throw new RuntimeException("Conversion error");
		return statusBiz;
	}
	
	public static int getProtocolStatus(IStatus clientStatus, boolean isFriendsOnly, boolean isBlocked) {
		if (clientStatus == null) throw new NullPointerException("clientStatus cannot be null.");

		int protocolStatus = -1;
		
		if (clientStatus.getStatus() == StatusConst.ONLINE) {
			protocolStatus = GGStatusEnabled.GG_STATUS_AVAIL;
		} else if (clientStatus.getStatus() == StatusConst.ONLINE_WITH_DESCRIPTION) {
			protocolStatus = GGStatusEnabled.GG_STATUS_AVAIL_DESCR;
		} else if (clientStatus.getStatus() == StatusConst.BUSY) {
			protocolStatus = GGStatusEnabled.GG_STATUS_BUSY;
		} else if (clientStatus.getStatus() == StatusConst.BUSY_WITH_DESCRIPTION) {
			protocolStatus = GGStatusEnabled.GG_STATUS_BUSY_DESCR;
		} else if (clientStatus.getStatus() == StatusConst.OFFLINE) {
			protocolStatus = GGStatusEnabled.GG_STATUS_NOT_AVAIL;
		} else if (clientStatus.getStatus() == StatusConst.OFFLINE_WITH_DESCRIPTION) {
			protocolStatus = GGStatusEnabled.GG_STATUS_NOT_AVAIL_DESCR;
		} else if (clientStatus.getStatus() == StatusConst.INVISIBLE) {
			protocolStatus = GGStatusEnabled.GG_STATUS_INVISIBLE;
		} else if (clientStatus.getStatus() == StatusConst.INVISIBLE_WITH_DESCRIPTION) {
			protocolStatus = GGStatusEnabled.GG_STATUS_INVISIBLE_DESCR;
		}

		if (protocolStatus != -1) {
			if (isFriendsOnly) {
				protocolStatus = protocolStatus | GGStatusEnabled.GG_STATUS_FRIENDS_MASK;
			}
			if (isBlocked) protocolStatus = protocolStatus | GGStatusEnabled.GG_STATUS_BLOCKED;
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
			case GGMessage.GG_CLASS_ACK: return MessageClass.DO_NOT_CONFIRM;
			case GGMessage.GG_CLASS_CHAT: return MessageClass.CHAT;
			case GGMessage.GG_CLASS_CTCP: return MessageClass.PING;
			case GGMessage.GG_CLASS_MSG: return MessageClass.IN_NEW_WINDOW;
			case GGMessage.GG_CLASS_QUEUED: return MessageClass.QUEUED;
			default: throw new RuntimeException("Unable to convert, messageClass: "+protocolMessageClass);
		}
	}
	
	public static int getProtocolMessageClass(MessageClass clientMessageClass) {
		if (clientMessageClass == MessageClass.CHAT) return GGMessage.GG_CLASS_CHAT;
		if (clientMessageClass == MessageClass.DO_NOT_CONFIRM) return GGMessage.GG_CLASS_ACK;
		if (clientMessageClass == MessageClass.IN_NEW_WINDOW) return GGMessage.GG_CLASS_MSG;
		if (clientMessageClass == MessageClass.QUEUED) return GGMessage.GG_CLASS_QUEUED;
		if (clientMessageClass == MessageClass.PING) return GGMessage.GG_CLASS_CTCP;
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
	
	public static void main(String args[]) {
//		long now = System.currentTimeMillis();
//		int nowSeconds = GGUtils.millisToSeconds(now);
//		System.out.println("NowMillis: "+now);
//		System.out.println("NowSeconds: "+nowSeconds);
//		System.out.println("Date: "+new Date(nowSeconds));
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2004);
		cal.set(Calendar.MONTH, 06);
		cal.set(Calendar.DATE, 03);
		System.out.println(cal);
		System.out.println(cal.getTime());
	}
	
//	1102724043
//
//	acze (1:12)
//	a to jest:
//	acze (1:12)
//	Sat Dec 11 01:14:09 CET 2004
//
//	acze (1:13)
//	13 stycznia godzina 19:18:43, czyli tak jak interpretuje, to by bylo:
//	acze (1:14)
//	13*(3600*24)+18*60+19*3600+43	
	
}
