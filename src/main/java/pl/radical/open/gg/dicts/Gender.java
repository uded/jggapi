package pl.radical.open.gg.dicts;

/**
 * @author <a href="mailto:lukasz@radical.com.pl">Łukasz Rżanek</a>
 * @since 1.6.9.0
 */
public enum Gender {
	MALE("male"),
	FEMALE("female");

	private String value;

	private Gender(final String value) {
		this.value = value;
	}
}
