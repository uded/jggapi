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
package pl.mn.communicator;

/**
 * This class represents personal user's information. It is stored remotely
 * in Gadu-Gadu public directory service.
 * 
 * Created on 2004-11-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: PersonalInfo.java,v 1.6 2004-12-23 17:52:24 winnetou25 Exp $
 */
public class PersonalInfo {

	/** First name of the user */
	private String m_firstName = null;
	
	/** Last name of the user */
	private String m_lastName = null;
	
	/** Birth date of the user */
	private String m_birthDate = null;

	/** City of the user */
	private String m_city = null;
	
	/** The handle that the user want to identify hisself/herself */
	private String m_nickName = null;
	
	/** The gender of the user */
	private Gender m_gender = null;
	
	/** The name of the user's family */
	private String m_familyName = null;
	
	/** the name of the user's city */
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
