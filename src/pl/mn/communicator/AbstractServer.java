package pl.mn.communicator;


/**
 * Klasa reprezentuj¹ca serwer rozmów.
 * 
 * @author mnaglik
 */
public abstract class AbstractServer {
	/**
	 * Adres ip, lub tekstowy serwera rozmów
	 */
	protected String address;
	
	/**
	 * Numer portu serwera
	 */
	protected int port;

	/**
	 * Utworz adres serwera rozmów.
	 * 
	 * @param address adres serwera
	 * @param port post serwera
	 */
	public AbstractServer(String address, int port) {
		this.address = address;
		this.port = port;
	}
	/**
	 * Zwróæ adres serwera rozmów.
	 * 
	 * @return String
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Zwróæ port serwera rozmów.
	 *
	 *  @return int
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "[" + this.address + "-" + this.port + "]";
	}
}
