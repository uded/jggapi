package pl.radical.open.gg;

/**
 * This class should be used when there is a need to search in Gadu-Gadu's public directory.
 * <p>
 * Create instance of this class and then narrow search by setting criteria on this object. All methods can return null
 * if a certain criteria was not used in narrowing search.
 * <p>
 * Created on 2004-11-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class PublicDirSearchQuery extends PublicDirSearch {

	private String m_lastName = null;
	private Gender m_gender = null;
	private Boolean m_activeOnly = null;
	private Integer m_start = null;

	public Gender getGender() {
		return m_gender;
	}

	public void setGender(final Gender gender) {
		m_gender = gender;
	}

	public String getLastName() {
		return m_lastName;
	}

	public void setLastName(final String lastName) {
		m_lastName = lastName;
	}

	public void setActiveOnly(final Boolean only) {
		m_activeOnly = only;
	}

	public Boolean isActiveOnly() {
		return m_activeOnly;
	}

	public void setStart(final Integer start) {
		m_start = start;
	}

	public Integer getStart() {
		return m_start;
	}

}
