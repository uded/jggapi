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

/**
 * Pakiet wychodz±cy gg.
 * @version $Revision: 1.5 $
 * @author mnaglik
 */
interface GGOutgoingPackage {
	/**
	 * Zwróæ nag³ówek pakietu
	 * @return int
	 */
	int getHeader();

	/**
	 * Zwróæ d³ugo¶æ pakietu
	 * D³ugo¶æ bez nag³ówka i inta zawieraj±cego d³ugo¶æ ca³ego pakietu.
	 * @return int
	 */
	int getLength();

	/**
	 * Zwróæ bajty z zawarto¶ci± pakietu do wys³ania
	 * @return byte[] zawarto¶æ pakietu
	 */
	byte [] getContents();
}
