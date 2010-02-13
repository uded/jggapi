package pl.radical.open.gg.event;

import pl.radical.open.gg.GGException;

import java.util.EventListener;

/**
 * The listener interface that is related with login related events.
 * <p>
 * The classes that implement this interface are notified of whether the client's attempt to login was successful or
 * not.
 * <p>
 * Created on 2004-11-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public interface LoginListener extends EventListener {

	/**
	 * Messaged when user has successfully logged in to Gadu-Gadu server.
	 */
	void loginOK() throws GGException;

	/**
	 * Messaged when there was an error while logging in, most probably because of an incorrect password.
	 */
	void loginFailed(final LoginFailedEvent loginFailedEvent) throws GGException;

	void loggedOut() throws GGException;

	public static class Stub implements LoginListener {

		/**
		 * @see pl.radical.open.gg.event.LoginListener#loginOK()
		 */
		public void loginOK() throws GGException {
		}

		/**
		 * @see pl.radical.open.gg.event.LoginListener#loginFailed()
		 */
		public void loginFailed(final LoginFailedEvent loginFailedEvent) throws GGException {
		}

		/**
		 * @see pl.radical.open.gg.event.LoginListener#loggedOut()
		 */
		public void loggedOut() throws GGException {
		}

	}

}
