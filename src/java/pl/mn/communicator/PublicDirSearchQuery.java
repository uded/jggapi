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
 * @version $Id: PublicDirSearchQuery.java,v 1.2 2004-12-18 14:23:36 winnetou25 Exp $
 */
public class PublicDirSearchQuery extends PubDirSearch {

	private String m_lastName = null;
	private Gender m_gender = null;
	private Boolean m_activeOnly = null;
	protected Integer m_start = null;

	public Gender getGender() {
		return m_gender;
	}
	
	public void setGender(Gender gender) {
		m_gender = gender;
	}
	
	public String getLastName() {
		return m_lastName;
	}
	
	public void setLastName(String lastName) {
		m_lastName = lastName;
	}
	
	public void setActiveOnly(Boolean only) {
		m_activeOnly = only;
	}
	
	public Boolean isActiveOnly() {
		return m_activeOnly;
	}

	public void setStart(Integer start) {
		m_start = start;
	}
	
	public Integer getStart() {
		return m_start;
	}

}
