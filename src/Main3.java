/*
 * Created on 2004-10-23
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
import java.io.IOException;

import pl.mn.communicator.GGException;
import pl.mn.communicator.IRegistrationService;
import pl.mn.communicator.ISession;
import pl.mn.communicator.QA;
import pl.mn.communicator.gadu.handlers.Session;

/**
 * @author mateusz
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Main3 {
	
	public static void main(String args[]) throws IOException {
		ISession session = new Session();
		IRegistrationService registrationService = session.getRegistrationService();
		try {
			registrationService.registerAccount("mati@niak.infoman.com.pl", "ziomal123456", QA.GRAND_MOTHER_NAME, "Klara");
		} catch (GGException e) {
			e.printStackTrace();
		}
	}
	
}