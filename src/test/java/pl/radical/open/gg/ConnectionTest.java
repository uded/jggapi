package pl.radical.open.gg;

import static org.junit.Assert.assertEquals;

import pl.radical.open.gg.event.ConnectionListener;
import pl.radical.open.gg.event.LoginFailedEvent;
import pl.radical.open.gg.event.LoginListener;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionTest {
	private final Logger log = LoggerFactory.getLogger(getClass().getName());

	public static boolean asyncOp = false;

	private static ISession session1;
	private static ISession session2;

	@Test
	public void connectionTest() throws GGException, InterruptedException {
		log.info("Executing connectionTest() method");

		session1 = connectUser(SessionFactory.createSession(), 20239471, "RadicalEntropy");
		assertEquals(true, session1.getConnectionService().isConnected());

		session2 = connectUser(SessionFactory.createSession(), 20241237, "Radical Test");
		assertEquals(true, session2.getConnectionService().isConnected());
	}

	@Test
	public void loginTest() throws InterruptedException {
		ILoginService loginService;

		log.debug("Loging in user1");
		loginService = session1.getLoginService();
		loginService.addLoginListener(new LoginListener.Stub() {
			@Override
			public void loginOK() {
				log.info("Login for user 1 OK");
			}

			@Override
			public void loginFailed(final LoginFailedEvent loginFailedEvent) {
				log.error("Login failed!");
			}
		});

		log.debug("Loging in user2");
		loginService= session2.getLoginService();
		loginService.addLoginListener(new LoginListener.Stub() {
			@Override
			public void loginOK() {
				log.info("Login for user 2 OK");
				asyncOp = true;
			}

			@Override
			public void loginFailed(final LoginFailedEvent loginFailedEvent) {
				log.error("Login failed!");
			}
		});

		while(!asyncOp) {
			Thread.sleep(100);
		}
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
		final IServer server = connectionService.lookupServer(loginContext.getUin());
		connectionService.connect(server);

		while (!asyncOp) {
			Thread.sleep(100);
		}
		asyncOp = false;
		return session;
	}
}
