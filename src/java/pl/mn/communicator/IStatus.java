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
 * Interface that is common for all statuses, that is local and remote.
 * <p>
 * Created on 2004-12-12
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: IStatus.java,v 1.9 2004-12-23 17:52:24 winnetou25 Exp $
 */
public interface IStatus {

    /**
     * Get the StatusType.
     * 
     * @return StatusType
     */
    StatusType getStatusType();

    /** 
     * Get the status description.
     * 
     * @return description of status.
     */
    String getDescription();
    
    /**
     * Get the return date.
     * 
     * @return the return date.
     */
    Date getReturnDate();
    
    /**
     * Tells if the description has been set on this status.
     * 
     * @return <code>true</code> if the description has been set, <code>false</code> otherwise.
     */
    boolean isDescriptionSet();
    
    /**
     * Tells if the return date has been set on this status instance.
     * 
     * @return <code>true</code> if the return date has been set, <code>false</code> otherwise.
     */
    boolean isReturnDateSet();
    
}
