package pl.mn.communicator;

import java.util.Map;

/**
 * Klasa reprezentuj¹ca status u¿ytkownika.
 * Dodatkowe statusy specyficzne dla serwerów rozmów
 * mog¹ zostaæ dodane w podklasach.
 * 
 * @version $Revision: 1.3 $
 * @author mnaglik
 */
public interface IStatus {
	/**
	 * Status on-line
	 */
	public final static int ON_LINE = 1;

	/**
	 * Status off-line
	 */
	public final static int OFF_LINE = 0;

	/**
	 * Pobierz aktualny status
	 * 
	 * @return int
	 */
	public int getStatus();
	/**
	 * Ustaw aktualny status
	 * 
	 * @return int
	 */
	public void setStatus(int status);
	/**
	 * Pobierz dostêpne statusy.
	 * Zwraca mapê dostêpnych statusów.
	 * Kluczem jest Integer z nr statusu,
	 * a wartoscia String z nazw¹ statusu
	 * 
	 * @return Map
	 */
	public Map getAvaiableStatuses();
}