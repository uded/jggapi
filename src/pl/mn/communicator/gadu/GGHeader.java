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
 * Naglowek wiadomosci gg.
 * @version $Revision: 1.5 $
 * @author mnaglik
 */
class GGHeader {
	private byte[] data;
	private int type;
	private int length;

	/**
	 * Constructor for Header.
	 * @param data dane naglowka
	 */
	public GGHeader(byte[] data) {
		this.type = GGConversion.byteToInt(data, 0);
		this.length = GGConversion.byteToInt(data, 4);
	}

	/**
	 * Returns the length.
	 * @return int
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Returns the type.
	 * @return int
	 */
	public int getType() {
		return type;
	}
}
