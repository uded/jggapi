/*
 * Copyright (c) 2003-2005 JGGApi Development Team. All Rights Reserved. This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version. This program is distributed in
 * the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a
 * copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package pl.radical.open.gg;

/**
 * Created on 2004-11-11
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: LocalUser.java,v 1.2 2005/11/19 19:56:57 winnetou25 Exp $
 */
public class LocalUser {

	/** Gadu-Gadu user's uin */
	private int m_uin = -1;

	/** the name of the user */
	private String m_name = null;

	/** the last name of the user */
	private String m_lastName = null;

	/** the nick name of the user */
	private String m_nickName = null;

	/** the name that is an alias for the user */
	private String m_displayName = null;

	/** the telehone of the user */
	private String m_telephone = null;

	/** the group that the user belongs */
	private String m_group = null;

	/** the e-mail address of the user */
	private String m_emailAddress = null;

	/** flag to indicate that the user is blocked */
	private boolean m_isBlocked = false;

	public void setFirstName(final String name) {
		if (name == null) {
			throw new NullPointerException("name cannot be null");
		}
		m_name = name;
	}

	public String getFirstName() {
		return m_name;
	}

	public void setLastName(final String surname) {
		m_lastName = surname;
	}

	public String getLastName() {
		return m_lastName;
	}

	public void setNickName(final String nickName) {
		m_nickName = nickName;
	}

	public String getNickName() {
		return m_nickName;
	}

	public void setDisplayName(final String displayName) {
		m_displayName = displayName;
	}

	public String getDisplayName() {
		return m_displayName;
	}

	public void setTelephone(final String telephone) {
		m_telephone = telephone;
	}

	public String getTelephone() {
		return m_telephone;
	}

	public String getGroup() {
		return m_group;
	}

	public void setGroup(final String group) {
		m_group = group;
	}

	public void setUin(final int uin) {
		if (uin < 0) {
			throw new IllegalArgumentException("uin cannot be less than 0");
		}
		m_uin = uin;
	}

	public int getUin() {
		return m_uin;
	}

	public void setEmailAddress(final String emailAddress) {
		m_emailAddress = emailAddress;
	}

	public String getEmailAddress() {
		return m_emailAddress;
	}

	public void setBlocked(final boolean isBlocked) {
		m_isBlocked = isBlocked;
	}

	public boolean isBlocked() {
		return m_isBlocked;
	}

}
