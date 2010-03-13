package pl.radical.open.gg.packet;

public enum GGStatusFlags {
	FLAG_UNKNOWN(0x00000001),
	FLAG_VIDEOSUPPORTED(0x00000002),
	FLAG_MOBILECLIENT(0x00100000),
	FLAG_RECEIVELINKS(0x00800000);

	private int value;

	private GGStatusFlags(final int value) {
		this.value = value;
	}

	public int value() {
		return value;
	}
}
