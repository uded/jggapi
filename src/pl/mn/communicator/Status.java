/*
 * Created on 2004-12-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pl.mn.communicator;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author mateusz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Status implements IStatus {

	private static Log logger = LogFactory.getLog(Status.class);

	private StatusType m_statusType = StatusType.ONLINE;
	private boolean m_friendsOnly = false;
	private boolean m_blocked = false;
	
	/** Status description */
	private String m_description = null;

	/** Return time */
	private Date m_returnTime = null;

	/**
	 * @param status the status of user
	 */
	public Status(StatusType status) {
		this(status, null, null);
	}

	public Status(StatusType status, String description) {
		this(status, description, null);
	}

	public Status(StatusType statusType, String description, Date returnDate) {
		if (statusType == null)
			throw new NullPointerException("statusType cannot be null");
		m_statusType = statusType;
		m_description = description;
		m_returnTime = returnDate;
	}

	/**
	 * @see pl.mn.communicator.IStatus#getStatusType()
	 */
	public StatusType getStatusType() {
		return m_statusType;
	}

	/**
	 * @see pl.mn.communicator.IStatus#setStatusType(int)
	 */
	public void setStatusType(StatusType status) {
		m_statusType = status;
	}

	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return m_description;
	}

	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		m_description = description;
	}

	/**
	 * @return Returns the returnTime.
	 */
	public Date getReturnDate() {
		return m_returnTime;
	}

	/**
	 * @param returnTime The returnTime to set.
	 */
	public void setReturnDate(Date returnTime) {
		if (returnTime == null)
			throw new NullPointerException("returnTime cannot be null");
		m_returnTime = returnTime;
	}

	/**
	 * @see pl.mn.communicator.IStatus#isDescriptionSet()
	 */
	public boolean isDescriptionSet() {
		return m_description != null;
	}

	/**
	 * @see pl.mn.communicator.IStatus#isReturnDateSet()
	 */
	public boolean isReturnDateSet() {
		return m_returnTime != null;
	}
	
	/**
	 * @see pl.mn.communicator.IStatus#isFriendsOnly()
	 */
	public boolean isFriendsOnly() {
		return m_friendsOnly;
	}
	
	/**
	 * @see pl.mn.communicator.IStatus#setFriendsOnly(boolean)
	 */
	public void setFriendsOnly(boolean bool) {
		m_friendsOnly = bool;
	}
	
	/**
	 * @see pl.mn.communicator.IStatus#setBlocked(boolean)
	 */
	public void setBlocked(boolean blocked) {
		m_blocked = blocked;
	}
	
	/**
	 * @see pl.mn.communicator.IStatus#isBlocked()
	 */
	public boolean isBlocked() {
		return m_blocked;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "[Status: " + m_statusType + ", description: " + m_description+ ", time: " + m_returnTime+"]";
	}

}