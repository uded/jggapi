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
package pl.mn.communicator.packet.out;

import java.util.Random;

import pl.mn.communicator.PublicDirQuery;
import pl.mn.communicator.packet.GGPubdirEnabled;
import pl.mn.communicator.packet.PublicDirConstants;

/**
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGPubdirRequest.java,v 1.4 2004-12-16 22:22:50 winnetou25 Exp $
 */
public class GGPubdirRequest implements GGOutgoingPackage, GGPubdirEnabled {

	public static final int GG_PUBDIR50_REQUEST = 0x0014;
	
//#define GG_PUBDIR50_REQUEST 0x0014
//	
//struct gg_pubdir50 {
//	char type;
//	int seq;
//	char request[];
//};

	private final static Random SEQUENCER = new Random();
	
	private byte m_requestType = -1;
	private int m_seq = SEQUENCER.nextInt(99999);
	private String m_request = "";
	
	private GGPubdirRequest() {
		m_seq = SEQUENCER.nextInt(99999);
	}
	
	/**
     * @see pl.mn.communicator.packet.out.GGOutgoingPackage#getPacketType()
     */
    public int getPacketType() {
    	return GG_PUBDIR50_REQUEST;
    }

    /**
     * @see pl.mn.communicator.packet.out.GGOutgoingPackage#getLength()
     */
    public int getLength() {
    	return 5+m_request.getBytes().length;
    }

    /**
     * @see pl.mn.communicator.packet.out.GGOutgoingPackage#getContents()
     */
    public byte[] getContents() {
    	byte[] toSend = new byte[getLength()];
    	
    	toSend[0] = m_requestType;
    	toSend[1] = (byte) (m_seq & 0xFF);
       	toSend[2] = (byte) ((m_seq >> 8) & 0xFF);
       	toSend[3] = (byte) ((m_seq >> 16) & 0xFF);
       	toSend[4] = (byte) ((m_seq >> 24) & 0xFF);

       	byte[] requestBytes = m_request.getBytes();
       	for (int i=0; i<requestBytes.length; i++) {
       		toSend[5+i] = requestBytes[i];
       	}
       	
        return toSend;
    }
    
    public static GGPubdirRequest createSearchPubdirRequest(PublicDirQuery publicDirQuery) {
    	GGPubdirRequest pubdirRequest = new GGPubdirRequest();
    	pubdirRequest.m_requestType = GG_PUBDIR50_SEARCH;
    	StringBuffer buffer = new StringBuffer();
    	
    	if (publicDirQuery.getFirstname()!=null) {
    		buffer.append(PublicDirConstants.FIRST_NAME);
    		buffer.append('.');
    		buffer.append(publicDirQuery.getFirstname());
    	}
    	if (publicDirQuery.getSurname()!=null) {
    		buffer.append(PublicDirConstants.LAST_NAME);
    		buffer.append('.');
    		buffer.append(publicDirQuery.getSurname());
    	}
    	
    	pubdirRequest.m_request = buffer.toString();
    	return pubdirRequest;
    }
    
    public static GGPubdirRequest createReadPubdirRequest() {
    	GGPubdirRequest pubdirRequest = new GGPubdirRequest();
    	pubdirRequest.m_requestType = GG_PUBDIR50_READ;
    	pubdirRequest.m_request = "";
    	return pubdirRequest;
    }
    
    public static GGPubdirRequest createWritePubdirRequest() {
    	return null;
    }
    
}
