package pl.mn.communicator.gadu;
import java.io.IOException;

import pl.mn.communicator.GGException;
import pl.mn.communicator.ISession;
import pl.mn.communicator.SessionFactory;

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

		final ISession session = SessionFactory.createSession();
		
//		GGToken token = session.getRegistrationService().getRegistrationToken();
//		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//		System.out.println("TokenURL: "+token.getFullTokenURL());
//		System.out.print("Wpisz tokenVal: ");
//		String line = reader.readLine();
//		
////		int uin = session.getRegistrationService().registerAccount("mati@niak.infoman.com.pl", "dupka", token.getTokenID(), line);
////		System.out.println("Nowy UIN: "+uin);
////		session.getRegistrationService().changePassword(2862549, "mati@niak.infoman.com.pl", "dupka", "dupka1", token.getTokenID(), line);
//		session.getRegistrationService().unregisterAccount(2862549, "dupka1", token.getTokenID(), line);
//		session.getRegistrationService().sendPassword(376798, "mati@sz.home.pl", token.getTokenID(), line.toLowerCase().trim());
	}
	
}
