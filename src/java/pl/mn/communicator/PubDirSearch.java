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
 * Created on 2004-12-17
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: PubDirSearch.java,v 1.1 2004-12-18 00:08:43 winnetou25 Exp $
 */
public abstract class PubDirSearch {

	protected Integer m_uin = null;
	protected String m_firstName = null;
	protected String m_nickName = null;
	protected String m_birthYear = null;
	protected String m_city = null;
	protected String m_familyName = null;
	protected String m_familyCity = null;
	protected Integer m_start = null;

	public Integer getUin() {
		return m_uin;
	}
	
	public void setUin(Integer uin) {
		m_uin = uin;
	}
	
	public String getFirstName() {
		return m_firstName;
	}
	
	public void setFirstName(String firstName) {
		m_firstName = firstName;
	}
	
	public String getBirthYear() {
		return m_birthYear;
	}
	
	public void setBirthYear(String birthYear) {
		m_birthYear = birthYear;
	}
	
	public String getNickName() {
		return m_nickName;
	}
	
	public void setNickName(String nickName) {
		m_nickName = nickName;
	}
	
	public void setCity(String city) {
		m_city = city;
	}
	
	public String getCity() {
		return m_city;
	}
	
	public String getFamilyName() {
		return m_familyName;
	}
	
	public void setFamilyName(String familyName) {
		m_familyName = familyName;
	}
	
	public void setFamilyCity(String familyCity) {
		m_familyCity = familyCity;
	}
	
	public String getFamilyCity() {
		return m_familyCity;
	}

	public void setStart(Integer start) {
		m_start = start;
	}
	
	public Integer getStart() {
		return m_start;
	}

}
