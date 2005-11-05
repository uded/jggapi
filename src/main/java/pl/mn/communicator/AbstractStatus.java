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

import java.util.Date;

/**
 * The abstact status implementation that is common for
 * LocalStatus and RemoteStatus.
 * <p>
 * Created on 2004-12-21
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: AbstractStatus.java,v 1.1 2005-11-05 23:34:52 winnetou25 Exp $
 */
public abstract class AbstractStatus implements IStatus {

	/** the type of the status */
	private StatusType m_statusType = StatusType.ONLINE;
	
	/** Status description */
	private String m_description = null;

	/** Return time */
	private Date m_returnTime = null;

	protected AbstractStatus(StatusType statusType, String description, Date returnDate) {
		if (statusType == null) throw new NullPointerException("statusType cannot be null");
		m_statusType = statusType;
		m_description = description;
		m_returnTime = returnDate;
	}

	protected AbstractStatus(StatusType statusType, String description) {
		this(statusType, description, null);
	}
	
	protected AbstractStatus(StatusType statusType) {
		this(statusType, null, null);
	}

	/**
	 * @see pl.mn.communicator.IStatus#getStatusType()
	 */
	public StatusType getStatusType() {
		return m_statusType;
	}

	public void setStatusType(StatusType status) {
		m_statusType = status;
	}

	/**
	 * Returns the description.
	 * 
	 * @return <code>String</code> description as string.
	 */
	public String getDescription() {
		return m_description;
	}

	/**
	 * @param description the description to set.
	 */
	public void setDescription(String description) {
		m_description = description;
	}

	/**
	 * @return <code>Date</code> the return time.
	 */
	public Date getReturnDate() {
	    if (m_returnTime == null) return null;
		return new Date(m_returnTime.getTime());
	}

	/**
	 * @param returnTime The return time to set.
	 */
	public void setReturnDate(Date returnTime) {
		if (returnTime == null) throw new NullPointerException("returnTime cannot be null");
		m_returnTime = new Date(returnTime.getTime());
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
	
}
