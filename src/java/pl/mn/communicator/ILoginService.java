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

import java.util.Date;

import pl.mn.communicator.event.LoginListener;

/**
 * Created on 2004-11-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: ILoginService.java,v 1.2 2004-12-18 15:11:12 winnetou25 Exp $
 */
public interface ILoginService {

	/**
	 * Logins user to currently connected server.
	 * 
	 * @throws GGException if an error occurs while logging in.
	 */
	void login() throws GGException;

	/**
	 * Logsout user from the server that we are currently connected.
	 * 
	 * @throws GGException if error occurs while logging out.
	 */
	void logout() throws GGException;
	
	/**
	 * Logs out user and sets the description.
	 * Description cannot be null but returnTime can
	 * be null.
	 * 
	 * @param description
	 * @param returnTime
	 * @throws GGException if error occurs while logging out.
	 */
	void logout(String description, Date returnTime) throws GGException;
	
	/**
	 * Adds <code>LoginListener</code> to be notified of loginOK
	 * or loginFailed events.
	 * 
	 * @param loginListener
	 */
	void addLoginListener(LoginListener loginListener);
	
	/**
	 * Remove <code>LoginListener</code> that was being notified
	 * of loginOK or loginFailed events.
	 * 
	 * @param loginListener
	 */
	void removeLoginListener(LoginListener loginListener);
	
}
