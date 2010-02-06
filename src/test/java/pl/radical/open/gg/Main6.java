package pl.radical.open.gg;
import pl.radical.open.gg.GGException;
import pl.radical.open.gg.IServer;
import pl.radical.open.gg.ISession;
import pl.radical.open.gg.LocalUser;
import pl.radical.open.gg.LoginContext;
import pl.radical.open.gg.PersonalInfo;
import pl.radical.open.gg.PublicDirSearchReply;
import pl.radical.open.gg.SessionFactory;
import pl.radical.open.gg.event.ConnectionListener;
import pl.radical.open.gg.event.ContactListListener;
import pl.radical.open.gg.event.LoginFailedEvent;
import pl.radical.open.gg.event.LoginListener;
import pl.radical.open.gg.event.PublicDirListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

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

	public static void main(final String args[]) throws IOException, GGException {

		final ISession session = SessionFactory.createSession();

		final LoginContext loginContext = new LoginContext(1038285, "test");

		session.getConnectionService().addConnectionListener(new ConnectionListener.Stub() {

			@Override
			public void connectionEstablished() throws GGException {
				System.out.println("Connection established.");
				session.getLoginService().login(loginContext);
			}

			@Override
			public void connectionClosed() {
				System.out.println("Connection closed.");
			}

			@Override
			public void connectionError(final Exception ex) throws GGException {
				System.out.println("Connection Error: "+ex.getMessage());
				session.getConnectionService().disconnect();
			}

		});

		session.getContactListService().addContactListListener(new ContactListListener() {

			public void contactListExported() {
				System.out.println("Contact list exported...");
			}

			public void contactListReceived(final Collection<LocalUser> users) {
				System.out.println("Contact list received...");
				for (final LocalUser user : users) {
					System.out.println("DisplayName: "+user.getDisplayName());
					System.out.println("EmailAddress: "+user.getEmailAddress());
					System.out.println("IsBlocked: "+user.isBlocked());
				}
			}

		});
		session.getLoginService().addLoginListener(new LoginListener.Stub() {

			@Override
			public void loginOK() throws GGException {
				System.out.println("Login OK.");

				try {
					final FileInputStream fis = new FileInputStream(new File("c:\\kontakty.txt"));
					session.getContactListService().exportContactList(fis);
				} catch (final FileNotFoundException ex) {
					ex.printStackTrace();
				}

			}

			@Override
			public void loginFailed(final LoginFailedEvent loginFailedEvent) throws GGException {
				String reasonString = null;
				if (loginFailedEvent.getReason() == LoginFailedEvent.INCORRECT_PASSWORD) {
					reasonString = "Incorrect Password";
				} else if (loginFailedEvent.getReason() == LoginFailedEvent.NEED_EMAIL_REASON) {
					reasonString = "Need Email";
				}
				System.out.println("Login Failed, reason: "+reasonString);
			}

			/**
			 * @see pl.radical.open.gg.event.LoginListener.Stub#loggedOut()
			 */
			@Override
			public void loggedOut() throws GGException {
				System.out.println("Logged out...");
				session.getConnectionService().disconnect();
			}

		});

		session.getPublicDirectoryService().addPublicDirListener(new PublicDirListener() {

			public void onPublicDirectoryRead(final int queryID, final PersonalInfo pubDirReply) {
				System.out.println("Got pubDir read reply");
				System.out.println("FirstName: "+pubDirReply.getFirstName());
				System.out.println("Surname: "+pubDirReply.getLastName());
			}

			public void onPublicDirectoryUpdated(final int queryID) {
				System.out.println("Updated pubDir");
			}

			public void onPublicDirectorySearchReply(final int queryID, final PublicDirSearchReply publicDirSearchReply) {
				System.out.println("Got pubdir search results.");
				for (final Iterator<PublicDirSearchReply.Entry> it = publicDirSearchReply.listResults(); it.hasNext();) {
					final PublicDirSearchReply.Entry entry = it.next();
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
