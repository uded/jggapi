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

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Klasa reprezentuj±ca status u¿ytkownika.
 * Dodatkowe statusy specyficzne dla serwerów rozmów
 * mog¹ zostaæ dodane w podklasach.
 * 
 * @version $Revision: 1.6 $
 * @author mnaglik
 */
public abstract class AbstractStatus implements IStatus {
	private static Logger logger = Logger.getLogger(AbstractStatus.class);

	/**
	 * Aktualny status 
	 */
	protected int status; 

	public AbstractStatus(int status) {
		this.status = status;
	}
	
	/**
	 * Pobierz aktualny status
	 * 
	 * @return int
	 */
	public int getStatus() {
		return status;
	}
	
	/**
	 * Ustaw aktualny status
	 * 
	 * @return int
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * Pobierz dostêpne statusy.
	 * Zwraca mapê dostêpnych statusów.
	 * Kluczem jest Integer z nr statusu,
	 * a wartoscia String z nazw¹ statusu
	 * 
	 * @return Map
	 */
	public Map getAvaiableStatuses() {
		HashMap map = new HashMap();

		map.put(new Integer(IStatus.ON_LINE),"On Line");
		map.put(new Integer(IStatus.OFF_LINE),"Off Line");
	
		return map;
	}
}
