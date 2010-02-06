package pl.mn.communicator.packet;

public class HexDump {

	public static String byteToHexString(final byte b) {
		return intToHexString(b, 2, '0');
	}

	public static String intToHexString(int num, int width, final char fill) {
		String result = "";

		if (num == 0) {
			result = "0";
			width--;
		} else {
			while (num != 0 && width > 0) {
				final String tmp = Integer.toHexString(num & 0xf);
				result = tmp + result;
				num = num >> 4;
				width--;
			}
		}
		for (; width > 0; width--) {
			result = fill + result;
		}
		return result;
	}

	public static String hexDump(final byte data[]) {
		return hexDump(data, data.length);
	}

	public static String hexDump(final byte data[], final int length) {
		int i;
		int j;
		final int bytesPerLine = 16;
		String result = "";

		for (i = 0; i < length; i += bytesPerLine) {
			// print the offset as a 4 digit hex number
			result = result + intToHexString(i, 4, '0') + "  ";

			// print each byte in hex
			for (j = i; j < length && j - i < bytesPerLine; j++) {
				result = result + byteToHexString(data[j]) + " ";
			}

			// skip over to the ascii dump column
			for (; 0 != j % bytesPerLine; j++) {
				result = result + "   ";
			}
			result = result + "  |";

			// print each byte in ascii
			for (j = i; j < length && j - i < bytesPerLine; j++) {
				if ((data[j] & 0xff) > 0x001f && (data[j] & 0xff) < 0x007f) {
					final Character ch = new Character((char) data[j]);
					result = result + ch;
				} else {
					result = result + ".";
				}
			}
			result = result + "|";
		}
		return result;
	}

}
