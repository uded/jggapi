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
 * Listener ze zdarzeniami po³¹czenia.
 * 
 * @version $Revision: 1.3 $
 * @author mnaglik
 */
public interface ConnectionListener {
	/**
	 * Po³¹czenie zosta³o pomyœlnie nawi¹zane.
	 */
	public void connectionEstablished();

	/**
	 * Roz³aczono z serwerem.<BR>
	 * Wywo³ywane podczas celowego roz³¹czania z serwerem.
	 */
	public void disconnected();

	/**
	 * Problem z po³¹czeniem.<BR>
	 * Wyst¹pi³ b³¹d w po³¹czeniu.
	 * 
	 * @param error tekstowy opis b³êdu zwi¹zanego z po³¹czeniem 
	 */
	public void connectionError(String error);
}
