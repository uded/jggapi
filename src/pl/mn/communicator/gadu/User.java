package pl.mn.communicator.gadu;

import org.apache.log4j.Logger;

import pl.mn.communicator.AbstractUser;

/**
 * Uzytkownik gg.
 * 
 * @version $Revision: 1.3 $
 * @author mnaglik
 */
public class User extends AbstractUser {
	private static Logger logger = Logger.getLogger(User.class);
	public User(int number, String name, boolean onLine) {
		super(number,name,onLine);
	}
}
