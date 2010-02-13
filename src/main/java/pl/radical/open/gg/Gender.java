package pl.radical.open.gg;

/**
 * The Gender of the Gadu-Gadu user.
 * <p>
 * Created on 2004-12-16
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class Gender {

	/** String that represents a user's gender */
	private String m_gender = null;

	private Gender(final String gender) {
		m_gender = gender;
	}

	public final static Gender FEMALE = new Gender("female");
	public final static Gender MALE = new Gender("male");

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return m_gender;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return m_gender.hashCode();
	}

}
