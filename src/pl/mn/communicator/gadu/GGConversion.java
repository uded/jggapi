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
 * @version $Revision: 1.6 $
 * @author mnaglik
 */
final class GGConversion {
	/**
	 * Prywatny konstruktor.
	 */
	private GGConversion() {
	}

	/**
	 * Zamien tabice bajtów na integer.
	 * @param buf tablica bajtów
	 * @return int
	 */
	public static int byteToInt(byte[] buf) {
		return byteToInt(buf, 0);
	}

	/**
	 * Zamien tablice bajtów na integer zaczynaj±c od pozycji start.
	 * @param buf tablica bajtów
	 * @param start pozycja od której tablica jest czytana
	 * @return int
	 */
	public static int byteToInt(byte[] buf, int start) {
		int i = 0;
		int pos = start;

		int tmp, plus = 0;
		tmp = unsignedByteToInt(buf[pos++]) << 0;
		i += tmp;
		tmp = unsignedByteToInt(buf[pos++]) << 8;
		i += tmp;
		tmp = unsignedByteToInt(buf[pos++]) << 16;
		i += tmp;
		tmp = unsignedByteToInt(buf[pos++]) << 24;
		i += tmp;

		return i;
	}

	/**
	 * 
	 * @param i
	 * @return
	 */
	public static int unsignedByteToInt(byte i) {
		if (i < 0)
			return (i & 0x7F) + 0x80;
		else
			return i;
	}

	public static byte[] intToByte(int buf) {
		byte [] toSend = new byte[4];
		
		toSend[3] = (byte) (buf >> 24 & 0xFF);
		toSend[2] = (byte) (buf >> 16 & 0xFF);
		toSend[1] = (byte) (buf >> 8 & 0xFF);
		toSend[0] = (byte) (buf & 0xFF);
		
		return toSend;
	}

	public static long unsignedIntToLong(int i) {
		long plus = 0;
		plus -= (i & 0x80000000);
		i &= 0x7FFFFFFF;
		return (long) (i + plus);
	}
}
