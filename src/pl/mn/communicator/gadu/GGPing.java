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
 * Pakiet wychodz±cy typu ping, okresowo wysy³any do serwera.
 * @version $Revision: 1.5 $
 * @author mnaglik
 */
final class GGPing implements GGOutgoingPackage {
	private byte[] data;
	private static GGPing ggPing = new GGPing();

	/**
	 * Prywatny konstruktor.
	 */
	private GGPing() {
	}
	
	/**
	 * @see pl.mn.communicator.gadu.GGOutgoingPackage#getHeader()
	 */
	public int getHeader() {
		return 0x08;
	}

	/**
	 * @see pl.mn.communicator.gadu.GGOutgoingPackage#getLength()
	 */
	public int getLength() {
		return 0x00;
	}

	/**
	 * @see pl.mn.communicator.gadu.GGOutgoingPackage#getContents()
	 */
	public byte[] getContents() {
		return new byte[0];
	}

	/**
	 * Pobierz instancje tego obiektu.
	 * @return instancja <code>GGPing</code>
	 */
	public static GGPing getPing() {
		return ggPing;
	}
}
