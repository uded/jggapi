package pl.mn.communicator;

/**
 * Listener zwi¹zany ze zdarzeniami u¿ytkowników.
 * 
 * @author mnaglik
 */
public interface UserListener {
	/**
	 * U¿ytkownik pod³¹czy³ siê.
	 * 
	 * @param userNumber nr u¿ytkownika, który siê pod³¹czy³
	 */
	public void userEntered(int userNumber);

	/**
	 * U¿ytkownik od³¹czy³ siê.
	 * 
	 * @param userNumber nr u¿ytkownika, który siê od³¹czy³
	 */
	public void userLeaved(int userNumber);
}
