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

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: Status.java,v 1.3 2004-12-12 01:22:29 winnetou25 Exp $
 */
public class Status implements IStatus {
	
    private static Log logger = LogFactory.getLog(Status.class);

    private StatusConst m_status = StatusConst.ONLINE;
    
    /** Status description */
    private String m_description = null;

    private Date m_returnTime = null;
    
    private boolean m_friendsOnly = false;
    
    private boolean m_blocked = false;

    /**
     * @param status the status of user
     */
    public Status(StatusConst status) {
    	this(status, null, null);
    }
    
    public Status(StatusConst status, String description) {
    	this(status, description, null);
    }
    
    public Status (StatusConst status, String description, Date returnDate) {
    	m_status = status;
    	m_description = description;
    	m_returnTime = returnDate;
    }
    
    /**
	 * @see pl.mn.communicator.IStatus#getStatus()
	 */
	public StatusConst getStatus() {
		return m_status;
	}
	
	/**
	 * @see pl.mn.communicator.IStatus#setStatus(int)
	 */
	public void setStatus(StatusConst status) {
		m_status = status;
	}

     /**
     * @return Returns the description.
     */
    public String getDescription() {
        return m_description;
    }

    /**
     * @param description The description to set.
     */
    public void setDescription(String description) {
        m_description = description;
    }
    
    /**
     * @return Returns the returnTime.
     */
    public Date getReturnDate() {
        return m_returnTime;
    }

    /**
     * @param returnTime The returnTime to set.
     */
    public void setReturnDate(Date returnTime) {
    	if (returnTime == null) throw new NullPointerException("returnTime cannot be null");
        m_returnTime = returnTime;
    }
    
    /**
	 * @see pl.mn.communicator.IStatus#isDescriptionSet()
	 */
	public boolean isDescriptionSet() {
		return m_description != null;
	}
	
	/**
	 * @see pl.mn.communicator.IStatus#isReturnDateSet()
	 */
	public boolean isReturnDateSet() {
		return m_returnTime != null;
	}
	
    /**
	 * @see pl.mn.communicator.IStatus#isBlockedMask()
	 */
	public boolean isBlockedMask() {
		return m_blocked;
	}
	
	/**
	 * @see pl.mn.communicator.IStatus#isFriendsOnly()
	 */
	public boolean isFriendsOnly() {
		return m_friendsOnly;
	}
	
	/**
	 * @see pl.mn.communicator.IStatus#setBlockedMask(boolean)
	 */
	public void setBlockedMask(boolean bool) {
		m_blocked = bool;
	}
	
	/**
	 * @see pl.mn.communicator.IStatus#setFriendsOnly(boolean)
	 */
	public void setFriendsOnly(boolean bool) {
		m_friendsOnly = bool;
	}
	
    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "Status: " + m_status + ", description: " + m_description + ", time: " +m_returnTime;
    }
    
}
