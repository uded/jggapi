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
 *
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: StatusType.java,v 1.3 2004-12-13 23:43:51 winnetou25 Exp $
 */
public class StatusType {

	private String m_status;
	
	private StatusType(String status) {
		m_status = status;
	}

	/** Status online */
	public final static StatusType ONLINE = new StatusType("online");
	
	/** Status online with description */
	public final static StatusType ONLINE_WITH_DESCRIPTION = new StatusType("online_with_description");
	
	/** Status offline */
	public final static StatusType OFFLINE = new StatusType("offline");
	
	/** Status offline with description */
	public final static StatusType OFFLINE_WITH_DESCRIPTION = new StatusType("offline_with_description");
	
	/** Status busy */
	public final static StatusType BUSY = new StatusType("busy");
	
	/** Status busy with description */
	public final static StatusType BUSY_WITH_DESCRIPTION = new StatusType("busy_with_description");
	
	/** Status invisible */
	public final static StatusType INVISIBLE = new StatusType("invisible");
	
	/** Status invisible with description */
	public final static StatusType INVISIBLE_WITH_DESCRIPTION = new StatusType("invisible_with_description");
	
	public boolean isDescriptionStatus() {
		return m_status.endsWith("description");
	}
	
    public String toString() {
        return m_status;
    }
    
    /**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return m_status.hashCode();
	}

    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
	
}
