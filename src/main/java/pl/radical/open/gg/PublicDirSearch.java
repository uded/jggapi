package pl.radical.open.gg;

/**
 * This is an abstract class that is common for a query to Gadu-Gadu's public directory and reply from it.
 * <p>
 * Created on 2004-12-17
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public abstract class PublicDirSearch {

	protected Integer m_uin = null;
	protected String m_firstName = null;
	protected String m_nickName = null;
	protected String m_birthYear = null;
	protected String m_city = null;
	protected String m_familyName = null;
	protected String m_familyCity = null;

	public Integer getUin() {
		return m_uin;
	}

	public void setUin(final Integer uin) {
		m_uin = uin;
	}

	public String getFirstName() {
		return m_firstName;
	}

	public void setFirstName(final String firstName) {
		m_firstName = firstName;
	}

	public String getBirthYear() {
		return m_birthYear;
	}

	public void setBirthYear(final String birthYear) {
		m_birthYear = birthYear;
	}

	public String getNickName() {
		return m_nickName;
	}

	public void setNickName(final String nickName) {
		m_nickName = nickName;
	}

	public String getCity() {
		return m_city;
	}

	public void setCity(final String city) {
		m_city = city;
	}

	public String getFamilyName() {
		return m_familyName;
	}

	public void setFamilyName(final String familyName) {
		m_familyName = familyName;
	}

	public void setFamilyCity(final String familyCity) {
		m_familyCity = familyCity;
	}

	public String getFamilyCity() {
		return m_familyCity;
	}

}
