package pl.radical.open.gg;

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
	private boolean m_friendsOnly = false;

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
		return m_friendsOnly;
	}

	public void setFriendsOnly(final boolean bool) {
		m_friendsOnly = bool;
	}

}
