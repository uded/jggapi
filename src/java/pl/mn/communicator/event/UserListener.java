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
package pl.mn.communicator.event;

import java.util.EventListener;

import pl.mn.communicator.IStatus;
import pl.mn.communicator.IUser;

/**
 * The listener interface that is for classes that want to
 * be notified of user statuses events.
 * <p>
 * If the user changes status the class that implements this interface,
 * so called UserHandler is notified.
 * <p>
 * Created on 2004-11-28
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: UserListener.java,v 1.6 2004-12-19 21:19:58 winnetou25 Exp $
 */
public interface UserListener extends EventListener {

	/**
	 * The notification that the user changed the status.
	 * 
	 * @param user the Gadu-Gadu user that changed the status.
	 * @param newStatus the new status of the user.
     */
    void userStatusChanged(IUser user, IStatus newStatus);
    
}
