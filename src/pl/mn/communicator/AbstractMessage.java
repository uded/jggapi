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
 * Wiadomo¶æ do serwera rozmów.
 * 
 * @version $Revision: 1.6 $
 * @author mnaglik
 */
public abstract class AbstractMessage implements IMessage {
	private static Logger logger = Logger.getLogger(AbstractMessage.class);
	/**
	 * Nr adresata wiadomo¶ci
	 */
	protected int user;
	
	/**
	 * Treœæ wiadomo¶ci
	 */
	protected String text;

	/**
	 * Tworzy wiadomo¶æ do konkretnego u¿ytkownika.
	 * 
	 * @param toUser nr u¿ytkownika do którego wysy³amy
	 * @param text wiadomo¶æ tekstowa
	 */
	public AbstractMessage(int toUser, String text) {
		this.user = toUser;
		this.text = text;
	}

	/**
	 * Pobierz u¿ytkownika do którego jest wiadomoœæ
	 * 
	 * @return User 
	 */
	public int getUser() {
		return user;
	}

	/**
	 * Pobierz treœæ wiadomoœæi
	 * 
	 * @return String
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @param user
	 */
	public void setUser(int user) {
		this.user = user;
	}

}
