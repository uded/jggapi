package pl.mn.communicator.gadu.test;

import java.io.IOException;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import pl.mn.communicator.Connection;
import pl.mn.communicator.ConnectionListener;
import pl.mn.communicator.ISession;
import pl.mn.communicator.LoginContext;
import pl.mn.communicator.Server;


/**
 * @author mnaglik
 */
public class ConnectionTest extends TestCase {
    private static Logger log = Logger.getLogger(ConnectionTest.class);
    ISession connection;

    public void testConnect() throws IOException {
    }

    public void testSendMessage() throws IOException {
        LoginContext user = new LoginContext(1336843, "dupadupa");
        Server server = Server.getDefaultServer(user);

        log.info(server.getAddress() + ":" + server.getPort());

        connection = new Connection(server, user);
        connection.addConnectionListener(new ConnectionListener() {
                public void connectionEstablished() {
                    log.info("connection established");
                }

                public void disconnected() {
                    log.info("disconnected");
                }

                public void connectionError(String error) {
                    log.info("connection error: " + error);
                }
            });
        connection.connect();
    }

    public void testDisconnect() throws IOException {
    }
}
