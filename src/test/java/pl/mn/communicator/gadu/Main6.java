package pl.mn.communicator.gadu;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import pl.mn.communicator.GGException;
import pl.mn.communicator.IServer;
import pl.mn.communicator.ISession;
import pl.mn.communicator.LocalUser;
import pl.mn.communicator.LoginContext;
import pl.mn.communicator.PersonalInfo;
import pl.mn.communicator.PublicDirSearchReply;
import pl.mn.communicator.SessionFactory;
import pl.mn.communicator.event.ConnectionListener;
import pl.mn.communicator.event.ContactListListener;
import pl.mn.communicator.event.LoginFailedEvent;
import pl.mn.communicator.event.LoginListener;
import pl.mn.communicator.event.PublicDirListener;

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
public class Main6 {
	
	public static void main(String args[]) throws IOException, GGException {
		
		final ISession session = SessionFactory.createSession();
		
		final LoginContext loginContext = new LoginContext(1038285, "test");
		
		session.getConnectionService().addConnectionListener(new ConnectionListener.Stub() {
			
			public void connectionEstablished() throws GGException {
				System.out.println("Connection established.");
				session.getLoginService().login(loginContext);
			}
			
			public void connectionClosed() {
				System.out.println("Connection closed.");
			}
			
			public void connectionError(Exception ex) throws GGException {
				System.out.println("Connection Error: "+ex.getMessage());
				session.getConnectionService().disconnect();
			}
			
		});
		
		session.getContactListService().addContactListListener(new ContactListListener() {

			public void contactListExported() {
				System.out.println("Contact list exported...");
			}

			public void contactListReceived(Collection users) {
				System.out.println("Contact list received...");
				for (Iterator iter = users.iterator(); iter.hasNext();) {
					LocalUser user = (LocalUser) iter.next();
					System.out.println("DisplayName: "+user.getDisplayName());
					System.out.println("EmailAddress: "+user.getEmailAddress());
					System.out.println("IsBlocked: "+user.isBlocked());
				}
			}
			
		});
		session.getLoginService().addLoginListener(new LoginListener.Stub() {
			
			public void loginOK() throws GGException {
				System.out.println("Login OK.");

				try {
					FileInputStream fis = new FileInputStream(new File("c:\\kontakty.txt"));
					session.getContactListService().exportContactList(fis);
				} catch (FileNotFoundException ex) {
					ex.printStackTrace();
				}
				
			}
			
			public void loginFailed(LoginFailedEvent loginFailedEvent) throws GGException {
				String reasonString = null;
				if (loginFailedEvent.getReason() == LoginFailedEvent.INCORRECT_PASSWORD) {
					reasonString = "Incorrect Password";
				} else if (loginFailedEvent.getReason() == LoginFailedEvent.NEED_EMAIL_REASON) {
					reasonString = "Need Email";
				}
				System.out.println("Login Failed, reason: "+reasonString);
			}
			
			/**
			 * @see pl.mn.communicator.event.LoginListener.Stub#loggedOut()
			 */
			public void loggedOut() throws GGException {
				System.out.println("Logged out...");
				session.getConnectionService().disconnect();
			}
			
		});
		
		session.getPublicDirectoryService().addPublicDirListener(new PublicDirListener() {

			public void onPublicDirectoryRead(int queryID, PersonalInfo pubDirReply) {
				System.out.println("Got pubDir read reply");
				System.out.println("FirstName: "+pubDirReply.getFirstName());
				System.out.println("Surname: "+pubDirReply.getLastName());
			}

			public void onPublicDirectoryUpdated(int queryID) {
				System.out.println("Updated pubDir");
			}
			
			public void onPublicDirectorySearchReply(int queryID, PublicDirSearchReply publicDirSearchReply) {
				System.out.println("Got pubdir search results.");
				for (Iterator it = publicDirSearchReply.listResults(); it.hasNext();) {
					PublicDirSearchReply.Entry entry = (PublicDirSearchReply.Entry) it.next();
					System.out.println("FirstName: "+entry.getFirstName());
					System.out.println("City: "+entry.getCity());
				}
			}
			
		});
		
		final IServer server = session.getConnectionService().lookupServer(loginContext.getUin());
		session.getConnectionService().connect(server);
		;
		;
		session.getConnectionService().disconnect();
		System.out.println("Bye.");
	}
	
}
