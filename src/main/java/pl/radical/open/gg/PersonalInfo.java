package pl.radical.open.gg;

import pl.radical.open.gg.dicts.Gender;

/**
 * This class represents personal user's information. It is stored remotely in Gadu-Gadu public directory service.
 * Created on 2004-11-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class PersonalInfo {

	/** First name of the user */
	private String firstName = null;

	/** Last name of the user */
	private String lastName = null;

	/** Birth date of the user */
	private String birthDate = null;

	/** City of the user */
	private String city = null;

	/** The handle that the user want to identify hisself/herself */
	private String nickName = null;

	/** The gender of the user */
	private Gender gender = null;

	/** The name of the user's family */
	private String familyName = null;

	/** the name of the user's city */
	private String familyCity = null;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(final String birthDate) {
		this.birthDate = birthDate;
	}

	public String getCity() {
		return city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(final Gender gender) {
		this.gender = gender;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(final String nickName) {
		this.nickName = nickName;
	}

	public String getFamilyCity() {
		return familyCity;
	}

	public void setFamilyCity(final String familyCity) {
		this.familyCity = familyCity;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(final String familyName) {
		this.familyName = familyName;
	}

}
