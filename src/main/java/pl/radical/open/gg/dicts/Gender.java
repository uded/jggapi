package pl.radical.open.gg.dicts;

import lombok.Getter;

/**
 * @author <a href="mailto:lukasz@radical.com.pl">Łukasz Rżanek</a>
 * @since 1.6.9.0
 */
public enum Gender {
	MALE("male"),
	FEMALE("female");

	@Getter
	private String value;

	private Gender(final String value) {
		this.value = value;
	}

}
