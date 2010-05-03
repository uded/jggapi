package pl.radical.open.gg;

/**
 * Created on 2004-11-11
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class LocalUser {

	/** Gadu-Gadu user's uin */
	private int m_uin = -1;

	/** the name of the user */
	private String m_name = null;

	/** the last name of the user */
	private String m_lastName = null;

	/** the nick name of the user */
	private String m_nickName = null;

	/** the name that is an alias for the user */
	private String m_displayName = null;

	/** the telehone of the user */
	private String m_telephone = null;

	/** the group that the user belongs */
	private String m_group = null;

	/** the e-mail address of the user */
	private String m_emailAddress = null;

	/** flag to indicate that the user is blocked */
	private boolean m_isBlocked = false;

	public void setFirstName(final String name) {
		if (name.isEmpty()) {
			throw new IllegalArgumentException("name cannot be null");
		}
		m_name = name;
	}

	public String getFirstName() {
		return m_name;
	}

	public void setLastName(final String surname) {
		m_lastName = surname;
	}

	public String getLastName() {
		return m_lastName;
	}

	public void setNickName(final String nickName) {
		m_nickName = nickName;
	}

	public String getNickName() {
		return m_nickName;
	}

	public void setDisplayName(final String displayName) {
		m_displayName = displayName;
	}

	public String getDisplayName() {
		return m_displayName;
	}

	public void setTelephone(final String telephone) {
		m_telephone = telephone;
	}

	public String getTelephone() {
		return m_telephone;
	}

	public String getGroup() {
		return m_group;
	}

	public void setGroup(final String group) {
		m_group = group;
	}

	public void setUin(final int uin) {
		if (uin < 0) {
			throw new IllegalArgumentException("uin cannot be less than 0");
		}
		m_uin = uin;
	}

	public int getUin() {
		return m_uin;
	}

	public void setEmailAddress(final String emailAddress) {
		m_emailAddress = emailAddress;
	}

	public String getEmailAddress() {
		return m_emailAddress;
	}

	public void setBlocked(final boolean isBlocked) {
		m_isBlocked = isBlocked;
	}

	public boolean isBlocked() {
		return m_isBlocked;
	}

}
