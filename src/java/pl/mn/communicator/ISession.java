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

import pl.mn.communicator.event.SessionStateListener;

/**
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: ISession.java,v 1.8 2004-12-19 21:19:57 winnetou25 Exp $
 */
public interface ISession {

	/**
	 * Returns object representing Gadu-Gadu server that 
	 * is currently associated with this session.
	 * 
	 * @return <code>IServer</code> associated with the session instance.
	 */
	IServer getServer();
	
	/**
	 * Returns login context that is currenly bound to this session.
	 * 
	 * @return <code>LoginContext</code>
	 */
	LoginContext getLoginContext();

	/**
	 * Returns state of this session.
	 * 
	 * @return <code>SessionState</code>
	 */
	SessionState getSessionState();
	
	IConnectionService getConnectionService();

	ILoginService getLoginService();

	IMessageService getMessageService();
	
	IPresenceService getPresenceService();

	IContactListService getContactListService();

	IPublicDirectoryService getPublicDirectoryService();
	
//	IRegistrationService getRegistrationService();
//
//	IFileService getFileService();

	void addSessionStateListener(SessionStateListener sessionStateListener);
	
	void removeSessionStateListener(SessionStateListener sessionStateListener);
	
}
