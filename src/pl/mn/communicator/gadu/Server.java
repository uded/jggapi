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

import pl.mn.communicator.AbstractServer;
import pl.mn.communicator.ILocalUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URL;

import java.util.StringTokenizer;


/**
 * Klasa z danymi dotycz±cymi serwera gg.
 * @version $Revision: 1.12 $
 * @author mnaglik
 */
public final class Server extends AbstractServer {
    private static Logger logger = Logger.getLogger(Server.class);

    /**
     * Twórz obiekt serwera.
     * @param address adres serwera
     * @param port port serwera
     */
    public Server(String address, int port) {
        super(address, port);
    }

    /**
     * Pobierz serwera gg ze strony www udostêpnionej na serwerze www gg.
     * @param user u¿ytkownik (potrzebny do stworzenia adresu)
     * @return Server serwer
     * @throws IOException b³±d pobierania domy¶lnego serwera
     */
    public static Server getDefaultServer(ILocalUser user)
        throws IOException {
        URL url = new URL(
                "http://appmsg.gadu-gadu.pl/appsvc/appmsg.asp?fmnumber="
                + user.getUserNo());

        InputStream is = url.openStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(is));

        String line = in.readLine();
        is.close();
        in.close();

        return parseAddress(line);
    }

    /**
     * Parsuj adres serwera.
     * @param line linia do parsowania
     * @return Server
     */
    private static Server parseAddress(String line) {
        final int nrOfTokens = 3;
        StringTokenizer token = new StringTokenizer(line);

        for (int i = 0; i < nrOfTokens; i++) {
            token.nextToken();
        }
        StringTokenizer token1 = new StringTokenizer(token.nextToken(), ":");

        return new Server(token1.nextToken(),
            Integer.parseInt(token1.nextToken()));
    }
}
