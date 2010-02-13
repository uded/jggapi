package pl.radical.open.gg;

import java.util.Date;

/**
 * The abstact status implementation that is common for LocalStatus and RemoteStatus.
 * <p>
 * Created on 2004-12-21
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public abstract class AbstractStatus implements IStatus {

	/** the type of the status */
	private StatusType m_statusType = StatusType.ONLINE;

	/** Status description */
	private String m_description = null;

	/** Return time */
	private Date m_returnTime = null;

	protected AbstractStatus(final StatusType statusType, final String description, final Date returnDate) {
		if (statusType == null) {
			throw new NullPointerException("statusType cannot be null");
		}
		m_statusType = statusType;
		m_description = description;
		m_returnTime = returnDate;
	}

	protected AbstractStatus(final StatusType statusType, final String description) {
		this(statusType, description, null);
	}

	protected AbstractStatus(final StatusType statusType) {
		this(statusType, null, null);
	}

	/**
	 * @see pl.radical.open.gg.IStatus#getStatusType()
	 */
	public StatusType getStatusType() {
		return m_statusType;
	}

	public void setStatusType(final StatusType status) {
		m_statusType = status;
	}

	/**
	 * Returns the description.
	 * 
	 * @return <code>String</code> description as string.
	 */
	public String getDescription() {
		return m_description;
	}

	/**
	 * @param description
	 *            the description to set.
	 */
	public void setDescription(final String description) {
		m_description = description;
	}

	/**
	 * @return <code>Date</code> the return time.
	 */
	public Date getReturnDate() {
		if (m_returnTime == null) {
			return null;
		}
		return new Date(m_returnTime.getTime());
	}

	/**
	 * @param returnTime
	 *            The return time to set.
	 */
	public void setReturnDate(final Date returnTime) {
		if (returnTime == null) {
			throw new NullPointerException("returnTime cannot be null");
		}
		m_returnTime = new Date(returnTime.getTime());
	}

	/**
	 * @see pl.radical.open.gg.IStatus#isDescriptionSet()
	 */
	public boolean isDescriptionSet() {
		return m_description != null;
	}

	/**
	 * @see pl.radical.open.gg.IStatus#isReturnDateSet()
	 */
	public boolean isReturnDateSet() {
		return m_returnTime != null;
	}

}
