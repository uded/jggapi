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
 * Created on 2004-12-12
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGUserMode.java,v 1.2 2004-12-18 15:10:03 winnetou25 Exp $
 */
public class GGUserMode {

	private String m_type = null;
	
	private GGUserMode(String type) {
		m_type = type;
	}

	public final static GGUserMode BUDDY = new GGUserMode("user_mode_buddy");
	public final static GGUserMode FRIEND = new GGUserMode("user_mode_friend");
	public final static GGUserMode BLOCKED = new GGUserMode("user_mode_blocked");

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
