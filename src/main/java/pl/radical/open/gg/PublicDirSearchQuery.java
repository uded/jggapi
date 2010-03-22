package pl.radical.open.gg;

import pl.radical.open.gg.dicts.Gender;

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

	private String lastName = null;
	private Gender gender = null;
	private Boolean activeOnly = null;
	private Integer start = null;

	public Gender getGender() {
		return gender;
	}

	public void setGender(final Gender gender) {
		this.gender = gender;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public void setActiveOnly(final Boolean activeOnly) {
		this.activeOnly = activeOnly;
	}

	public Boolean isActiveOnly() {
		return activeOnly;
	}

	public void setStart(final Integer start) {
		this.start = start;
	}

	public Integer getStart() {
		return start;
	}

}
