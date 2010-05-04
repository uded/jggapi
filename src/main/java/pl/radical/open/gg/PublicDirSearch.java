package pl.radical.open.gg;

/**
 * This is an abstract class that is common for a query to Gadu-Gadu's public directory and reply from it.
 * <p>
 * Created on 2004-12-17
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class PublicDirSearch {

	private Integer uin = null;
	private String firstName = null;
	private String nickName = null;
	private String birthYear = null;
	private String city = null;
	private String familyName = null;
	private String familyCity = null;

	public final Integer getUin() {
		return uin;
	}

	public final void setUin(final Integer uin) {
		this.uin = uin;
	}

	public final String getFirstName() {
		return firstName;
	}

	public final void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public final String getBirthYear() {
		return birthYear;
	}

	public final void setBirthYear(final String birthYear) {
		this.birthYear = birthYear;
	}

	public final String getNickName() {
		return nickName;
	}

	public final void setNickName(final String nickName) {
		this.nickName = nickName;
	}

	public final String getCity() {
		return city;
	}

	public final void setCity(final String city) {
		this.city = city;
	}

	public final String getFamilyName() {
		return familyName;
	}

	public final void setFamilyName(final String familyName) {
		this.familyName = familyName;
	}

	public final void setFamilyCity(final String familyCity) {
		this.familyCity = familyCity;
	}

	public final String getFamilyCity() {
		return familyCity;
	}

}
