package pl.radical.open.gg;

/**
 * This is an abstract class that is common for a query to Gadu-Gadu's public directory and reply from it.
 * <p>
 * Created on 2004-12-17
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public abstract class PublicDirSearch {

	protected Integer uin = null;
	protected String firstName = null;
	protected String nickName = null;
	protected String birthYear = null;
	protected String city = null;
	protected String familyName = null;
	protected String familyCity = null;

	public Integer getUin() {
		return uin;
	}

	public void setUin(final Integer uin) {
		this.uin = uin;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(final String birthYear) {
		this.birthYear = birthYear;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(final String nickName) {
		this.nickName = nickName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(final String familyName) {
		this.familyName = familyName;
	}

	public void setFamilyCity(final String familyCity) {
		this.familyCity = familyCity;
	}

	public String getFamilyCity() {
		return familyCity;
	}

}
