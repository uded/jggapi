package pl.radical.open.gg;

import static org.junit.Assert.assertEquals;

import pl.radical.open.gg.event.ConnectionListener;
import pl.radical.open.gg.event.ContactListListener;
import pl.radical.open.gg.event.LoginFailedEvent;
import pl.radical.open.gg.event.LoginListener;

import java.util.Collection;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionTest {
	private final Logger log = LoggerFactory.getLogger(getClass().getName());

	static boolean asyncOp = false;

	private static ISession session1;
	private static ISession session2;

	@Test(timeout = 1000 * 30)
	public void connectionTest() throws GGException, InterruptedException {
		log.info("Executing connectionTest() method");

		session1 = connectUser(SessionFactory.createSession(), 20239471, "RadicalEntropy");
		assertEquals(true, session1.getConnectionService().isConnected());

		session2 = connectUser(SessionFactory.createSession(), 20241237, "RadicalTest");
		assertEquals(true, session2.getConnectionService().isConnected());
	}

	// @Test
	public void loginTest() throws GGException, InterruptedException {
		ILoginService loginService;

		loginService = session1.getLoginService();
		loginService.addLoginListener(new LoginListener.Stub() {
			@Override
			public void loginOK() throws GGException {
				log.info("Login for user 1 OK");
				session1.getPresenceService().setStatus(new LocalStatus(StatusType.ONLINE_WITH_DESCRIPTION, "Jestem testowy"));

				final IContactListService cls = session1.getContactListService();
				cls.addContactListListener(new ContactListListener.Stub() {
					@Override
					public void contactListReceived(final Collection<LocalUser> users) {
						for (final LocalUser localUser : users) {
							log.info("Otrzymałem użytkownika: " + localUser.getUin());
						}
					}
				});
				cls.importContactList();
			}

			@Override
			public void loginFailed(final LoginFailedEvent loginFailedEvent) {
				log.error("Login failed!");
			}
		});
		log.debug("Loging in user1");
		loginService.login(new LoginContext(20239471, "RadicalEntropy"));

		Thread.sleep(45000);
		assertEquals(true, loginService.isLoggedIn());

		if (loginService.isLoggedIn()) {
			loginService.logout();
		}

		// log.debug("Loging in user2");
		// loginService = session2.getLoginService();
		// loginService.addLoginListener(new LoginListener.Stub() {
		// @Override
		// public void loginOK() throws GGException {
		// log.info("Login for user 2 OK");
		// session1.getPresenceService().setStatus(new LocalStatus(StatusType.ONLINE_WITH_DESCRIPTION,
		// "Jestem testowy"));
		// }
		//
		// @Override
		// public void loginFailed(final LoginFailedEvent loginFailedEvent) {
		// log.error("Login failed!");
		// return;
		// }
		// });
		// loginService.login(new LoginContext(20241237, "RadicalTest"));
		//
		// Thread.sleep(10000);
		// assertEquals(true, loginService.isLoggedIn());
		//
		// if (loginService.isLoggedIn()) {
		// loginService.logout();
		// }
	}

	private ISession connectUser(final ISession session, final int uid, final String password) throws GGException, InterruptedException {
		final LoginContext loginContext = new LoginContext(uid, password);

		session.getConnectionService().addConnectionListener(new ConnectionListener.Stub() {
			@Override
			public void connectionEstablished() throws GGException {
				log.info("Connection established.");
				ConnectionTest.asyncOp = true;
			}
		});

		final IConnectionService connectionService = session.getConnectionService();
		final IServer[] server = connectionService.lookupServer(loginContext.getUin());
		connectionService.connect(server);

		while (!asyncOp) {
			Thread.sleep(100);
		}
		asyncOp = false;
		return session;
	}
}
