/*
 * Created on 2004-11-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pl.mn.communicator;

/**
 * @author mateusz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface ILoginService {

	/**
	 * Login user to currently connected server.
	 * @param loginContext to use while loggin.
	 * @throws GGException if an error occurs while logging in.
	 */
	void login(LoginContext loginContext) throws GGException;
	void logout() throws GGException;
	void logout(String description) throws GGException;
	void addLoginListener(LoginListener loginListener);
	void removeLoginListener(LoginListener loginListener);
	
}
