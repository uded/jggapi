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
package pl.mn.communicator.packet.in;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import pl.mn.communicator.LocalUser;

/**
 * Created on 2004-12-11
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGUserListReply.java,v 1.6 2004-12-19 21:19:59 winnetou25 Exp $
 */
public class GGUserListReply implements GGIncomingPackage {

	public final static int GG_USERLIST_REPLY  = 0x0010;
	
	private final static int GG_USERLIST_PUT_REPLY  = 0x00;        /* początek eksportu listy */
	private final static int GG_USERLIST_PUT_MORE_REPLY  = 0x02;    /* kontynuacja */

	private final static int GG_USERLIST_GET_MORE_REPLY  = 0x04;    /* początek importu listy */
	private final static int GG_USERLIST_GET_REPLY = 0x06; 			/* ostatnia część importu */ 
	
	private byte m_type = -1;
	
	private Collection m_users = null;
	
	public GGUserListReply(byte[] data) {
		m_type = (byte) data[0];
		if (isGetMoreReply() || isGetReply()) {
			m_users = createUsersCollection(data);
		}
	}
	
	/**
	 * @see pl.mn.communicator.packet.in.GGIncomingPackage#getPacketType()
	 */
	public int getPacketType() {
		return GG_USERLIST_PUT_REPLY;
	}
	
	private Collection createUsersCollection(byte[] data) {
		ArrayList list = new ArrayList();
		String contactListString = new String(data, 1, data.length-1);

		String[] contactListStrings = contactListString.split(";");
		List contactList = Arrays.asList(contactListStrings);
		
		int count = contactListStrings.length/11;
		
		int index = 0;
		for (int i=0; i<count; i++) {
			List subList = contactList.subList(index, index+11);
			index+=11;
			LocalUser localUser = createLocalUser(subList);
			list.add(localUser);
		}
		
		return list;
	}
	
//	imie;nazwisko;pseudo;wyswietlane;telefon;grupa;uin;adres@email;0;;0;
	private LocalUser createLocalUser(List entries) {
		String firstName = (String) entries.get(0);
		String surName = (String) entries.get(1);
		String nickname = (String) entries.get(2);
		String displayName = (String) entries.get(3);
		String telephone = (String) entries.get(4);
		String group = (String) entries.get(5);
		String uin = (String) entries.get(6);
		String email = (String) entries.get(7);

		LocalUser localUser = new LocalUser();
		localUser.setName(firstName);
		localUser.setLastName(surName);
		localUser.setNickName(nickname);
		localUser.setDisplayName(displayName);
		localUser.setTelephone(telephone);
		localUser.setGroup(group);
		localUser.setUin(Integer.valueOf(uin).intValue());
		localUser.setEmailAddress(email);
		
		return localUser;
	}
	
	public Collection getContactList() {
		return m_users;
	}
	
	public boolean isPutReply() {
		return m_type == GG_USERLIST_PUT_REPLY;
	}
	
	public boolean isPutMoreReply() {
		return m_type == GG_USERLIST_PUT_MORE_REPLY;
	}
	
	public boolean isGetReply() {
		return m_type == GG_USERLIST_GET_REPLY;
	}
	
	public boolean isGetMoreReply() {
		return m_type == GG_USERLIST_GET_MORE_REPLY;
	}
	
}
