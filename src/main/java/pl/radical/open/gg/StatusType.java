package pl.radical.open.gg;

/**
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public final class StatusType {

	private String status = null;

	private StatusType(final String status) {
		this.status = status;
	}

	/** Status online */
	public final static StatusType ONLINE = new StatusType("online");

	/** Status online with description */
	public final static StatusType ONLINE_WITH_DESCRIPTION = new StatusType("online_with_description");

	/** Status offline */
	public final static StatusType OFFLINE = new StatusType("offline");

	/** Status offline with description */
	public final static StatusType OFFLINE_WITH_DESCRIPTION = new StatusType("offline_with_description");

	/** Status busy */
	public final static StatusType BUSY = new StatusType("busy");

	/** Status busy with description */
	public final static StatusType BUSY_WITH_DESCRIPTION = new StatusType("busy_with_description");

	/** Status invisible */
	public final static StatusType INVISIBLE = new StatusType("invisible");

	/** Status invisible with description */
	public final static StatusType INVISIBLE_WITH_DESCRIPTION = new StatusType("invisible_with_description");

	public boolean isDescriptionStatus() {
		return status.endsWith("description");
	}

	@Override
	public String toString() {
		return status;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return status.hashCode() * 37;
	}

}
