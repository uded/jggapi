/*
 * Copyright (c) 2003 Marcin Naglik (mnaglik@gazeta.pl)
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package pl.mn.communicator.gadu.test;

import java.io.IOException;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pl.mn.communicator.ISession;
import pl.mn.communicator.LoginContext;
import pl.mn.communicator.Server;
import pl.mn.communicator.event.ConnectionListener;

/**
 * @author mnaglik
 */
public class ConnectionTest extends TestCase {

	private static Log log = LogFactory.getLog(ConnectionTest.class);

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
