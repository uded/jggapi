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

import pl.mn.communicator.packet.handlers.Session;

/**
 * This is the factory class that helps developers
 * to create a new instance of a session class.
 * <p>
 * Created on 2004-12-12
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: SessionFactory.java,v 1.10 2004-12-20 22:42:11 winnetou25 Exp $
 */
public class SessionFactory {
	
	/**
	 * Creates <code>ISession></code> instance based on <code>LoginContext</code> object.
	 * <p>
	 * It attempts to get default server based on user's uin that is inside
	 * <code>LoginContext</code> object.
	 * 
	 * @param loginContext the login context that is used to retrieve server and is necessary for <code>session</code> instance.
	 * @throws GGException if there is an error during looking up of the Gadu-Gadu server.
	 * @throws NullPointerException if the supplied loginContext is null.
	 * @return <code>ISession</code> instance.
	 */
	public static ISession createSession(LoginContext loginContext) throws GGException {
		IServer server = Server.getDefaultServer(loginContext);
		return new Session(server, loginContext);
	}

	/**
	 * Create <code>ISession</code> instance based on <code>LoginContext</code> object.
	 * Address is the IP address of the Gadu-Gadu server.
	 * Port is the port of the Gadu-Gadu server.
	 * 
	 * @param loginContext the <code>LoginContext</code> instance to be used.
	 * @param address IP address of the Gadu-Gadu server.
	 * @param port port number of tha Gadu-Gadu server.
	 * @throws NullPointerException if the address or loginContext is null.
     * @throws IllegalArgumentException if the port is not value between 0 and 65535.
	 * @return <code>ISession</code> instance.
	 */
	public static ISession createSession(LoginContext loginContext, String address, int port) {
		IServer server = new Server(address, port);
		return new Session (server, loginContext);
	}

	/**
	 * Create <code>ISession</code> instance based on <code>LoginContext</code> object
	 * and on separately specified <code>IServer</code> object.
	 * 
	 * @param loginContext the <code>LoginContext</code> object that contains information necessary for authenticating a user.
	 * @param server <code>IServer</code> object that referes to the Gadu-Gadu server.
	 * @throws NullPointerException if the server or loginContext is null.
	 * @return <code>ISession</code> instance.
	 */
	public static ISession createSession(LoginContext loginContext, IServer server) {
		return new Session(server, loginContext);
	}
	
}
