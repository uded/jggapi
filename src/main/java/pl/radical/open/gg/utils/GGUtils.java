package pl.radical.open.gg.utils;

import pl.radical.open.gg.GGException;
import pl.radical.open.gg.dicts.Encoding;
import pl.radical.open.gg.packet.dicts.GGHashType;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.collections.primitives.ArrayCharList;
import org.apache.commons.collections.primitives.CharList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @author <a href="mailto:lukasz.rzanek@radical.com.pl>Łukasz Rżanek</a>
 */
public final class GGUtils {
	private static final Logger LOG = LoggerFactory.getLogger(GGUtils.class);

	private GGUtils() {
	}

	public static String prettyBytesToString(final byte[] bytes) {
		final StringBuffer received = new StringBuffer();
		received.append('{');

		final char[] dump = Hex.encodeHex(bytes);
		int i = 0;
		for (final char c : dump) {
			received.append(c);
			i++;
			if (i % 2 == 0) {
				received.append(' ');
			}
		}

		received.append('}');

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

	public static byte[] intToByteArray(final int value) {
		final byte[] result = new byte[4];
		result[0] = (byte) (value & 0xFF);
		result[1] = (byte) (value >> 8 & 0xFF);
		result[2] = (byte) (value >> 16 & 0xFF);
		result[3] = (byte) (value >> 24 & 0xFF);

		return result;
	}

	public static byte[] shortToByteArray(final short value) {
		final byte[] result = new byte[2];
		result[0] = (byte) (value & 0xFF);
		result[1] = (byte) (value >> 8 & 0xFF);

		return result;
	}

	public static long secondsToMillis(final int seconds) {
		return seconds * 1000L;
	}

	public static int millisToSeconds(final long millis) {
		return (int) (millis / 1000L);
	}

	public static int unsignedByteToInt(final byte value) {
		int result = value;
		if (value < 0) {
			result = (value & 0x7F) + 0x80;
		}
		return result;
	}

	public static byte[] intToByte(final int buf) {
		final byte[] toSend = new byte[4];

		toSend[3] = (byte) (buf >> 24 & 0xFF);
		toSend[2] = (byte) (buf >> 16 & 0xFF);
		toSend[1] = (byte) (buf >> 8 & 0xFF);
		toSend[0] = (byte) (buf & 0xFF);

		return toSend;
	}

	public static long unsignedIntToLong(int value) {
		long plus = 0;
		plus -= value & 0x80000000;
		value &= 0x7FFFFFFF;

		return value + plus;
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
			returnString = new String(desc, Encoding.WINDOWS1250.getValue());
		} catch (final UnsupportedEncodingException ex) {
			LOG.warn("Unable to convert", ex);
			// FIXME Co to jest u diabła???
			returnString = new String(desc);
		}
		return returnString;
	}

	public static String byteToString(final byte[] data, final int startIndex, final Encoding encoding) {
		int counter = 0;

		while (counter + startIndex < data.length && data[counter + startIndex] != 0) {
			counter++;
		}

		final byte[] desc = new byte[counter];
		System.arraycopy(data, startIndex, desc, 0, counter);

		String returnString = null;
		try {
			returnString = new String(desc, encoding.getValue());
		} catch (final UnsupportedEncodingException ex) {
			LOG.warn("Unable to convert", ex);
			returnString = new String(desc);
		}
		return returnString;
	}

	public static byte[] convertIntToByteArray(final int value) {
		final byte[] bytes = new byte[4];
		bytes[0] = (byte) (value & 0xFF);
		bytes[1] = (byte) (value >> 8 & 0xFF);
		bytes[2] = (byte) (value >> 16 & 0xFF);
		bytes[3] = (byte) (value >> 24 & 0xFF);

		return bytes;
	}

	public static int getLoginHash(final char[] password, final int seed) {
		long x, y, z;

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

	public static char[] byteToCharArray(final byte[] bytes) {
		final CharList charList = new ArrayCharList();
		for (final byte b : bytes) {
			charList.add((char) b);
		}

		return charList.toArray();
	}

	private static byte[] getLoginHashSHA(final char[] password, final int seed) throws GGException {
		try {
			final MessageDigest hash = MessageDigest.getInstance("SHA1");
			hash.update(new String(password).getBytes());
			hash.update(GGUtils.intToByte(seed));
			return hash.digest();
		} catch (final NoSuchAlgorithmException e) {
			LOG.error("SHA1 algorithm not usable", e);
			throw new GGException("SHA1 algorithm not usable!", e);
		}
	}

	public static byte[] getLoginHash(final char[] password, final int seed, final GGHashType hashType) throws GGException {
		byte[] result;

		switch (hashType) {
			case GG_LOGIN_HASH_GG32:
				result = intToByte(getLoginHash(password, seed));
				break;
			case GG_LOGIN_HASH_SHA1:
				result = getLoginHashSHA(password, seed);
				break;
			default:
				throw new GGException("Hash algorithm to be used during login was not specified");
		}
		return result;
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
