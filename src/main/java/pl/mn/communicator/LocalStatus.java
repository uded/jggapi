/*
 * Copyright (c) 2003-2005 JGGApi Development Team. All Rights Reserved. This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version. This program is distributed in
 * the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a
 * copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc.,
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
 * @version $Id: LocalStatus.java,v 1.1 2005/11/05 23:34:52 winnetou25 Exp $
 */
public class LocalStatus extends AbstractStatus implements ILocalStatus {

	/** the field that indicated whether or user that logs on wants to be seen only by friends */
	private boolean m_friendsOnly = false;

	public LocalStatus(final StatusType statusType) {
		super(statusType);
	}

	public LocalStatus(final StatusType statusType, final String description) {
		super(statusType, description);
	}

	public LocalStatus(final StatusType statusType, final String description, final Date returnDate) {
		super(statusType, description, returnDate);
	}

	/**
	 * @see pl.mn.communicator.ILocalStatus#isFriendsOnly()
	 */
	public boolean isFriendsOnly() {
		return m_friendsOnly;
	}

	public void setFriendsOnly(final boolean bool) {
		m_friendsOnly = bool;
	}

}
