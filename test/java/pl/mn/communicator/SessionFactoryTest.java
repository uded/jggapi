/*
 * Created on 2004-12-18
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pl.mn.communicator;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pl.mn.communicator.event.ConnectionListener;
import pl.mn.communicator.event.SessionStateEvent;
import pl.mn.communicator.event.SessionStateListener;
import junit.framework.TestCase;

/**
 * @author Uzytkownik
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SessionFactoryTest extends TestCase {
	private final static Log logger = LogFactory.getLog(SessionFactoryTest.class);

	private Properties props;
	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		InputStream is = SessionFactoryTest.class.getResourceAsStream("config.properties");
		
		props = new Properties();
		props.load(is);

		is.close();
		
	}
	/*
	 * Class under test for ISession createSession(LoginContext)
	 */
	public void testCreateSessionLoginContext() throws GGException, InterruptedException {
		
		LoginContext loginContext = new LoginContext(Integer.parseInt(props.getProperty("uid")),props.getProperty("password"));
		ISession session = SessionFactory.createSession(loginContext);
		session.getConnectionService().addConnectionListener(new ConnectionListener(){

			public void connectionEstablished() {
				// TODO Auto-generated method stub
				logger.info("Connection established");
			}

			public void connectionClosed() {
				// TODO Auto-generated method stub
				logger.info("Connection closed");
			}

			public void connectionError(Exception e) {
				// TODO Auto-generated method stub
				logger.info("Connection error",e);
			}

			public void pongReceived() {
				// TODO Auto-generated method stub
				logger.info("Pong received");
			}});
		
		session.addSessionStateListener(new SessionStateListener(){

			public void sessionStateChanged(SessionStateEvent stateEvent) {
				logger.info("session changed " + stateEvent);
			}});
		
		session.getConnectionService().connect();

		Thread.sleep(10000);
		
		session.getConnectionService().disconnect();
		
		Thread.sleep(10000);
		
		logger.info("koniec");
	}

	/*
	 * Class under test for ISession createSession(LoginContext, String, short)
	 */
	public void testCreateSessionLoginContextStringshort() {
	}

	/*
	 * Class under test for ISession createSession(LoginContext, IServer)
	 */
	public void testCreateSessionLoginContextIServer() {
	}

}
