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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The class represents Gadu-Gadu user.
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @version $Id: GGUser.java,v 1.1 2004-12-12 16:21:55 winnetou25 Exp $
 */
public class GGUser implements IUser {
	
    private static Log logger = LogFactory.getLog(GGUser.class);

    private int m_uin = -1;
    private GGUserMode m_userMode = null;
    
    public GGUser(int uin, GGUserMode userMode) {
    	if (uin < 0) throw new IllegalArgumentException("uin cannot be less than 0");
    	if (userMode == null) throw new NullPointerException("userMode cannot be null");
    	m_uin = uin;
    	m_userMode = userMode;
    }

    /**
     * @return int
     */
    public int getUin() {
        return m_uin;
    }
    
    /**
	 * @see pl.mn.communicator.IUser#getUserMode()
	 */
	public GGUserMode getUserMode() {
		return m_userMode;
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
        return "Uin: " + m_uin;
    }
    
}
