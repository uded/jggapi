package pl.mn.communicator.gadu;

/**
 * Naglowek wiadomosci gg
 * 
 * @author mnaglik
 */
class GGHeader {
	private byte[] data;
	private int type;
	private int length;

	/**
	 * Constructor for Header.
	 */
	public GGHeader(byte[] data) {
		this.type = GGConversion.byteToInt(data, 0);
		this.length = GGConversion.byteToInt(data, 4);
	}

	/**
	 * Returns the length.
	 * @return int
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Returns the type.
	 * @return int
	 */
	public int getType() {
		return type;
	}
}
