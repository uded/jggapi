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
package pl.mn.communicator;

/**
 * Created on 2004-11-11
 * 
 * @author mateusz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LocalUser {

	//imie;nazwisko;pseudo;wyswietlane;telefon;grupa;uin;adres@email;0;;0;
	private String m_name;
	private String m_surname;
	private String m_nickName;
	private String m_displayName;
	private String m_telephone;
	private String m_group;
	private String m_uin;
	private String m_emailAddress;
	private int m_mode = -1;
	
	public void setName(String name) {
		if (name == null) throw new NullPointerException("name cannot be null");
		m_name = name;
	}
	
	public String getName() {
		return m_name;
	}
	
	public void setSurname(String surname) {
		if (surname == null) throw new NullPointerException("surname cannot be null");
		m_surname = surname;
	}
	
	public String getSurname() {
		return m_surname;
	}

	public void setNickName(String nickName) {
		if (nickName == null) throw new NullPointerException("nickName cannot be null");
		m_nickName = nickName;
	}

	public String getNickName() {
		return m_nickName;
	}
	
	public void setDisplayName(String displayName) {
		if (displayName == null) throw new NullPointerException("displayName cannot be null");
		m_displayName = displayName;
	}

	public String getDisplayName() {
		return m_displayName;
	}

	public void setTelephone(String telephone) {
		if (telephone == null) throw new NullPointerException("telephone cannot be null");
		m_telephone = telephone;
	}
	
	public String getTelephone() {
		return m_telephone;
	}

	public void setGroup(String group) {
		if (group == null) throw new NullPointerException("group cannot be null");
		m_group = group;
	}
	
	public void setUin(String uin) {
		if (uin == null) throw new NullPointerException("uin cannot be null");
		m_uin = uin;
	}

	public String getUin() {
		return m_uin;
	}
	
	public void setEmailAddress(String emailAddress) {
		if (emailAddress == null) throw new NullPointerException("Email address cannot be null");
		m_emailAddress = emailAddress;
	}
	
	public String getEmailAddress() {
		return m_emailAddress;
	}
	
	public void setUserMode(int userMode) {
		m_mode = userMode;
	}
	
	public int getUserMode() {
		return m_mode;
	}

	public static interface UserMode {
		int USER_NORMAL = 1;
		int USER_BLOCKED = 2;
		int USER_OFFLINE = 3;
	}
	
}
