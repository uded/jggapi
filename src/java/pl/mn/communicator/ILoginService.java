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

import pl.mn.communicator.event.LoginListener;

/**
 * The client should use this interface if it wants to log in
 * to the Gadu-Gadu server.
 * <p>
 * Created on 2004-11-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: ILoginService.java,v 1.4 2004-12-19 17:14:35 winnetou25 Exp $
 */
public interface ILoginService {

	/**
	 * Invocation of this method tries to log in user to currently connected server.
	 * <p>
	 * The uin and password that is used in logging in process
	 * is retrieved from the <code>LoginContext</code> instance.
	 * 
	 * @throws GGException if an error occurs while logging in.
	 * @throws GGSessionException if there is an incorrect session state.
	 */
	void login() throws GGException;

	/**
	 * Invocation of this method tries to log out user from the
	 * currently connected the server.
	 * <p>
	 * It sets user's status to inavailable.
	 * 
	 * @throws GGException if error occurs while logging out.
	 * @throws GGSessionException if there is an incorrect session state.
	 */
	void logout() throws GGException;
	
	/**
	 * Invocation of this method tries to log out user from the
	 * currently connected server.
	 * <p>
	 * It sets user's status to inavailable with description.
	 * Note that returnTime can be null.
	 * 
	 * @param description the description that will be set.
	 * @param returnTime the time the user will be back.
	 * @throws GGException if error occurs while logging out.
	 * @throws GGSessionException if there is an incorrect session state.
	 * @throws NullPointerException if the description is null.
	 */
	void logout(String description, Date returnTime) throws GGException;
	
	/**
	 * Adds <code>LoginListener</code> instance to the list that
	 * will be notified of login related events.
	 * 
	 * @param loginListener the <code>LoginListener</code> instance to be notified.
	 */
	void addLoginListener(LoginListener loginListener);
	
	/**
	 * Remove <code>LoginListener</code> instance from the list that
	 * will be notified of login related events.
	 * 
	 * @param loginListener the <code>LoginListener</code> instance that will no longer be notified.
	 */
	void removeLoginListener(LoginListener loginListener);
	
}
