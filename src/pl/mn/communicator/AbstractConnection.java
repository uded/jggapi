package pl.mn.communicator;

import org.apache.log4j.Logger;

/**
 * Po³¹czenie z serwerem gg.<BR>
 * S³u¿y do tworzenia po³¹czenia.
 * <BR><BR>
 * <i>Przyk³ad u¿ycia:</i><BR><BR>
 * <code>
 * AbstractLocalUser user = new XXXLocalUser(1234,"password");<BR>
 * AbstractServer server = XXXServerAddress.getHost(user);<BR>
 * AbstractServer s = new XXXServer(server.user);<BR>
 * AcstractConnection c = new XXXConnection();<BR><BR>
 * try{<BR>
 * &nbsp; &nbsp; c.connect();<BR>
 * }catch(Exception e){<BR>
 * &nbsp; &nbsp; ...<BR>
 * }
 * </code>
 * 
 * @author mnaglik
 */
public abstract class AbstractConnection implements IConnection {
	private static Logger logger = Logger.getLogger(AbstractConnection.class);
	/**
	 * Listener u¿ytkowników
	 */
	protected UserListener userListener = null;

	/**
	 * Listener po³¹czenia
	 */
	protected ConnectionListener connectionListener = null;

	/**
	 * Listener wiadomoœci
	 */
	protected MessageListener messageListener = null;

	/**
	 * Dodaj listenera u¿ytkowników.<BR>
	 * Obs³uguje odpowiednie zdarzenia zwi¹zane z u¿ytkownikami
	 * takie jak pryjœcie i odejœcie u¿ytkownika
	 * 
	 * @see UserListener
	 * @param userListener obiekt listenera
	 */
	public void addUserListener(UserListener userListener) {
		this.userListener = userListener;
	}
	/**
	 * Usuwa listenera u¿ytkowników.<BR>
	 * Je¿eli nie ma aktywnego listenera nic siê nie dzieje.
	 * 
	 * @see UserListener
	 */
	public void removeUserListener() {
		this.userListener = null;
	}

	/**
	 * Dodaj listenera zwi¹zanego z po³¹czeniem.<BR>
	 * Obs³uguje on takie zdarzenia jak nawi¹zanie po³¹czenia,
	 * zerwanie po³¹czenia itp.
	 * 
	 * @see ConnectionListener
	 * @param connectionListener obiekt listenera
	 */
	public void addConnectionListener(ConnectionListener connectionListener) {
		this.connectionListener = connectionListener;
	}
	/**
	 * Usuwa listenera zwi¹zanego z po³¹czeniem.<BR>
	 * Jê¿eli nie ma aktywnego listenera nic siê nie dzieje.
	 * 
	 * @see ConnectionListener
	 */
	public void removeConnectionListener() {
		this.connectionListener = null;
	}
	/**
	 * Dodaje listenera wiadomoœci.<BR>
	 * Obs³uguje on takie zdarzenia jak nadejœcie wiadomoœci.
	 * 
	 * @see MessageListener 
	 * @param messageListener obiekt listenera
	 */
	public void addMessageListener(MessageListener messageListener) {
		this.messageListener = messageListener;
	}

	/**
	 * Usuwa listenera wiadomoœci.<BR>
	 * Je¿eli nie ma aktywnego listenera nic siê nie dzieje.
	 * 
	 * @see MessageListener
	 */
	public void removeMessageListener() {
		this.messageListener = null;
	}
}