package pl.radical.open.gg.packet.dicts;

import lombok.Getter;

/**
 * @author <a href="mailto:lukasz@radical.com.pl">Łukasz Rżanek</a>
 * @since 1.6.9.0
 */
public enum GGStatusFlags {
	/**
	 * Unknow flag, a base to start all calculations
	 */
	FLAG_UNKNOWN(0x00000001),
	/**
	 * Video support on the client side
	 */
	FLAG_VIDEOSUPPORTED(0x00000002),
	/**
	 * If the client being used is a mobile client
	 */
	FLAG_MOBILECLIENT(0x00100000),
	/**
	 * Receive links from strangers
	 */
	FLAG_RECEIVELINKS(0x00800000);

	@Getter
	private int value;

	private GGStatusFlags(final int value) {
		this.value = value;
	}

}
