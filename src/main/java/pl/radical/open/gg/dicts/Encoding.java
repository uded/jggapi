package pl.radical.open.gg.dicts;

/**
 * Created on 2010-03-22
 * 
 * @author <a href="mailto:klacia.85@gmail.com">Kamil Klatkowski</a>
 * @since 1.6.9
 */

public enum Encoding {

	WINDOWS1250("windows-1250"),
	UTF8("UTF8");

	private String value;

	private Encoding(final String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
