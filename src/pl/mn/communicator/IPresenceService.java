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

import java.util.Collection;

import pl.mn.communicator.event.UserListener;

/**
 * Created on 2004-11-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: IPresenceService.java,v 1.8 2004-12-14 19:49:00 winnetou25 Exp $
 */
public interface IPresenceService {

	void setStatus(IStatus status) throws GGException;

	IStatus getStatus();
	
	void addMonitoredUser(IUser user) throws GGException;

	void removeMonitoredUser(IUser user) throws GGException;

	void changeMonitoredUserStatus(IUser user) throws GGException;
	
	Collection getMonitoredUsers();
	
	void addUserListener(UserListener userListener);
	void removeUserListener(UserListener userListener);
	
}
