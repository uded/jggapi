package pl.radical.open.gg.packet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class GGUtils {

	public final static String WINDOW_ENCODING = "windows-1250";

	private static final Logger LOGGER = LoggerFactory.getLogger(GGUtils.class);

	public static String prettyBytesToString(final byte[] bytes) {
		final StringBuffer received = new StringBuffer();
		received.append("{");

		final String dump = HexDump.hexDump(bytes);
		received.append(dump);

		// for (int i=0; i<bytes.length; i++) {
		// received.append("'" + bytes[i] + "',");
		// }

		received.append("}");

		return received.toString();
	}

	public static int byteToInt(final byte[] buf) {
		return byteToInt(buf, 0);
	}

	public static int byteToInt(final byte[] buf, final int start) {
		int i = 0;
		int pos = start;

		int tmp;
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

	public static int byteToShort(final byte[] buf, final int start) {
		int i = 0;
		int pos = start;

		int tmp;
		tmp = unsignedByteToInt(buf[pos++]) << 0;
		i += tmp;
		tmp = unsignedByteToInt(buf[pos++]) << 8;
		i += tmp;

		return i;
	}

	public static long secondsToMillis(final int seconds) {
		return seconds * 1000L;
	}

	public static int millisToSeconds(final long millis) {
		return (int) (millis / 1000L);
	}

	public static int unsignedByteToInt(final byte i) {
		if (i < 0) {
			return (i & 0x7F) + 0x80;
		} else {
			return i;
		}
	}

	public static byte[] intToByte(final int buf) {
		final byte[] toSend = new byte[4];

		toSend[3] = (byte) (buf >> 24 & 0xFF);
		toSend[2] = (byte) (buf >> 16 & 0xFF);
		toSend[1] = (byte) (buf >> 8 & 0xFF);
		toSend[0] = (byte) (buf & 0xFF);

		return toSend;
	}

	public static long unsignedIntToLong(int i) {
		long plus = 0;
		plus -= i & 0x80000000;
		i &= 0x7FFFFFFF;

		return i + plus;
	}

	public static String byteToString(final byte[] data, final int startIndex) {
		int counter = 0;

		while (counter + startIndex < data.length && data[counter + startIndex] != 0) {
			counter++;
		}

		final byte[] desc = new byte[counter];
		System.arraycopy(data, startIndex, desc, 0, counter);

		String returnString = null;
		try {
			returnString = new String(desc, WINDOW_ENCODING);
		} catch (final UnsupportedEncodingException ex) {
			LOGGER.warn("Unable to convert", ex);
			return new String(desc);
		}
		return returnString;
	}

	public static byte[] convertIntToByteArray(final int i) {
		final byte[] bytes = new byte[4];
		bytes[0] = (byte) (i & 0xFF);
		bytes[1] = (byte) (i >> 8 & 0xFF);
		bytes[2] = (byte) (i >> 16 & 0xFF);
		bytes[3] = (byte) (i >> 24 & 0xFF);

		return bytes;
	}

	public static int getLoginHash(final char[] password, final int seed) {
		long x;
		long y;
		long z;

		y = seed;

		int i;

		for (x = 0, i = 0; i < password.length; i++) {
			x = x & 0xffffff00 | password[i];
			y ^= x;

			int k = (int) y;
			k += x;
			y = GGUtils.unsignedIntToLong(k);

			k = (int) x;
			k <<= 8;
			x = GGUtils.unsignedIntToLong(k);

			y ^= x;

			k = (int) x;
			k <<= 8;
			x = GGUtils.unsignedIntToLong(k);

			k = (int) y;
			k -= x;
			y = GGUtils.unsignedIntToLong(k);

			k = (int) x;
			k <<= 8;
			x = GGUtils.unsignedIntToLong(k);

			y ^= x;

			z = y & 0x1f;
			y = GGUtils.unsignedIntToLong((int) (y << z | y >> 32 - z));
		}

		return (int) y;
	}

	public static int copy(final InputStream input, final OutputStream output) throws IOException {
		final byte[] buffer = new byte[1024];
		int count = 0;
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}

}
