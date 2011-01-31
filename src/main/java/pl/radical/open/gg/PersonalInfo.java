package pl.radical.open.gg;

import pl.radical.open.gg.dicts.Gender;

import lombok.Data;

/**
 * This class represents personal user's information. It is stored remotely in Gadu-Gadu public directory service.
 * Created on 2004-11-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
@Data
public class PersonalInfo {

	/**
	 * First name of the user
	 */
	private String firstName;

	/**
	 * Last name of the user
	 */
	private String lastName;

	/**
	 * Birth date of the user
	 */
	private String birthDate;

	/**
	 * City of the user
	 */
	private String city;

	/**
	 * The handle that the user want to identify hisself/herself
	 */
	private String nickName;

	/**
	 * The gender of the user
	 */
	private Gender gender;

	/**
	 * The name of the user's family
	 */
	private String familyName;

	/**
	 * the name of the user's city
	 */
	private String familyCity;
}
