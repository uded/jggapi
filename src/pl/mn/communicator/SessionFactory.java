/*
 * Created on 2004-12-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pl.mn.communicator;

import pl.mn.communicator.gadu.handlers.Session;

/**
 * @author mateusz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SessionFactory {

	public static ISession createSession(LoginContext loginContext) throws GGException {
		IServer server = Server.getDefaultServer(loginContext);
		return new Session(server, loginContext);
	}
	
}
