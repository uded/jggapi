package pl.mn.communicator.gadu;

import java.util.Map;

import pl.mn.communicator.AbstractStatus;

/**
 * Status u¿ytkownika gg.
 * 
 * @author mnaglik
 */
public class Status extends AbstractStatus {
	/**
	 * Status niewidoczny.
	 */
	public final static int NOT_VISIBLE = 3;

	/**
	 * @param status
	 */
	public Status(int status) {
		super(status);
	}

	/**
	 * Pobierz dostêpne statusy.
	 * @return Map 
	 * @see pl.mn.communicator.AbstractStatus#getAvaiableStatuses()
	 */
	public Map getAvaiableStatuses() {
		Map map = super.getAvaiableStatuses();
		map.put(new Integer(Status.NOT_VISIBLE),"NOT VISIBLE");

		return map;
	}

}
