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
package pl.mn.communicator;

/**
 * This class represents personal user's information. It is stored remotely
 * in Gadu-Gadu public directory service.
 * 
 * Created on 2004-11-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: PersonalInfo.java,v 1.3 2004-12-19 18:50:44 winnetou25 Exp $
 */
public class PersonalInfo {

	private String m_firstName = null;
	private String m_lastName = null;
	private String m_birthDate = null;
	private String m_city = null;
	private String m_nickName = null;
	private Gender m_gender = null;
	private String m_familyName = null;
	private String m_familyCity = null;

	public PersonalInfo() {
		
	}
	
	public String getFirstName() {
		return m_firstName;
	}
	
	public void setFirstName(String name) {
		m_firstName = name;
	}
	
	public String getLastName() {
		return m_lastName;
	}
	
	public void setLastName(String name) {
		m_lastName = name;
	}
	
	public String getBirthDate() {
		return m_birthDate;
	}
	
	public void setBirthDate(String date) {
		m_birthDate = date;
	}
	
	public String getCity() {
		return m_city;
	}
	
	public void setCity(String city) {
		m_city = city;
	}
	
	public Gender getGender() {
		return m_gender;
	}
	
	public void setGender(Gender gender) {
		m_gender = gender;
	}
	
	public String getNickName() {
		return m_nickName;
	}
	
	public void setNickName(String nickName) {
		m_nickName = nickName;
	}
	
	public String getFamilyCity() {
		return m_familyCity;
	}

	public void setFamilyCity(String familyCity) {
		m_familyCity = familyCity;
	}
	
	public String getFamilyName() {
		return m_familyName;
	}

	public void setFamilyName(String familyName) {
		m_familyName = familyName;
	}
	
}
