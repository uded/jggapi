package pl.mn.communicator;

/**
 * Listener wiadomoœci.<BR>
 * Obs³uguje zdarzenia zwi¹zane z wiadomoœciami.<BR>
 * 
 * @author mnaglik
 */
public interface MessageListener {
	/**
	 * Nadesz³a wiadomoœæ.
	 * 
	 * @param message wiadomoœæ z serwera rozmów
	 */
	public void messageArrived(AbstractMessage message);
}
