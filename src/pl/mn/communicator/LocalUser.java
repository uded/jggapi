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
 * U¿ytkownik lokalny gg.
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @version $Id: LocalUser.java,v 1.1 2004-10-27 00:51:54 winnetou25 Exp $
 */
public final class LocalUser extends AbstractLocalUser {
	
    private static Logger logger = Logger.getLogger(LocalUser.class);

    /**
     * Tworz u¿ytkownika lokalnego.
     * @param userNo numer u¿ytkownika
     * @param password has³o u¿ytkownika
     */
    public LocalUser(int userNo, String password) {
        super(userNo, password);
    }
    
}
