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

/**
 * The listener interface that is related with login related events.
 * <p>
 * The classes that implement this interface are notified of
 * whether the client's attempt to login was successful or not.
 * <p>   
 * Created on 2004-11-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: LoginListener.java,v 1.9 2004-12-26 22:08:31 winnetou25 Exp $
 */
public interface LoginListener extends EventListener {
	
	/**
	 * Messaged when user has successfully
	 * logged in to Gadu-Gadu server.
	 */
	void loginOK();
	
	/** 
	 * Messaged when there was an error while logging in,
	 * most probably because of an incorrect password.
	 */
	void loginFailed();
	
	public static class Stub implements LoginListener {
		
		/**
		 * @see pl.mn.communicator.event.LoginListener#loginOK()
		 */
		public void loginOK() { }
		
		/**
		 * @see pl.mn.communicator.event.LoginListener#loginFailed()
		 */
		public void loginFailed() { }

	}
	
}
