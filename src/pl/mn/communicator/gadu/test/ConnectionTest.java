/*
 * Created on 2003-09-30
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package pl.mn.communicator.gadu.test;

import java.io.IOException;

import junit.framework.TestCase;
import pl.mn.communicator.gadu.Connection;
import pl.mn.communicator.gadu.LocalUser;
import pl.mn.communicator.gadu.Message;
import pl.mn.communicator.gadu.Server;

/**
 * @author mna
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ConnectionTest extends TestCase {
	Connection connection;


	public void testConnect() throws IOException {
	}

	public void testSendMessage() throws IOException {
		LocalUser user = new LocalUser(1336843,"dupadupa");
		Server server = Server.getDefaultServer(user);
		
		System.out.println(server.getAddress()+":"+server.getPort());
		
		connection = new Connection(server,user);
		connection.connect();
		connection.sendMessage(new Message(1755689,"Test"));
		connection.disconnect();
	}

	public void testDisconnect() throws IOException {
	}


}
