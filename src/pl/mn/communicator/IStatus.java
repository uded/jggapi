package pl.mn.communicator;

import java.util.Map;

/**
 * @author mnaglik
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
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