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
 * Created on 2004-11-11
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: LocalUser.java,v 1.8 2004-12-21 21:23:34 winnetou25 Exp $
 */
public class LocalUser {

//	private IStatus m_status = new Status(StatusType.OFFLINE);
	
	private int m_uin = -1;
	private String m_name = null;
	private String m_lastName = null;
	private String m_nickName = null;
	private String m_displayName = null;
	private String m_telephone = null;
	private String m_group = null;
	private String m_emailAddress = null;
	private int m_mode = -1;

	public LocalUser() {
		
	}
	
//	public void setStatus(IStatus status) {
//		if (status == null) throw new NullPointerException("status cannot be null");
//		m_status = status;
//	}
//	
//	public IStatus getStatus() {
//		return m_status;
//	}
	
	public void setName(String name) {
		if (name == null) throw new NullPointerException("name cannot be null");
		m_name = name;
	}
	
	public String getName() {
		return m_name;
	}
	
	public void setLastName(String surname) {
		m_lastName = surname;
	}
	
	public String getLastName() {
		return m_lastName;
	}

	public void setNickName(String nickName) {
		m_nickName = nickName;
	}

	public String getNickName() {
		return m_nickName;
	}
	
	public void setDisplayName(String displayName) {
		m_displayName = displayName;
	}

	public String getDisplayName() {
		return m_displayName;
	}

	public void setTelephone(String telephone) {
		m_telephone = telephone;
	}
	
	public String getTelephone() {
		return m_telephone;
	}

	public String getGroup() {
		return m_group;
	}
	
	public void setGroup(String group) {
		m_group = group;
	}
	
	public void setUin(int uin) {
		if (uin < 0) throw new IllegalArgumentException("uin cannot be less than 0");
		m_uin = uin;
	}

	public int getUin() {
		return m_uin;
	}
	
	public void setEmailAddress(String emailAddress) {
		m_emailAddress = emailAddress;
	}
	
	public String getEmailAddress() {
		return m_emailAddress;
	}
	
}
