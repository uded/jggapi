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
public interface LoginListener {
	
	/**
	 * Messaged when user has successfully logged in to Gadu-Gadu server.
	 */
	void loginOK();
	
	/** 
	 * Messaged when user has not successfully logged in to Gadu-Gadu server.
	 */
	void loginFailed();
	
	public static class Stub implements LoginListener {
		
		/**
		 * @see pl.mn.communicator.LoginListener#userLoggedIn()
		 */
		public void loginOK() { }
		
		/**
		 * @see pl.mn.communicator.LoginListener#userLoggedOut()
		 */
		public void loginFailed() { }

	}
	
}
