package pl.mn.communicator;

import org.apache.log4j.Logger;

/**
 * U¿ytkownik lokalny klienta rozmów.<BR>
 * Obiekt reprezentuje lokalnego u¿ytkownika.
 * 
 * @author mnaglik
 */
public abstract class AbstractLocalUser {
	private static Logger logger = Logger.getLogger(AbstractLocalUser.class);
	/**
	 * Numer u¿ytkownika
	 */
	protected int userNo;

	/**
	 * Has³o u¿ytkownika
	 */
	protected String password;

	/**
	 * Utwórz u¿ytkownika lokalnego.
	 * 
	 * @param userNo nr uzytkownika
	 * @param password has³o
	 */
	public AbstractLocalUser(int userNo, String password) {
		this.userNo = userNo;
		this.password = password;
	}
	/**
	 * Pobierz has³o u¿ytkownika.<BR>
	 * Has³o jest w postaci niezaszyfrowanej. 
	 * 
	 * @return String password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Pobierz nr u¿ytkownika.
	 * 
	 * @return int userNo
	 */
	public int getUserNo() {
		return userNo;
	}

}
