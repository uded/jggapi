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
 * The Gender of the Gadu-Gadu user.
 * <p>
 * Created on 2004-12-16
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: Gender.java,v 1.6 2004-12-23 17:52:24 winnetou25 Exp $
 */
public class Gender {

	/** String that represents a user's gender */
	private String m_gender = null;
		
	private Gender(String gender) {
		m_gender = gender;
	}
		
	public final static Gender FEMALE = new Gender("female");
	public final static Gender MALE = new Gender("male");

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return m_gender;
	}
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return m_gender.hashCode();
	}
	
}
