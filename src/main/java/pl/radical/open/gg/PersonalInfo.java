package pl.radical.open.gg;

/**
 * This class represents personal user's information. It is stored remotely in Gadu-Gadu public directory service.
 * Created on 2004-11-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class PersonalInfo {

	/** First name of the user */
	private String m_firstName = null;

	/** Last name of the user */
	private String m_lastName = null;

	/** Birth date of the user */
	private String m_birthDate = null;

	/** City of the user */
	private String m_city = null;

	/** The handle that the user want to identify hisself/herself */
	private String m_nickName = null;

	/** The gender of the user */
	private Gender m_gender = null;

	/** The name of the user's family */
	private String m_familyName = null;

	/** the name of the user's city */
	private String m_familyCity = null;

	public String getFirstName() {
		return m_firstName;
	}

	public void setFirstName(final String name) {
		m_firstName = name;
	}

	public String getLastName() {
		return m_lastName;
	}

	public void setLastName(final String name) {
		m_lastName = name;
	}

	public String getBirthDate() {
		return m_birthDate;
	}

	public void setBirthDate(final String date) {
		m_birthDate = date;
	}

	public String getCity() {
		return m_city;
	}

	public void setCity(final String city) {
		m_city = city;
	}

	public Gender getGender() {
		return m_gender;
	}

	public void setGender(final Gender gender) {
		m_gender = gender;
	}

	public String getNickName() {
		return m_nickName;
	}

	public void setNickName(final String nickName) {
		m_nickName = nickName;
	}

	public String getFamilyCity() {
		return m_familyCity;
	}

	public void setFamilyCity(final String familyCity) {
		m_familyCity = familyCity;
	}

	public String getFamilyName() {
		return m_familyName;
	}

	public void setFamilyName(final String familyName) {
		m_familyName = familyName;
	}

}
