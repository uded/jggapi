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

import pl.mn.communicator.logger.Logger;


/**
 * Klasa reprezentuj±ca serwer rozmów.
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @version $Id: AbstractServer.java,v 1.12 2004-11-11 18:38:49 winnetou25 Exp $
 */
public abstract class AbstractServer implements IServer {
	
    private static Logger logger = Logger.getLogger(AbstractServer.class);

    /**
     * Adres ip, lub tekstowy serwera rozmów
     */
    protected String address;

    /**
     * Numer portu serwera
     */
    protected int port;

    /**
     * Utworz adres serwera rozmów.
     * @param address adres serwera
     * @param port post serwera
     */
    public AbstractServer(String address, int port) {
        this.address = address;
        this.port = port;
    }

    /**
     * Zwróæ adres serwera rozmów.
     * @return String
     */
    public String getAddress() {
        return address;
    }

    /**
     * Zwróæ port serwera rozmów.
     *  @return int
     */
    public int getPort() {
        return port;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "[" + this.address + "-" + this.port + "]";
    }

    /**
     * @param address adres serwera
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @param port port serwera
     */
    public void setPort(int port) {
        this.port = port;
    }
    
}
