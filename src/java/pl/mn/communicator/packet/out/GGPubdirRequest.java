/*
 * Copyright (c) 2003-2005 <a href="http://jggapi.sourceforge.net/team-list.html">JGGApi Development Team</a> All Rights Reserved.
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

import pl.mn.communicator.Gender;
import pl.mn.communicator.PersonalInfo;
import pl.mn.communicator.PublicDirSearchQuery;
import pl.mn.communicator.packet.GGPubdirEnabled;

/**
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGPubdirRequest.java,v 1.10 2004-12-19 18:50:51 winnetou25 Exp $
 */
public class GGPubdirRequest implements GGOutgoingPackage, GGPubdirEnabled {

	public static final int GG_PUBDIR50_REQUEST = 0x0014;
	
	private final static Random SEQUENCER = new Random();
	
	private byte m_requestType = -1;
	private int m_seq = -1;
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
    
    public static GGPubdirRequest createSearchPubdirRequest(PublicDirSearchQuery publicDirQuery) {
    	GGPubdirRequest pubdirRequest = new GGPubdirRequest();
    	pubdirRequest.m_requestType = GG_PUBDIR50_SEARCH;
    	
    	StringBuffer buffer = new StringBuffer();
    	if (publicDirQuery.getUin() != null) {
    		Integer uin = publicDirQuery.getUin();
    		String uinEntry = getEntry(UIN, String.valueOf(uin.intValue()));
    		buffer.append(uinEntry);
    	}
    	if (publicDirQuery.getFirstName() != null) {
    		String firstNameEntry = getEntry(FIRST_NAME, publicDirQuery.getFirstName());
    		buffer.append(firstNameEntry);
    	}
    	if (publicDirQuery.getLastName() != null) {
    		String lastNameEntry = getEntry(LAST_NAME, publicDirQuery.getLastName());
    		buffer.append(lastNameEntry);
    	}
    	if (publicDirQuery.getCity() != null) {
    		String cityEntry = getEntry(CITY, publicDirQuery.getCity());
    		buffer.append(cityEntry);
    	}
    	if (publicDirQuery.getNickName() != null) {
    		String nickNameEntry = getEntry(NICK_NAME, publicDirQuery.getNickName());
    		buffer.append(nickNameEntry);
    	}
    	if (publicDirQuery.getBirthYear() != null) {
    		String birthEntry = getEntry(BIRTH_YEAR, publicDirQuery.getBirthYear());
    		buffer.append(birthEntry);
    	}
    	if (publicDirQuery.getGender() != null) {
    		Gender gender = publicDirQuery.getGender();
    		String genderEntry = getEntry(GENDER, gender == Gender.MALE ? "2" : "1");
    		buffer.append(genderEntry);
    	}
    	if (publicDirQuery.getFamilyName() != null) {
    		String familyNameEntry = getEntry(FAMILY_NAME, publicDirQuery.getFamilyName());
    		buffer.append(familyNameEntry);
    	}
    	if (publicDirQuery.getFamilyCity() != null) {
    		String familyCityEntry = getEntry(FAMILY_CITY, publicDirQuery.getFamilyCity());
    		buffer.append(familyCityEntry);
    	}
    	if (publicDirQuery.getStart() != null) {
    		Integer startInteger = publicDirQuery.getStart(); 
    		String startEntry = getEntry(START, String.valueOf(startInteger.intValue()));
    		buffer.append(startEntry);
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
    
    public static GGPubdirRequest createWritePubdirRequest(PersonalInfo publicDirInfo) {
    	if (publicDirInfo == null) throw new NullPointerException("publicDirInfo cannot be null");
    	GGPubdirRequest pubdirRequest = new GGPubdirRequest();
    	pubdirRequest.m_requestType = GG_PUBDIR50_WRITE;
    	pubdirRequest.m_request = prepareWriteRequest(publicDirInfo);
    	return pubdirRequest;
    }
    
    private static String prepareWriteRequest(PersonalInfo publicDirInfo) {
    	StringBuffer buffer = new StringBuffer();
    	if (publicDirInfo.getFirstName() != null) {
    		String firstNameEntry = getEntry(FIRST_NAME, publicDirInfo.getFirstName());
    		buffer.append(firstNameEntry);
    	}
    	if (publicDirInfo.getLastName() != null) {
    		String lastNameEntry = getEntry(LAST_NAME, publicDirInfo.getLastName());
    		buffer.append(lastNameEntry);
    	}
    	if (publicDirInfo.getCity() != null) {
    		String cityEntry = getEntry(CITY, publicDirInfo.getCity());
    		buffer.append(cityEntry);
    	}
    	if (publicDirInfo.getNickName() != null) {
    		String nickNameEntry = getEntry(NICK_NAME, publicDirInfo.getNickName());
    		buffer.append(nickNameEntry);
    	}
    	if (publicDirInfo.getGender() != null) {
    		Gender gender = publicDirInfo.getGender();
    		String genderEntry = getEntry(GENDER, gender == Gender.MALE ? "1" : "2");
    		buffer.append(genderEntry);
    	}
    	if (publicDirInfo.getFamilyName() != null) {
    		String familyNameEntry = getEntry(FAMILY_NAME, publicDirInfo.getFamilyName());
    		buffer.append(familyNameEntry);
    	}
    	if (publicDirInfo.getFamilyCity() != null) {
    		String familyCityEntry = getEntry(FAMILY_CITY, publicDirInfo.getFamilyCity());
    		buffer.append(familyCityEntry);
    	}
    	if (publicDirInfo.getBirthDate() != null) {
    		String birthDateEntry = getEntry(BIRTH_YEAR, publicDirInfo.getBirthDate());
    		buffer.append(birthDateEntry);
    	}
    	return buffer.toString();
    }
    
    private static String getEntry(String key, String value) {
    	StringBuffer buffer = new StringBuffer();
    	buffer.append(key);
    	buffer.append('\0');
    	buffer.append(value);
    	buffer.append('\0');
    	return buffer.toString();
    }
    
}
