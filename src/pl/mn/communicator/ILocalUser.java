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
 * U¿ytkownik lokalny klienta rozmów.<BR>
 * Obiekt reprezentuje lokalnego u¿ytkownika.
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @version $Id: ILocalUser.java,v 1.7 2004-10-26 23:56:40 winnetou25 Exp $
 */
public interface ILocalUser {
	
    /**
     * Pobierz has³o u¿ytkownika.<BR>
     * Has³o jest w postaci niezaszyfrowanej.
     * @return String password
     */
    String getPassword();

    /**
     * Pobierz nr u¿ytkownika.
     * @return int userNo
     */
    int getUserNo();

    /**
     * @param password has³o
     */
    void setPassword(String password);

    /**
     * @param userNo u¿ytkownik
     */
    void setUserNo(int userNo);
}
