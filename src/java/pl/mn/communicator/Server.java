/*
 * Copyright (c) 2003-2005 JGGApi Development Team. All Rights Reserved.
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
import java.net.URLConnection;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: Server.java,v 1.6 2004-12-20 00:15:52 winnetou25 Exp $
 */
public final class Server implements IServer {
	
    private static Log logger = LogFactory.getLog(Server.class);

    /** The server's address */
    protected String m_address;

    /** The server's port */
    protected int m_port;

    /**
     * @param address the server's address.
     * @param port the server's port.
     * @throws NullPointerException if the address is null.
     * @throws IllegalArgumentException if the port is not value between 0 and 65535.
     */
    public Server(String address, int port) {
    	if (address == null) throw new NullPointerException("address cannot be null");
    	if (port < 0 || port > 65535) throw new IllegalArgumentException("port cannot be less than 0 and grather than 65535");
        m_address = address;
        m_port = port;
    }

    
    public String getAddress() {
        return m_address;
    }

    public int getPort() {
        return m_port;
    }
    
    public void setAddress(String address) {
        m_address = address;
    }

    public void setPort(int port) {
        m_port = port;
    }    

    public String toString() {
        return "[" + m_address + ":" + m_port + "]";
    }

    public static Server getDefaultServer(LoginContext loginContext) throws GGException {
    	try {
        	URL url = new URL("http://appmsg.gadu-gadu.pl/appsvc/appmsg.asp?fmnumber="+ loginContext.getUin());

        	URLConnection con = url.openConnection();
        	con.setReadTimeout(120*1000);
        	con.setConnectTimeout(120*1000);
        	con.connect();
        	
        	InputStream is = con.getInputStream();
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
     * Parses the server's address.
     * @param line line to be parsed.
     * @return <code>Server</code> the server object. 
     */
    private static Server parseAddress(String line) {
        final int nrOfTokens = 3;
        StringTokenizer token = new StringTokenizer(line);

        for (int i = 0; i < nrOfTokens; i++) {
            token.nextToken();
        }
        StringTokenizer tokenizer = new StringTokenizer(token.nextToken(), ":");

        return new Server(tokenizer.nextToken(), Integer.parseInt(tokenizer.nextToken()));
    }
    
}
