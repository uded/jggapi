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
public interface IMessage {
	/**
	 * Pobierz u¿ytkownika do którego jest wiadomoœæ
	 * 
	 * @return User 
	 */
	public int getUser();
	/**
	 * Pobierz treœæ wiadomoœæi
	 * 
	 * @return String
	 */
	public String getText();
	/**
	 * @param text
	 */
	public void setText(String text);
	/**
	 * @param user
	 */
	public void setUser(int user);
}