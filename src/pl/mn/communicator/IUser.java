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
 * U¿ytkownik serwera rozmów.
 * 
 * @version $Revision: 1.5 $
 * @author mnaglik
 */
public interface IUser {
	/**
	 * Zwróæ nick u¿ytkownika.
	 * 
	 * @return String
	 */
	public String getName();
	/**
	 * Zwróæ numer u¿ytkownika.
	 * 
	 * @return int
	 */
	public int getNumber();
	/**
	 * Zmieñ nick u¿ytkownika
	 * 
	 * @param name nowe nick u¿ytkownika
	 */
	public void setName(String name);
	/**
	 * Zmieñ numer u¿ytkownika.
	 * 
	 * @param number nowy numer u¿ytkownika
	 */
	public void setNumber(int number);
	/**
	 * Zwróæ status u¿ytkownika.
	 * 
	 * @return boolean status u¿ytkownika <code>true</code>- online
	 */
	public boolean isOnLine();
	/**
	 * Zmieñ status u¿ytkownika.
	 * 
	 * @param onLine nowy status u¿ytkownika
	 */
	public void setOnLine(boolean onLine);
}