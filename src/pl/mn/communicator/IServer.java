package pl.mn.communicator;

/**
 * @author mnaglik
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
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