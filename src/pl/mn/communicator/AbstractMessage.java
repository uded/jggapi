package pl.mn.communicator;

/**
 * Wiadomoœæ do serwera rozmów.
 * 
 * @author mnaglik
 */
public abstract class AbstractMessage {
	/**
	 * Nr adresata wiadomoœci
	 */
	protected int toUser;
	
	/**
	 * Treœæ wiadomoœci
	 */
	protected String text;

	/**
	 * Tworzy wiadomoœæ do konkretnego u¿ytkownika.
	 * 
	 * @param toUser nr u¿ytkownika do którego wysy³amy
	 * @param text wiadomoœæ tekstowa
	 */
	public AbstractMessage(int toUser, String text) {
		this.toUser = toUser;
		this.text = text;
	}

	/**
	 * Pobierz u¿ytkownika do którego jest wiadomoœæ
	 * 
	 * @return User 
	 */
	public int getUser() {
		return toUser;
	}

	/**
	 * Pobierz treœæ wiadomoœæi
	 * 
	 * @return String
	 */
	public String getText() {
		return text;
	}
}
