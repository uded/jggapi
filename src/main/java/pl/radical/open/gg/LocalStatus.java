package pl.radical.open.gg;

import pl.radical.open.gg.packet.dicts.StatusType;

import java.util.Date;

/**
 * The default implementation of <code>ILocalStatus</code>.
 * <p>
 * Created on 2004-12-21
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class LocalStatus extends AbstractStatus implements ILocalStatus {

	/** the field that indicated whether or user that logs on wants to be seen only by friends */
	private boolean friendsOnly = false;

	public LocalStatus(final StatusType statusType) {
		super(statusType);
	}

	public LocalStatus(final StatusType statusType, final String description) {
		super(statusType, description);
	}

	public LocalStatus(final StatusType statusType, final String description, final Date returnDate) {
		super(statusType, description, returnDate);
	}

	/**
	 * @see pl.radical.open.gg.ILocalStatus#isFriendsOnly()
	 */
	public boolean isFriendsOnly() {
		return friendsOnly;
	}

	public void setFriendsOnly(final boolean bool) {
		friendsOnly = bool;
	}

}
