package pl.mn.communicator;

import org.apache.log4j.Logger;

/**
 * Wiadomoœæ do serwera rozmów.
 * 
 * @author mnaglik
 */
public abstract class AbstractMessage implements IMessage {
	private static Logger logger = Logger.getLogger(AbstractMessage.class);
	/**
	 * Nr adresata wiadomoœci
	 */
	protected int user;
	
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
		this.user = toUser;
		this.text = text;
	}

	/**
	 * Pobierz u¿ytkownika do którego jest wiadomoœæ
	 * 
	 * @return User 
	 */
	public int getUser() {
		return user;
	}

	/**
	 * Pobierz treœæ wiadomoœæi
	 * 
	 * @return String
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @param user
	 */
	public void setUser(int user) {
		this.user = user;
	}

}
