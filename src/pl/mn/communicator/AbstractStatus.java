package pl.mn.communicator;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Klasa reprezentuj¹ca status u¿ytkownika.
 * Dodatkowe statusy specyficzne dla serwerów rozmów
 * mog¹ zostaæ dodane w podklasach.
 * 
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
