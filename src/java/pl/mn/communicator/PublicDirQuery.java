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
 * Created on 2004-11-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: PublicDirQuery.java,v 1.1 2004-12-14 22:52:11 winnetou25 Exp $
 */
public class PublicDirQuery {

	private String m_uin = null;
	private String m_firstName = null;
	private String m_surName = null;
	private String m_nickName = null;
	private String m_birthYear = null;
	private Integer m_gener = null;  //1-female, 0-male
	private Boolean m_activeOnly = null;
	private String m_familyName = null;
	private String m_familyCity = null;

	public String getFirstname() {
		return m_firstName;
	}
	
	public void setFirstname(String firstName) {
		m_firstName = firstName;
	}
	
	public String getSurname() {
		return m_surName;
	}
	
	public void setSurname(String surName) {
		m_surName = surName;
	}
	
	/**
	 * @param only The m_activeOnly to set.
	 */
	public void setActiveOnly(Boolean only) {
		m_activeOnly = only;
	}
	
	/**
	 * @return Returns the m_activeOnly.
	 */
	public Boolean isActiveOnly() {
		return m_activeOnly;
	}
	
	public final static class Gender {
		
		private String m_gender;
		
		private Gender(String gender) {
			m_gender = gender;
		}
		
		public final static Gender FEMALE = new Gender("female");
		public final static Gender MALE = new Gender("male");
		
	}

}
