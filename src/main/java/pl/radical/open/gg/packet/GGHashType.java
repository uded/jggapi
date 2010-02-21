package pl.radical.open.gg.packet;

/**
 * A hash type to be used during a login process
 * 
 * @author <a href="mailto:lukasz@radical.com.pl">Łukasz Rżanek</a>
 */
public enum GGHashType {
	GG_LOGIN_HASH_GG32(0x01),
	GG_LOGIN_HASH_SHA1(0x02);

	private int value;

	private GGHashType(final int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
