package pl.mn.communicator;

/**
 * Wiadomoœæ do serwera rozmów.
 * 
 * @author mnaglik
 */
public interface IMessage {
	/**
	 * Pobierz u¿ytkownika do którego jest wiadomoœæ
	 * 
	 * @return User 
	 */
	public int getUser();
	/**
	 * Pobierz treœæ wiadomoœæi
	 * 
	 * @return String
	 */
	public String getText();
	/**
	 * @param text
	 */
	public void setText(String text);
	/**
	 * @param user
	 */
	public void setUser(int user);
}