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
package pl.mn.communicator.gadu;

import org.apache.log4j.Logger;

import pl.mn.communicator.ILocalUser;
import pl.mn.communicator.IMessage;
import pl.mn.communicator.IServer;
import pl.mn.communicator.IStatus;
import pl.mn.communicator.IUser;
import pl.mn.communicator.gadu.util.Util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

import java.net.Socket;

import java.util.Collection;


/**
 * Implementacja klienta gg.<BR>
 * S³u¿y do tworzenia po³±czenia.
 * <BR><BR>
 * <i>Przyk³ad u¿ycia:</i><BR><BR>
 * <code>
 * LocalUser user = new LocalUser(1234,"password");<BR>
 * Server server = ServerAddress.getHost(user);<BR>
 * Server s = new Server(server.user);<BR>
 * Connection c = new Connection();<BR><BR>
 * try{<BR>
 * &nbsp; &nbsp; c.connect();<BR>
 * }catch(Exception e){<BR>
 * &nbsp; &nbsp; ...<BR>
 * }
 * </code>
 * @version $Revision: 1.17 $
 * @author mnaglik
 */
public final class Connection extends pl.mn.communicator.AbstractConnection {
    private static Logger logger = Logger.getLogger(Connection.class);
    private IServer server;
    private ILocalUser localUser;
    private ConnectionThread connectionThread;
    private boolean isConnected = false;

    /**
     * Konstruktor polaczenia z serwerem gg.
     * @param server serwer gg.
     * @param localUser uzytkownik gg.
     */
    public Connection(IServer server, ILocalUser localUser) {
        this.server = server;
        this.localUser = localUser;
        connectionThread = new ConnectionThread();
    }

    /**
     * Pod³±cz sie do serwera gg.<BR>
     * Próbuje ³±czyæ siê z serwerem gg, na podstawie danych<BR>
     * z konstruktora.<BR>
     * W wypadku niepowodzenie wyrzuca odpowiednie wyj±tki
     * @throws IOException nie powiodla siê próba po³±czenia
     * - nie ma po³±czenia sieciowego?
     */
    public void connect() throws IOException {
        connectionThread.openConnection();
        isConnected = true;
    }

    /**
     * Zamyka po³±czenie z serwerem gg.
     * @throws IOException b³±d przy zamykaniu po³±czenia
     */
    public void disconnect() throws IOException {
        changeStatus(new Status(Status.OFF_LINE));
        connectionThread.closeConnection();
        isConnected = false;
    }

    /**
     * Wy¶lij wiadomo¶æ do serwera gg.
     * @see Message
     * @param message wiadomo¶æ do wys³ania.
     * @throws IOException b³±d wysy³ania wiadomo¶ci
     */
    public void sendMessage(IMessage message) throws IOException {
        if (isConnected) {
            GGSendMsg messageOut = new GGSendMsg(message);
            connectionThread.sendPackage(messageOut);
        }
    }

    /**
     * Zmien aktualny status.<BR>
     * @param status - kolejny status
     * @throws IOException {@inheritDoc}
     */
    public void changeStatus(IStatus status) throws IOException {
        if (isConnected) {
            GGStatus newStatus = new GGStatus(status.getStatus(), "estem");
            connectionThread.sendPackage(newStatus);
        }
    }

    /**
     * @see pl.mn.communicator.IConnection
     * #sendMonitoredUserList(java.util.Collection)
     */
    public void sendMonitoredUserList(Collection userList)
        throws IOException {
        // TODO Auto-generated method stub
    }

    /**
     * @see pl.mn.communicator.IConnection
     * #addMonitoredUser(pl.mn.communicator.IUser)
     */
    public void addMonitoredUser(IUser user) throws IOException {
        // TODO Auto-generated method stub
    }

    /**
     * @see pl.mn.communicator.IConnection
     * #removeMonitoredUser(pl.mn.communicator.IUser)
     */
    public void removeMonitoredUser(IUser user) throws IOException {
        // TODO Auto-generated method stub
    }

    /**
     * Watek polaczenia
     * @author mnaglik
     */
    private class ConnectionThread implements Runnable {
        private static final int HEADER_LENGTH = 8;
        private static final int PING_COUNT = 200;
        private static final int THREAD_SLEEP_TIME = 100;
        private static final int GG_PACKAGE_WELCOME = 1;
        private static final int GG_PACKAGE_LOGIN_OK = 3;
        private static final int GG_PACKAGE_LOGIN_ERROR = 9;
        private static final int GG_PACKAGE_MESSAGE = 10;
        private static final int GG_PACKAGE_CONNECTION_ERROR = 11;
        private Socket socket;
        private Thread thread;
        private BufferedInputStream dataInput;
        private BufferedOutputStream dataOutput;
        private int pingTimeCount = 0;

        /**
         * @see java.lang.Runnable#run()
         */
        public void run() {
            logger.debug("Run...");

            byte[] headerData = new byte[HEADER_LENGTH];

            try {
                while (thread != null) {
                    if (dataInput.available() > 0) {
                        dataInput.read(headerData);

                        decodePocket(new GGHeader(headerData));
                    } else {
                        pingTimeCount++;

                        if (pingTimeCount > PING_COUNT) {
                            // TODO poprawne wysylanie pinga
                            //sendPackage(GGPing.getPing());
                            logger.debug("Ping...");
                            pingTimeCount = 0;
                        }
                    }

                    Thread.sleep(THREAD_SLEEP_TIME);
                }
            } catch (IOException e) {
                logger.error(e, e);
            } catch (InterruptedException e) {
                logger.error(e, e);
            } finally {
                logger.debug("Doing finally");

                try {
                    closeConnection();
                } catch (IOException e) {
                    logger.error(e);
                }
            }

            logger.debug("Run ended.");
        }

        /**
         * Dekoduje pakiet i wywoluje odpowiednia metode
         * z konkretnego listenera
         * @param ggHeader naglowek wiadomosci
         * @throws IOException b³±d dekodowania pakietu
         */
        private void decodePocket(GGHeader ggHeader) throws IOException {
            byte[] keyBytes = new byte[ggHeader.getLength()];
            dataInput.read(keyBytes);
            logger.debug("Pakiet przychodzacy: "
                    + Util.bytesToString(keyBytes));

            switch (ggHeader.getType()) {
            case GG_PACKAGE_WELCOME:

                GGWelcome ggWelcome = new GGWelcome(keyBytes);
                GGLogin ggLogin = new GGLogin(socket.getLocalAddress()
                                                    .getAddress(),
                        socket.getLocalPort(), localUser, ggWelcome);
                sendPackage(ggLogin);

                break;

            case GG_PACKAGE_LOGIN_OK:

                if (connectionListener != null) {
                    connectionListener.connectionEstablished();
                }

                logger.debug("Login OK");
                changeStatus(new Status(Status.ON_LINE));

                break;

            case GG_PACKAGE_LOGIN_ERROR:

                if (connectionListener != null) {
                    connectionListener.connectionError("9");
                }

                logger.debug("Loggin ERROR");
                this.closeConnection();

                break;

            case GG_PACKAGE_MESSAGE:

                GGRecvMsg message = new GGRecvMsg(keyBytes);
                IMessage messageOut = new Message(message.getSender(),
                        message.getMessage());

                if (messageListener != null) {
                    messageListener.messageArrived(messageOut);
                }

                break;

            case GG_PACKAGE_CONNECTION_ERROR:

                if (connectionListener != null) {
                    connectionListener.connectionError("11");
                }

                logger.debug("Error logging 11");
                this.closeConnection();

                break;

            default:
                logger.debug("Unknown package");
            }
        }

        /**
         * @param outgoingPackage pakiet wychodz±cy
         */
        private synchronized void sendPackage(GGOutgoingPackage outgoingPackage) {
            int header = outgoingPackage.getHeader();
            int length = outgoingPackage.getLength();

            byte[] contents = outgoingPackage.getContents();

            try {
                dataOutput.write(GGConversion.intToByte(header));

                if (length > 0) {
                    dataOutput.write(GGConversion.intToByte(length));
                    dataOutput.write(contents);
                }

                dataOutput.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * Otworz polaczenie
         * @throws UnknownHostException
         * @throws IOException b³±d otwierania po³±czenia
         */
        private void openConnection() throws IOException {
            socket = new Socket(server.getAddress(), server.getPort());

            dataInput = new BufferedInputStream(socket.getInputStream());
            dataOutput = new BufferedOutputStream(socket.getOutputStream());

            thread = new Thread(this);
            thread.start();
        }

        /**
         * Zamknij polaczenie
         * @throws IOException b³±d zamykania po³±czenia
         */
        private void closeConnection() throws IOException {
            thread = null;
            dataInput = null;
            dataOutput = null;
            socket.close();
        }
    }
}
