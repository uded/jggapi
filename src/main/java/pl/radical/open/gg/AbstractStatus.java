package pl.radical.open.gg;

import pl.radical.open.gg.packet.dicts.StatusType;

import java.util.Date;

/**
 * The abstact status implementation that is common for LocalStatus and RemoteStatus.
 * <p>
 * Created on 2004-12-21
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public abstract class AbstractStatus implements IStatus {

	/**
	 * the type of the status
	 */
	private StatusType statusType = StatusType.ONLINE;

	/**
	 * Status description
	 */
	private String description = null;

	/**
	 * Return time
	 */
	private Date returnTime = null;

	protected AbstractStatus(final StatusType statusType, final String description, final Date returnTime) {
		if (statusType == null) {
			throw new IllegalArgumentException("statusType cannot be null");
		}
		this.statusType = statusType;
		this.description = description;
		this.returnTime = returnTime;
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
		return statusType;
	}

	public void setStatusType(final StatusType status) {
		statusType = status;
	}

	/**
	 * Returns the description.
	 * 
	 * @return <code>String</code> description as string.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set.
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

	/**
	 * @return <code>Date</code> the return time.
	 */
	public Date getReturnDate() {
		if (returnTime == null) {
			return null;
		}
		return new Date(returnTime.getTime());
	}

	/**
	 * @param returnTime
	 *            The return time to set.
	 */
	public void setReturnDate(final Date returnTime) {
		if (returnTime == null) {
			throw new IllegalArgumentException("returnTime cannot be null");
		}
		this.returnTime = new Date(returnTime.getTime());
	}

	/**
	 * @see pl.radical.open.gg.IStatus#isDescriptionSet()
	 */
	public boolean isDescriptionSet() {
		return description != null;
	}

	/**
	 * @see pl.radical.open.gg.IStatus#isReturnDateSet()
	 */
	public boolean isReturnDateSet() {
		return returnTime != null;
	}

}
