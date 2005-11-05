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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: Server.java,v 1.1 2005-11-05 23:34:52 winnetou25 Exp $
 */
public final class Server implements IServer {
	
    private final static Log LOGGER = LogFactory.getLog(Server.class);

    /** The server's address */
    protected String m_address = null;

    /** The server's port */
    protected int m_port = -1;

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
    
    public String toString() {
        return "[" + m_address + ":" + m_port + "]";
    }

}
