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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The class represents Gadu-Gadu user.
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: User.java,v 1.5 2004-12-19 21:14:06 winnetou25 Exp $
 */
public class User implements IUser {
	
	private static Log logger = LogFactory.getLog(User.class);
	
	private int m_uin = -1;
	private UserMode m_userMode = null;
	
	public User(int uin, UserMode userMode) {
		if (uin < 0) throw new IllegalArgumentException("uin cannot be less than 0");
		if (userMode == null) throw new NullPointerException("userMode cannot be null");
		m_uin = uin;
		m_userMode = userMode;
	}
	
	public int getUin() {
		return m_uin;
	}
	
	/**
	 * @see pl.mn.communicator.IUser#getUserMode()
	 */
	public UserMode getUserMode() {
		return m_userMode;
	}
	
	public void setUserMode(UserMode userMode) {
		if (userMode == null) throw new NullPointerException("userMode cannot be null");
		m_userMode = userMode;
	}
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if (o instanceof IUser) {
			IUser user = (IUser) o;
			if (user.getUin() == m_uin) return true;
		}
		return false;
	}
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return m_uin;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "[Uin: " + m_uin+", userMode: "+m_userMode+"]";
	}
	
	public static class UserMode {
		
		private String m_type = null;
		
		private UserMode(String type) {
			m_type = type;
		}
		
		public final static UserMode BUDDY = new UserMode("user_mode_buddy");
		public final static UserMode FRIEND = new UserMode("user_mode_friend");
		public final static UserMode BLOCKED = new UserMode("user_mode_blocked");
		
		/**
		 * @see java.lang.Object#toString()
		 */
		public String toString() {
			return m_type;
		}
		
		/**
		 * @see java.lang.Object#hashCode()
		 */
		public int hashCode() {
			return m_type.hashCode();
		}
	}
	
}
