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
 * @version $Id: ILoginService.java,v 1.6 2004-12-14 20:10:50 winnetou25 Exp $
 */
public interface ILoginService {

	/**
	 * Logins user to currently connected server.
	 * 
	 * @throws GGException if an error occurs while logging in.
	 */
	void login() throws GGException;

	/**
	 * Logs out user from the server we are currently conneced.
	 * 
	 * @throws GGException if there is an error while logging out.
	 */
	void logout() throws GGException;
	
	/**
	 * Logs out user and sets the description.
	 * Description cannot be null, but the second parameter the returnTime can
	 * be null.
	 * @param description
	 * @throws GGException
	 */
	void logout(String description, Date returnTime) throws GGException;
	void addLoginListener(LoginListener loginListener);
	void removeLoginListener(LoginListener loginListener);
	
}
