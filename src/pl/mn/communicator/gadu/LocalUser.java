package pl.mn.communicator.gadu;

import org.apache.log4j.Logger;

import pl.mn.communicator.AbstractLocalUser;

/**
 * U¿ytkownik gg.
 * 
 * @author mnaglik
 */
public final class LocalUser extends AbstractLocalUser{
	private static Logger logger = Logger.getLogger(LocalUser.class);
	public LocalUser(int userNo, String password) {
		super(userNo,password);
	}
}
