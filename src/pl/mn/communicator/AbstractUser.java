package pl.mn.communicator;

import org.apache.log4j.Logger;

/**
 * U¿ytkownik serwera rozmów.
 *  
 * @author mnaglik
 */
public class AbstractUser {
	private static Logger logger = Logger.getLogger(AbstractUser.class);

	protected int number;
	protected boolean onLine;
	protected String name;

	public AbstractUser(int number, String name, boolean onLine) {
		this.number = number;
		this.onLine = onLine;
		this.name = name;
	}

	/**
	 * Zwróæ nick u¿ytkownika.
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Zwróæ numer u¿ytkownika.
	 * 
	 * @return int
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * Zmieñ nick u¿ytkownika
	 * 
	 * @param name nowe nick u¿ytkownika
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Zmieñ numer u¿ytkownika.
	 * 
	 * @param number nowy numer u¿ytkownika
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * Zwróæ status u¿ytkownika.
	 * True - u¿ytkownik online
	 * 
	 * @return boolean
	 */
	public boolean isOnLine() {
		return onLine;
	}

	/**
	 * Zmieñ status u¿ytkownika.
	 * 
	 * @param onLine nowy status u¿ytkownika
	 */
	public void setOnLine(boolean onLine) {
		this.onLine = onLine;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		return (o instanceof AbstractUser) && (number == ((AbstractUser) o).getNumber());
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return number;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "[" + name + ":" + number + ":" + onLine + "]";
	}

}
