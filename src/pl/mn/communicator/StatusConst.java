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
 * @author mateusz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class StatusConst {

	private String m_status;
	
	private StatusConst(String status) {
		m_status = status;
	}

	/** Status online */
	public final static StatusConst ONLINE = new StatusConst("online");
	
	/** Status online with description */
	public final static StatusConst ONLINE_WITH_DESCRIPTION = new StatusConst("online_with_desc");
	
	/** Status offline */
	public final static StatusConst OFFLINE = new StatusConst("offline");
	
	/** Status offline with description */
	public final static StatusConst OFFLINE_WITH_DESCRIPTION = new StatusConst("offline_with_desc");
	
	/** Status busy */
	public final static StatusConst BUSY = new StatusConst("busy");
	
	/** Status busy with description */
	public final static StatusConst BUSY_WITH_DESCRIPTION = new StatusConst("busy_with_desc");
	
	/** Status invisible */
	public final static StatusConst INVISIBLE = new StatusConst("invisible");
	
	/** Status invisible with description */
	public final static StatusConst INVISIBLE_WITH_DESCRIPTION = new StatusConst("invisible_with_desc");
	
	public boolean isDescriptionStatus() {
		return m_status.endsWith("desc");
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
