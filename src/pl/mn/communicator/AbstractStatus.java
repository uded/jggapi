package pl.mn.communicator;

import java.util.HashMap;
import java.util.Map;

/**
 * Klasa reprezentuj¹ca status u¿ytkownika.
 * Dodatkowe statusy specyficzne dla serwerów rozmów
 * mog¹ zostaæ dodane w podklasach.
 * 
 * @author mnaglik
 */
public abstract class AbstractStatus {
	/**
	 * Status on-line
	 */
	public final static int ON_LINE = 1;
	/**
	 * Status off-line
	 */
	public final static int OFF_LINE = 0;
	/**
	 * Aktualny status 
	 */
	protected int actualStatus; 

	public AbstractStatus(int status) {
		this.actualStatus = status;
	}
	
	/**
	 * Pobierz aktualny status
	 * 
	 * @return int
	 */
	public int getStatus() {
		return actualStatus;
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

		map.put(new Integer(AbstractStatus.ON_LINE),"On Line");
		map.put(new Integer(AbstractStatus.OFF_LINE),"Off Line");
	
		return map;
	}
}
