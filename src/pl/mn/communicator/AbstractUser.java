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

import org.apache.log4j.Logger;


/**
 * U¿ytkownik serwera rozmów.
 * @version $Revision: 1.10 $
 * @author mnaglik
 */
public class AbstractUser implements IUser {
    private static Logger logger = Logger.getLogger(AbstractUser.class);
    protected int number;
    protected boolean onLine;
    protected String name;

    /**
     * Tworz u¿ytkownika.
     * @param number numer u¿ykownika
     */
    public AbstractUser(int number) {
        this.number = number;
    }

    /**
     * Zwróæ numer u¿ytkownika.
     * @return int
     */
    public int getNumber() {
        return number;
    }

    /**
     * Zmieñ numer u¿ytkownika.
     * @param number nowy numer u¿ytkownika
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object o) {
        return (o instanceof IUser) && (number == ((IUser) o).getNumber());
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return number;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "[" + name + ":" + number + ":" + onLine + "]";
    }
}
