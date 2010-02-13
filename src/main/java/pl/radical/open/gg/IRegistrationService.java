package pl.radical.open.gg;

/**
 * Created on 2004-11-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public interface IRegistrationService {

	GGToken getRegistrationToken() throws GGException;

	/**
	 * This method allows to change password on Gadu-Gadu account.
	 */
	void changePassword(int uin, String email, String oldPassword, String newPassword, String tokenID, String tokenVal) throws GGException;

	/**
	 * Use this method if you want to create a new Gadu-Gadu account.
	 * 
	 * @param email
	 *            e-mail address that is associated with the new account.
	 * @param password
	 *            Password to access the new account.
	 * @param tokenID
	 *            the id of the token
	 * @param tokenVal
	 *            the value of the token
	 * @return uin of new Gadu-Gadu account.
	 * @throws <code>GGException</code> if error occurs while registering new Gadu-Gadu account.
	 */
	int registerAccount(String email, String password, String tokenID, String tokenVal) throws GGException;

	/**
	 * Use this method if you want to delete your current account from Gadu-Gadu server.
	 * 
	 * @param uin
	 *            Gadu-Gadu number to unregister.
	 * @param password
	 *            the password that will be used together with uin in unregistration process.
	 * @param tokenID
	 *            the id of the token
	 * @param tokenVal
	 *            the value of the token
	 * @return <code>true</code> if unregistration was successful, false otherwise.
	 * @throws <code>GGException</code> if error occurs while unregistering Gadu-Gadu account.
	 */
	void unregisterAccount(int uin, String password, String tokenID, String tokenVal) throws GGException;

	/**
	 * Sends user's current password to user's mailbox.
	 * 
	 * @param uin
	 *            User's Gadu-Gadu number.
	 * @param tokenID
	 *            the id of the token
	 * @param tokenVal
	 *            the value of the token
	 */
	void sendPassword(int uin, String email, String tokenID, String tokenVal) throws GGException;

}
