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

import pl.mn.communicator.AbstractUser;


/**
 * U¿ytkownik gg.
 * @version $Revision: 1.11 $
 * @author mnaglik
 */
public class User extends AbstractUser {
    private static Logger logger = Logger.getLogger(User.class);

    /**
     * Tworz u¿ytkownika na podstawie numeru i nazwy.
     * @param number numer u¿ytkownika
     */
    public User(int number) {
        super(number);
    }
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "numer: "+number;
	}
}
