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
package pl.mn.communicator.packet.out;

import java.util.Collection;
import java.util.Iterator;

import pl.mn.communicator.LocalUser;

/**
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGUserListRequest.java,v 1.7 2004-12-23 17:52:24 winnetou25 Exp $
 */
public class GGUserListRequest implements GGOutgoingPackage {
 
	public static final int GG_USERLIST_REQUEST  = 0x0016;
	
	private static final byte GG_USER_LIST_PUT = 0x00;
	private static final byte GG_USERLIST_PUT_MORE = 0x01;
	private static final int GG_USERLIST_GET = 0x02;

	private Collection m_usersToExport = null;
	
	private byte m_type;
	
	private String m_request;
	
	private GGUserListRequest() {
	}

	private String prepareRequest() {
	   	StringBuffer buffer = new StringBuffer();
    	for (Iterator it = m_usersToExport.iterator(); it.hasNext();) {
    		LocalUser localUser = (LocalUser) it.next();
	    	if (localUser.getFirstName() != null) buffer.append(localUser.getFirstName());
	    	buffer.append(';');
	    	if (localUser.getLastName() != null) buffer.append(localUser.getLastName());
	    	buffer.append(';');
	    	if (localUser.getNickName() != null) buffer.append(localUser.getNickName());
	    	buffer.append(';');
	    	if (localUser.getDisplayName() != null) buffer.append(localUser.getDisplayName());
	    	buffer.append(';');
	    	if (localUser.getTelephone() != null) buffer.append(localUser.getTelephone());
	    	buffer.append(';');
	    	if (localUser.getGroup() != null) buffer.append(localUser.getGroup());
	    	buffer.append(';');
	    	if (localUser.getUin() != -1) buffer.append(localUser.getUin());
	    	buffer.append(';');
	    	if (localUser.getEmailAddress() != null) buffer.append(localUser.getEmailAddress());
	    	buffer.append(';');
	    	buffer.append(0);
	    	buffer.append(';');
	    	buffer.append(';');
	    	buffer.append(0);
	    	buffer.append(';');
    	}
    	return buffer.toString();
	}	

	/**
     * @see pl.mn.communicator.packet.out.GGOutgoingPackage#getPacketType()
     */
    public int getPacketType() {
    	return GG_USERLIST_REQUEST;
    }

    /**
     * @see pl.mn.communicator.packet.out.GGOutgoingPackage#getLength()
     */
    public int getLength() {
    	return 1+m_request.length();
    }

    /**
     * @see pl.mn.communicator.packet.out.GGOutgoingPackage#getContents()
     */
    public byte[] getContents() {
    	byte[] toSend = new byte[getLength()];
    	
    	toSend[0] = m_type;

    	byte[] bytes = m_request.getBytes();
    	for (int i=0; i<bytes.length; i++) {
    		toSend[1+i] = bytes[i];
    	}
    	
    	return toSend;
    }
    
    public static GGUserListRequest createClearUsetListRequest() {
    	GGUserListRequest listRequest = new GGUserListRequest();
    	listRequest.m_request = "";
    	listRequest.m_type = GG_USER_LIST_PUT;
    	return listRequest;
    }

    public static GGUserListRequest createPutUserListRequest(Collection users) {
    	if (users == null) throw new NullPointerException("users collection cannot be null");
    	GGUserListRequest listRequest = new GGUserListRequest();
    	listRequest.m_type = GG_USER_LIST_PUT;
    	listRequest.m_usersToExport = users;
    	listRequest.m_request = listRequest.prepareRequest();
    	return listRequest;
    }
    
    public static GGUserListRequest createGetUserListRequest() {
    	GGUserListRequest listRequest = new GGUserListRequest();
    	listRequest.m_type = GG_USERLIST_GET;
    	listRequest.m_request = "";
    	return listRequest;
    }

}
