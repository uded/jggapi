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
package pl.mn.communicator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @version $Id: Server.java,v 1.3 2004-12-11 17:22:33 winnetou25 Exp $* @version $Revision: 1.3 $
 */
public final class Server implements IServer {
	
    private static Log logger = LogFactory.getLog(Server.class);

    protected String m_address;

    /**
     * Numer portu serwera
     */
    protected int m_port;

    /**
     * @param address adres serwera
     * @param port port serwera
     */
    public Server(String address, int port) {
        m_address = address;
        m_port = port;
    }

    
    /**
     * @return String
     */
    public String getAddress() {
        return m_address;
    }

    /**
     *  @return int
     */
    public int getPort() {
        return m_port;
    }
    
    /**
     * @param address adres serwera
     */
    public void setAddress(String address) {
        m_address = address;
    }

    /**
     * @param port port serwera
     */
    public void setPort(int port) {
        m_port = port;
    }    

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "[" + m_address + ":" + m_port + "]";
    }

    /**
     * @return Server serwer
     */
    public static Server getDefaultServer(LoginContext loginContext) throws GGException {
    	try {
        	URL url = new URL("http://appmsg.gadu-gadu.pl/appsvc/appmsg.asp?fmnumber="+ loginContext.getUin());

            InputStream is = url.openStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(is));

            String line = in.readLine();
            is.close();
            in.close();

            return parseAddress(line);
    	} catch (IOException ex) {
    		throw new GGException("Unable to get default server: "+loginContext, ex);
    	}
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

        return new Server(token1.nextToken(), Integer.parseInt(token1.nextToken()));
    }
    
}
