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
 * The default implementation of <code>ILocalStatus</code>.
 * <p>
 * Created on 2004-12-21
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: LocalStatus.java,v 1.2 2004-12-23 17:52:24 winnetou25 Exp $
 */
public class LocalStatus extends AbstractStatus implements ILocalStatus {

	/** the field that indicated whether or user that logs on wants to be seen only by friends */
	private boolean m_friendsOnly = false;

	public LocalStatus(StatusType statusType) {
		super(statusType);
	}

	public LocalStatus(StatusType statusType, String description) {
		super(statusType, description);
	}

	public LocalStatus(StatusType statusType, String description, Date returnDate) {
		super(statusType, description, returnDate);
	}
	
	/**
	 * @see pl.mn.communicator.IStatus#isFriendsOnly()
	 */
	public boolean isFriendsOnly() {
		return m_friendsOnly;
	}
	
	/**
	 * @see pl.mn.communicator.IStatus#setFriendsOnly(boolean)
	 */
	public void setFriendsOnly(boolean bool) {
		m_friendsOnly = bool;
	}

}
