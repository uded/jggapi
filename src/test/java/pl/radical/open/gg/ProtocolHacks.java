package pl.radical.open.gg;

import static org.junit.Assert.assertEquals;

import pl.radical.open.gg.packet.dicts.GGStatuses;
import pl.radical.open.gg.utils.GGUtils;

import java.util.HashMap;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.collections.primitives.ArrayByteList;
import org.apache.commons.collections.primitives.ArrayCharList;
import org.apache.commons.collections.primitives.ByteList;
import org.apache.commons.collections.primitives.CharList;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProtocolHacks {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Test
	public void gglogin80() throws DecoderException {
		final HashMap<String, String> hexStrings = new HashMap<String, String>(2);
		hexStrings.put("libgadu", "6fd43401706c024818f11bd44521f0d26518ad29f19139e6fbeae70000000000000000000000000000000000000000000000000000000000000000000000000000000000000000020000000100800007000000000000000000000000000000ff6421000000476164752d4761647520436c69656e74204275696c6420382e302e302e3837333100000000");
		hexStrings.put("jgg-api", "6fd43401706c0231db9f9506c8bf56bfad88787da3626d7a91cb220000000000000000000000000000000000000000000000000000000000000000000000000000000000000000020000000100800007000000000000000000000000000000406421000000476164752d4761647520436c69656e74204275696c6420382e302e302e3837333100000000");

		final byte[] versionName = "Gadu-Gadu Client Build 8.0.0.8731".getBytes();

		for (final String key : hexStrings.keySet()) {
			log.info("Sprawdzam hexString nr {}", key);

			final Hex hex = new Hex();
			final byte[] bytes = Hex.decodeHex(hexStrings.get(key).toCharArray());

			final ByteList bl = new ArrayByteList(bytes.length);
			for (final byte b : bytes) {
				bl.add(b);
			}

			final int uin = GGUtils.byteToInt(bl.subList(0, 4).toArray());
			log.info("UIN: {}", uin);
			assertEquals(20239471, uin);

			final byte[] lang = bl.subList(4, 6).toArray();
			log.info("Language: {}{}", (char) Byte.valueOf(lang[0]).byteValue(), (char) Byte.valueOf(lang[1]).byteValue());
			assertEquals('p', (char) Byte.valueOf(lang[0]).byteValue());
			assertEquals('l', (char) Byte.valueOf(lang[1]).byteValue());

			final int hashType = Byte.valueOf(bl.subList(6, 7).get(0)).intValue();
			log.info("Hash type: {}", hashType);
			assertEquals(2, hashType);

			final int status = GGUtils.byteToInt(bl.subList(71, 75).toArray());
			log.info("Status: {}", status);
			assertEquals(GGStatuses.GG_STATUS_AVAIL, status);

			final int flags = GGUtils.byteToInt(bl.subList(75, 79).toArray());
			log.info("Flags: {}", flags);
			assertEquals(0x00800000 + 0x00000001, flags);

			final int features = GGUtils.byteToInt(bl.toArray(), 79);
			log.info("Features: {}", features);
			assertEquals(0x00000007, features);

			final int localIp = GGUtils.byteToInt(bl.toArray(), 83);
			log.info("Local IP: {}", localIp);
			assertEquals(0, localIp);

			final int localPort = GGUtils.byteToShort(bl.toArray(), 87);
			log.info("Local port: {}", localPort);
			assertEquals(0, localPort);

			final int externalIp = GGUtils.byteToInt(bl.toArray(), 89);
			log.info("External IP: {}", externalIp);
			assertEquals(0, externalIp);

			final int externalPort = GGUtils.byteToShort(bl.toArray(), 93);
			log.info("External port: {}", externalPort);
			assertEquals(0, externalPort);

			final int imageSize = Byte.valueOf(bl.subList(95, 96).get(0)).intValue();
			log.info("Image size: {}", imageSize);

			final int unknown = Byte.valueOf(bl.subList(96, 97).get(0)).intValue();
			log.info("Unknown: {}", unknown);
			assertEquals(0x64, unknown);

			final int version = GGUtils.byteToInt(bl.toArray(), 97);
			log.info("Version: {}", Integer.toString(version, 16));
			// assertEquals(GGVersion.VERSION_80_build_8283, version);

			final byte[] b = bl.subList(101, 101 + versionName.length).toArray();
			final CharList cl = new ArrayCharList();
			for (final byte c : b) {
				cl.add((char) c);
			}
			log.info("Version name: '{}'", cl.toString());
			assertEquals(cl.toString(), "Gadu-Gadu Client Build 8.0.0.8731");

			final int descriptionSize = GGUtils.byteToInt(bl.toArray(), 101 + versionName.length);
			log.info("Description length: {}", descriptionSize);

			if (bl.size() > 101 + 4 + versionName.length) {
				log.error("There are few more bytes in the packet left...");
			}
		}
	}
}
