package pl.radical.open.gg;

import static org.junit.Assert.assertEquals;

import pl.radical.open.gg.event.ConnectionListener;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionTest {
	private final Logger log = LoggerFactory.getLogger(getClass().getName());

	@Test
	public void connectionTest() throws GGException, InterruptedException {
		log.info("Executing connectionTest() method");

		final ISession session = SessionFactory.createSession();
		final LoginContext loginContext = new LoginContext(20239471, "RadicalE");

		session.getConnectionService().addConnectionListener(new ConnectionListener.Stub() {
			@Override
			public void connectionEstablished() throws GGException {
				log.info("Connection established.");
			}
		});

		final IConnectionService connectionService = session.getConnectionService();
		final IServer server = connectionService.lookupServer(loginContext.getUin());
		connectionService.connect(server);

		Thread.sleep(500);

		assertEquals(true, connectionService.isConnected());

		connectionService.disconnect();
	}
}
