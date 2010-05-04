package pl.radical.open.gg;

import pl.radical.open.gg.packet.dicts.StatusType;

import java.io.Serializable;
import java.util.Date;

/**
 * Interface that is common for all statuses, that is local and remote.
 * <p>
 * Created on 2004-12-12
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public interface IStatus extends Serializable {

	/**
	 * Get the StatusType.
	 * 
	 * @return StatusType
	 */
	StatusType getStatusType();

	void setStatusType(StatusType status);

	/**
	 * Get the status description.
	 * 
	 * @return description of status.
	 */
	String getDescription();

	/**
	 * @param description
	 *            the description to set.
	 */
	void setDescription(String description);

	/**
	 * Get the return date.
	 * 
	 * @return the return date.
	 */
	Date getReturnDate();

	/**
	 * @param returnTime
	 *            The return time to set.
	 */
	void setReturnDate(Date returnTime);

	/**
	 * Tells if the description has been set on this status.
	 * 
	 * @return <code>true</code> if the description has been set, <code>false</code> otherwise.
	 */
	boolean isDescriptionSet();

	/**
	 * Tells if the return date has been set on this status instance.
	 * 
	 * @return <code>true</code> if the return date has been set, <code>false</code> otherwise.
	 */
	boolean isReturnDateSet();

}
