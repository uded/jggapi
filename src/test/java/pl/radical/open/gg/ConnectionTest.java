package pl.radical.open.gg;

import pl.radical.open.gg.event.ConnectionListener;
import pl.radical.open.gg.event.LoginFailedEvent;
import pl.radical.open.gg.event.LoginListener;

import java.util.concurrent.CountDownLatch;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionTest {
	private final Logger log = LoggerFactory.getLogger(getClass().getName());

	static boolean asyncOp = false;

	private final LoginContext loginContext1 = new LoginContext(20239471, "RadicalEntropy");
	private final LoginContext loginContext2 = new LoginContext(20241237, "RadicalTest");

	static ISession session1;
	static ISession session2;

	CountDownLatch connectLatch = new CountDownLatch(2);

	@Test(timeout = 1000 * 30)
	public void connectionTest() {
		log.info("Executing connectionTest() method");

		ConnectUser cu;

		cu = new ConnectUser(loginContext1);
		final Thread t1 = new Thread(cu);
		t1.run();
		session1 = cu.getSession();

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
						log.info("Connection established for user {}." + loginContext.getUin());
						connectLatch.countDown();
						super.connectionEstablished();
					}

					@Override
					public void connectionClosed() throws GGException {
						log.info("Connection closed for user {}." + loginContext.getUin());
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

	@Test(timeout = 1000 * 60)
	public void loginTest() throws GGException, InterruptedException {
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
				connectLatch.await();

				loginService = session.getLoginService();
				loginService.addLoginListener(new LoginListener.Stub() {
					@Override
					public void loginOK() throws GGException {
						log.info("Login for user {} OK", loginContext.getUin());
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
