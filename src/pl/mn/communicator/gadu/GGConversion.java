package pl.mn.communicator.gadu;

/**
 * @author mnaglik
 */
class GGConversion {
	private GGConversion() {
		// prywatny konstruktor po to,
		// zeby nie tworzyc instancji klasy
	}
	
	public static int byteToInt(byte[] buf) {
		return byteToInt(buf, 0);
	}

	public static int byteToInt(byte[] buf, int start) {
		int i = 0;
		int pos = start;

		int tmp,plus=0;
		tmp = unsignedByteToInt(buf[pos++]) << 0;
		i += tmp;
		tmp = unsignedByteToInt(buf[pos++]) << 8;
		i += tmp;
		tmp = unsignedByteToInt(buf[pos++]) << 16;
		i += tmp;
		tmp = unsignedByteToInt(buf[pos++]) << 24;
		i += tmp;

		return i;
	}

	public static int unsignedByteToInt(byte i) {
		if (i < 0)
			return (i & 0x7F) + 0x80;
		else
			return i;
	}

	public static byte[] intToByte(int buf) {
		byte [] toSend = new byte[4];
		
		toSend[3] = (byte) (buf >> 24 & 0xFF);
		toSend[2] = (byte) (buf >> 16 & 0xFF);
		toSend[1] = (byte) (buf >> 8 & 0xFF);
		toSend[0] = (byte) (buf & 0xFF);
		
		return toSend;
	}

	public static long unsignedIntToLong(int i) {
		long plus = 0;
		plus -= (i & 0x80000000);
		i &= 0x7FFFFFFF;
		return (long) (i + plus);
	}
}
