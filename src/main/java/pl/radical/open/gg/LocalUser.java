package pl.radical.open.gg;

/**
 * Created on 2004-11-11
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public final class LocalUser {

	/**
	 * Gadu-Gadu user's uin
	 */
	private int uin = -1;

	/**
	 * the name of the user
	 */
	private String name = null;

	/**
	 * the last name of the user
	 */
	private String lastName = null;

	/**
	 * the nick name of the user
	 */
	private String nickName = null;

	/**
	 * the name that is an alias for the user
	 */
	private String displayName = null;

	/**
	 * the telehone of the user
	 */
	private String telephone = null;

	/**
	 * the group that the user belongs
	 */
	private String group = null;

	/**
	 * the e-mail address of the user
	 */
	private String emailAddress = null;

	/**
	 * flag to indicate that the user is blocked
	 */
	private boolean blocked = false;

	public void setFirstName(final String name) {
		if (name.isEmpty()) {
			throw new IllegalArgumentException("name cannot be null");
		}
		this.name = name;
	}

	public String getFirstName() {
		return name;
	}

	public void setLastName(final String surname) {
		lastName = surname;
	}

	public String getLastName() {
		return lastName;
	}

	public void setNickName(final String nickName) {
		this.nickName = nickName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setDisplayName(final String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setTelephone(final String telephone) {
		this.telephone = telephone;
	}

	public String getTelephone() {
		return telephone;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(final String group) {
		this.group = group;
	}

	public void setUin(final int uin) {
		if (uin < 0) {
			throw new IllegalArgumentException("uin cannot be less than 0");
		}
		this.uin = uin;
	}

	public int getUin() {
		return uin;
	}

	public void setEmailAddress(final String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setBlocked(final boolean isBlocked) {
		blocked = isBlocked;
	}

	public boolean isBlocked() {
		return blocked;
	}

}
