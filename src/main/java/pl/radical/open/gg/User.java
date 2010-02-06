package pl.radical.open.gg;

/**
 * The class represents Gadu-Gadu user.
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class User implements IUser {

	private int m_uin = -1;
	private UserMode m_userMode = null;

	public User(final int uin) {
		this(uin, UserMode.BUDDY);
	}

	public User(final int uin, final UserMode userMode) {
		if (uin < 0) {
			throw new IllegalArgumentException("uin cannot be less than 0");
		}
		if (userMode == null) {
			throw new NullPointerException("userMode cannot be null");
		}
		m_uin = uin;
		m_userMode = userMode;
	}

	public int getUin() {
		return m_uin;
	}

	/**
	 * @see pl.radical.open.gg.IUser#getUserMode()
	 */
	public UserMode getUserMode() {
		return m_userMode;
	}

	public void setUserMode(final UserMode userMode) {
		if (userMode == null) {
			throw new NullPointerException("userMode cannot be null");
		}
		m_userMode = userMode;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object o) {
		if (o instanceof IUser) {
			final IUser user = (IUser) o;
			if (user.getUin() == m_uin) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return m_uin;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[Uin: " + m_uin + ", userMode: " + m_userMode + "]";
	}

	public static class UserMode {

		private String m_type = null;

		private UserMode(final String type) {
			m_type = type;
		}

		public final static UserMode BUDDY = new UserMode("user_mode_buddy");
		public final static UserMode FRIEND = new UserMode("user_mode_friend");
		public final static UserMode BLOCKED = new UserMode("user_mode_blocked");
		public final static UserMode UNKNOWN = new UserMode("user_mode_unknown");

		/**
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return m_type;
		}

		/**
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			return m_type.hashCode() * 37;
		}
	}

}
