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
package pl.mn.communicator.packet.in;

import pl.mn.communicator.packet.GGPubdirEnabled;
import pl.mn.communicator.packet.GGUtils;

/**
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGPubdirReply.java,v 1.2 2004-12-15 22:02:57 winnetou25 Exp $
 */
public class GGPubdirReply implements GGIncomingPackage, GGPubdirEnabled {
	
	public static final int GG_PUBDIR50_REPLY = 0x000E;

	private byte m_replyType = -1;
	private int m_sequence = -1;
	
	public GGPubdirReply(byte[] data) {
		m_replyType = data[0];
		m_sequence = GGUtils.byteToInt(data, 1);
	}
	
	/**
	 * @see pl.mn.communicator.packet.in.GGIncomingPackage#getPacketType()
	 */
	public int getPacketType() {
		return GG_PUBDIR50_REPLY;
	}
	
	public boolean isPubdirSearchReply() {
		return m_replyType == GG_PUBDIR50_SEARCH_REPLY;
	}
	
	public boolean isPubdirReadReply() {
		return m_replyType == GG_PUBDIR50_READ;
	}
	
	public boolean isPubdirWriteReply() {
		return m_replyType == GG_PUBDIR50_WRITE;
	}
	
	//TODO implement
//	#define GG_PUBDIR50_REPLY 0x000e
	
//struct gg_pubdir50_reply {
//	char type;
//	int seq;
//	char reply[];
//};
	
}
