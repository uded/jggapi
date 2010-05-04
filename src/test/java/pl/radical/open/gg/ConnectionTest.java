package pl.radical.open.gg;

import static pl.radical.open.gg.AlljGGapiTest.TEST_PASS_1;
import static pl.radical.open.gg.AlljGGapiTest.TEST_PASS_2;
import static pl.radical.open.gg.AlljGGapiTest.TEST_UIN_1;
import static pl.radical.open.gg.AlljGGapiTest.TEST_UIN_2;

import pl.radical.open.gg.event.ConnectionListener;
import pl.radical.open.gg.event.LoginFailedEvent;
import pl.radical.open.gg.event.LoginListener;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author <a href="mailto:lukasz@radical.com.pl">Łukasz Rżanek</a>
 */
public class ConnectionTest {
	private final Logger log = LoggerFactory.getLogger(getClass().getName());

	private final LoginContext loginContext1 = new LoginContext(TEST_UIN_1, TEST_PASS_1);
	private final LoginContext loginContext2 = new LoginContext(TEST_UIN_2, TEST_PASS_2);

	protected static ISession session1;
	protected static ISession session2;

	static final CountDownLatch CONNECT_LATCH = new CountDownLatch(2);
	static final CountDownLatch COMMUNICATION_LATCH = new CountDownLatch(2);

	public final void badConnectionTest() {
		final int uin = new Random().nextInt(15000000) + 5000000;
		final LoginContext loginContextBad = new LoginContext(uin, "pass");

		final ConnectUser cu = new ConnectUser(loginContextBad);
		final Thread badConnectT = new Thread(cu);
		badConnectT.run();

		final Thread badLoginT = new Thread(new LoginUser(cu.getSession(), loginContextBad));
		badLoginT.run();
	}

	@Test(timeout = 1000 * 30)
	public final void connectionTest() throws InterruptedException {
		log.info("Executing connectionTest() method");

		ConnectUser cu;

		cu = new ConnectUser(loginContext1);
		final Thread t1 = new Thread(cu);
		t1.run();
		session1 = cu.getSession();

		Thread.sleep(1000);

		cu = new ConnectUser(loginContext2);
		final Thread t2 = new Thread(cu);
		t2.run();
		session2 = cu.getSession();
	}

	private class ConnectUser implements Runnable {
		private ISession session;
		private final LoginContext loginContext;

		public ConnectUser(final LoginContext loginContext) {
			this.loginContext = loginContext;
		}

		@Override
		public void run() {
			try {
				session = SessionFactory.createSession();

				session.getConnectionService().addConnectionListener(new ConnectionListener.Stub() {
					@Override
					public void connectionEstablished() throws GGException {
						log.info("Connection established for user [{}].", loginContext.getUin());
						CONNECT_LATCH.countDown();
						super.connectionEstablished();
					}

					@Override
					public void connectionClosed() throws GGException {
						log.info("Connection closed for user [{}].", loginContext.getUin());
						super.connectionClosed();
					}

					@Override
					public void connectionError(final Exception ex) throws GGException {
						log.error("Connection error", ex);
						super.connectionError(ex);
					}
				});

				final IConnectionService connectionService = session.getConnectionService();
				final IServer[] server = connectionService.lookupServer(loginContext.getUin());

				log.info("Connecting user: {}", loginContext.getUin());
				connectionService.connect(server);
			} catch (final GGException e) {
				log.error("Connection failed", e);
			}
		}

		public ISession getSession() {
			return session;
		}
	}

	@Test(timeout = 1000 * 30)
	public final void loginTest() throws GGException, InterruptedException {
		log.info("Executing loginTest() method");

		final Thread t1 = new Thread(new LoginUser(session1, loginContext1));
		t1.run();

		final Thread t2 = new Thread(new LoginUser(session2, loginContext2));
		t2.run();
	}

	private class LoginUser implements Runnable {
		private ILoginService loginService;

		private final LoginContext loginContext;
		private final ISession session;

		public LoginUser(final ISession session, final LoginContext loginContext) {
			this.session = session;
			this.loginContext = loginContext;
		}

		@Override
		public void run() {
			try {
				CONNECT_LATCH.await();

				loginService = session.getLoginService();
				loginService.addLoginListener(new LoginListener.Stub() {
					@Override
					public void loginOK() throws GGException {
						log.info("Login for user {} OK", loginContext.getUin());
						COMMUNICATION_LATCH.countDown();
					}

					@Override
					public void loginFailed(final LoginFailedEvent loginFailedEvent) {
						log.error("Login failed!");
						System.exit(15);
					}
				});

				log.debug("Loging in user: {}", loginContext.getUin());
				loginService.login(loginContext);
			} catch (final Exception e) {
				log.error("Cannot login the user", e);
			}
		}
	}
}
