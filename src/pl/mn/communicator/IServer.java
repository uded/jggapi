package pl.mn.communicator;

/**
 * Klasa reprezentuj¹ca serwer rozmów.
 * 
 * @version $Revision: 1.3 $
 * @author mnaglik
 */
public interface IServer {
	/**
	 * Zwróæ adres serwera rozmów.
	 * 
	 * @return String
	 */
	public String getAddress();
	/**
	 * Zwróæ port serwera rozmów.
	 *
	 *  @return int
	 */
	public int getPort();
	/**
	 * @param address
	 */
	public void setAddress(String address);
	/**
	 * @param port
	 */
	public void setPort(int port);
}