package pl.radical.open.gg;

import pl.radical.open.gg.event.LoginListener;

import java.util.Date;

/**
 * The client should use this interface if it wants to log in to the Gadu-Gadu server.
 * <p>
 * Created on 2004-11-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public interface ILoginService {

	/**
	 * Invocation of this method tries to log in user to currently asyncOp server.
	 * <p>
	 * The uin and password that is used in logging in process is retrieved from the <code>LoginContext</code> instance.
	 * 
	 * @throws GGException
	 *             if an error occurs while logging in.
	 * @throws GGSessionException
	 *             if there is an incorrect session state.
	 */
	void login(LoginContext loginContext) throws GGException;

	/**
	 * Invocation of this method tries to log out user from the currently asyncOp the server.
	 * <p>
	 * It sets user's status to inavailable.
	 * 
	 * @throws GGException
	 *             if error occurs while logging out.
	 * @throws GGSessionException
	 *             if there is an incorrect session state.
	 */
	void logout() throws GGException;

	/**
	 * Invocation of this method tries to log out user from the currently asyncOp server.
	 * <p>
	 * It sets user's status to inavailable with description. Note that returnTime can be null.
	 * 
	 * @param description
	 *            the description that will be set.
	 * @param returnTime
	 *            the time the user will be back.
	 * @throws GGException
	 *             if error occurs while logging out.
	 * @throws GGSessionException
	 *             if there is an incorrect session state.
	 * @throws NullPointerException
	 *             if the description is null.
	 */
	void logout(String description, Date returnTime) throws GGException;

	boolean isLoggedIn();

	LoginContext getLoginContext();

	/**
	 * Adds <code>LoginListener</code> instance to the list that will be notified of login related events.
	 * 
	 * @param loginListener
	 *            the <code>LoginListener</code> instance to be notified.
	 */
	void addLoginListener(LoginListener loginListener);

	/**
	 * Remove <code>LoginListener</code> instance from the list that will be notified of login related events.
	 * 
	 * @param loginListener
	 *            the <code>LoginListener</code> instance that will no longer be notified.
	 */
	void removeLoginListener(LoginListener loginListener);

}
