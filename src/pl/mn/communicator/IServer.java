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

/**
 * Klasa reprezentuj±ca serwer rozmów.
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @version $Id: IServer.java,v 1.8 2004-10-26 23:56:40 winnetou25 Exp $
 */
public interface IServer {
    /**
     * Zwróæ adres serwera rozmów.
     * @return String
     */
    String getAddress();

    /**
     * Zwróæ port serwera rozmów.
     * @return int
     */
    int getPort();

    /**
     * @param address adres serwera
     */
    void setAddress(String address);

    /**
     * @param port port serwera
     */
    void setPort(int port);
}
