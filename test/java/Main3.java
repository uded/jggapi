import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import pl.mn.communicator.GGException;
import pl.mn.communicator.GGToken;
import pl.mn.communicator.ISession;
import pl.mn.communicator.packet.handlers.Session;

/*
 * Created on 2004-11-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author mateusz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Main3 {

	public static void main(String args[]) throws IOException, GGException {

		final ISession session = new Session();
		
		GGToken token = session.getRegistrationService().getRegistrationToken();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String line = reader.readLine();
		
		//session.getRegistrationService().registerAccount("dupa@dupa.com", "dupka", token.getTokenID(), line, GGQuestions.FAVOURITE_ACTOR, "Willis");

		session.getRegistrationService().remindAndSendPassword(376798, "mati@sz.home.pl", token.getTokenID(), line.toLowerCase().trim());
	}
	
}
