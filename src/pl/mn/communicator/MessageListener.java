package pl.mn.communicator;

/**
 * Listener wiadomoœci.<BR>
 * Obs³uguje zdarzenia zwi¹zane z wiadomoœciami.<BR>
 * 
 * @version $Revision: 1.3 $
 * @author mnaglik
 */
public interface MessageListener {
	/**
	 * Nadesz³a wiadomoœæ.
	 * 
	 * @param message wiadomoœæ z serwera rozmów
	 */
	public void messageArrived(IMessage message);
}
