/*
 * Created on 2003-10-01
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package pl.mn.communicator;

/**
 * @author mnaglik
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public interface ILocalUser {
	/**
	 * Pobierz has³o u¿ytkownika.<BR>
	 * Has³o jest w postaci niezaszyfrowanej. 
	 * 
	 * @return String password
	 */
	public String getPassword();
	/**
	 * Pobierz nr u¿ytkownika.
	 * 
	 * @return int userNo
	 */
	public int getUserNo();
	/**
	 * @param password
	 */
	public void setPassword(String password);
	/**
	 * @param userNo
	 */
	public void setUserNo(int userNo);
}