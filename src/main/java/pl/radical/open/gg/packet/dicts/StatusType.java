package pl.radical.open.gg.packet.dicts;

/**
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public enum StatusType {
	/** Status online */
	ONLINE("online", false),

	/** Status online with description */
	ONLINE_WITH_DESCRIPTION("online_with_description", true),

	/** Status offline */
	OFFLINE("offline", false),

	/** Status offline with description */
	OFFLINE_WITH_DESCRIPTION("offline_with_description", true),

	/** Status busy */
	BUSY("busy", false),

	/** Status busy with description */
	BUSY_WITH_DESCRIPTION("busy_with_description", true),

	/** Status invisible */
	INVISIBLE("invisible", false),

	/** Status invisible with description */
	INVISIBLE_WITH_DESCRIPTION("invisible_with_description", true);

	private String status = null;
	private boolean description;

	private StatusType(final String status, final boolean description) {
		this.status = status;
	}

	public boolean isDescriptionStatus() {
		return description;
	}

	public String getStatus() {
		return status;
	}

}
