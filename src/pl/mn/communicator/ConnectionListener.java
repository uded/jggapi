package pl.mn.communicator;

/**
 * Listener ze zdarzeniami po³¹czenia.
 * 
 * @version $Revision: 1.2 $
 * @author mnaglik
 */
public interface ConnectionListener {
	/**
	 * Po³¹czenie zosta³o pomyœlnie nawi¹zane.
	 */
	public void connectionEstablished();

	/**
	 * Roz³aczono z serwerem.<BR>
	 * Wywo³ywane podczas celowego roz³¹czania z serwerem.
	 */
	public void disconnected();

	/**
	 * Problem z po³¹czeniem.<BR>
	 * Wyst¹pi³ b³¹d w po³¹czeniu.
	 * 
	 * @param error tekstowy opis b³êdu zwi¹zanego z po³¹czeniem 
	 */
	public void connectionError(String error);
}
