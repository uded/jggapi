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
public interface IRegistrationService {

	/**
	 * 
	 * @param email
	 * @param passsord
	 * @param qa
	 * @param answer
	 */
	void changePassword(String email, String passsord, int qa, String answer);
	
	/**
	 * Use this method if you want to create a new Gadu-Gadu account.<BR>
	 * 
	 * @param email Email address that is associated with the new account.
	 * @param password Password to access the new account.
	 * @param qa The question type that is used to remind password.
	 * @param answer to the qa question.
	 * @return uin of new Gadu-Gadu account.
	 * @throws <code>GGException</code> if error occurs while registering new Gadu-Gadu account.
	 * @see 
	 */
	int registerAccount(String email, String password, int qa, String answer) throws GGException;

	/**
	 * Use this method if you want to delete your current account from Gadu-Gadu server.<BR>
	 * 
	 * @param uin Gadu-Gadu number to unregister.
	 * @param password the password that will be used together with uin in unregistration process.
	 * @return <code>true</code> if unregistration was successful, false otherwise.
	 * @throws <code>GGException</code> if error occurs while unregistering Gadu-Gadu account.
	 */
	void unregisterAccount(long uin, String password) throws GGException;
	
	/**
	 * Sends user's current password to user's mailbox.
	 */
	void remindAndSendPassword(long uin);
	
}
