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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.StringTokenizer;

import pl.mn.communicator.Gender;
import pl.mn.communicator.PublicDirInfo;
import pl.mn.communicator.packet.GGPubdirEnabled;
import pl.mn.communicator.packet.GGUtils;
import pl.mn.communicator.packet.PublicDirConstants;

/**
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGPubdirReply.java,v 1.5 2004-12-17 20:23:02 winnetou25 Exp $
 */
public class GGPubdirReply implements GGIncomingPackage, GGPubdirEnabled {
	
	public static final int GG_PUBDIR50_REPLY = 0x000E;

	private byte m_replyType = -1;
	private int m_sequence = -1;
	
	private Collection m_pubDirReplies = new ArrayList();
	
	public GGPubdirReply(byte[] data) {
		m_replyType = data[0];
		m_sequence = GGUtils.byteToInt(data, 1);
		if (isPubdirReadReply()) {
			handlePubdirReadReply(data);
		}
	}
	
	public Collection getPubdirReadReply() {
		return Collections.unmodifiableCollection(m_pubDirReplies);
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
		return m_replyType == GG_PUBDIR50_SEARCH;
	}
	
	public boolean isPubdirWriteReply() {
		return m_replyType == GG_PUBDIR50_WRITE;
	}
	
	private String byteToString(byte[] data, int startIndex) {
	    int counter = 0;
	
	    while (((counter + startIndex) < data.length)) {
	        counter++;
	    }
	
	    byte[] desc = new byte[counter];
	    System.arraycopy(data, startIndex, desc, 0, counter);
	
	    return new String(desc);
	}
	
	private void handlePubdirReadReply(byte[] data) {
		String string = byteToString(data, 5);
		StringTokenizer tokenizer = new StringTokenizer(string, "\0");
		PublicDirInfo pubDirReply = new PublicDirInfo();
		while (tokenizer.hasMoreTokens()) {
			String token = (String) tokenizer.nextToken();
			if (token.equals(PublicDirConstants.FIRST_NAME)) {
				String firstName = tokenizer.nextToken();
				pubDirReply.setFirstName(firstName);
			} else if (token.equals(PublicDirConstants.LAST_NAME)) {
				String lastName = tokenizer.nextToken();
				pubDirReply.setLastName(lastName);
			} else if (token.equals(PublicDirConstants.BIRTH_YEAR)) {
				String birthDate = tokenizer.nextToken();
				pubDirReply.setBirthDate(birthDate);
			} else if (token.equals(PublicDirConstants.CITY)) {
				String city = tokenizer.nextToken();
				pubDirReply.setCity(city);
			} else if (token.equals(PublicDirConstants.NICK_NAME)) {
				String nickName = tokenizer.nextToken();
				pubDirReply.setNickName(nickName);
			} else if (token.equals(PublicDirConstants.GENDER)) {
				String genderString = tokenizer.nextToken();
				Gender gender = null;
				if (genderString.equals("2")) {
					gender = Gender.FEMALE;
				} else if (genderString.equals("1")){
					gender = Gender.MALE;
				}
				pubDirReply.setGender(gender);
			} else if (token.equals(PublicDirConstants.FAMILY_NAME.toString())) {
				String familyName = tokenizer.nextToken();
				pubDirReply.setFamilyName(familyName);
			} else if (token.equals(PublicDirConstants.FAMILY_CITY.toString())) {
				String familyCity = tokenizer.nextToken();
				pubDirReply.setFamilyCity(familyCity);
			}
		}
		m_pubDirReplies.add(pubDirReply);
	}
	
//	#define GG_PUBDIR50_REPLY 0x000e
	
//struct gg_pubdir50_reply {
//	char type;
//	int seq;
//	char reply[];
//};
	
}
