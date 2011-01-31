package pl.radical.open.gg.packet.in;

import pl.radical.open.gg.packet.AbstractGGIncomingPacket;
import pl.radical.open.gg.packet.GGIncomingPackage;
import pl.radical.open.gg.packet.IncomingPacket;
import pl.radical.open.gg.packet.dicts.GGMessageClass;
import pl.radical.open.gg.packet.handlers.GGRecvMsg80PacketHandler;
import pl.radical.open.gg.utils.GGUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.apache.commons.collections.primitives.ArrayByteList;
import org.apache.commons.collections.primitives.ByteList;

/**
 * @author <a href="mailto:klacia.85@gmail.com">Kamil Klatkowski</a>
 * @author <a href="mailto:lukasz.rzanek@radical.com.pl>Łukasz Rżanek</a>
 * @since 1.7.0
 * @since 2010-03-18
 */

@IncomingPacket(
		type = 0x002E,
		label = "GG_RECV_MSG80",
		handler = GGRecvMsg80PacketHandler.class
)
@Data
@EqualsAndHashCode(callSuper = false)
public final class GGRecvMsg80 extends AbstractGGIncomingPacket implements GGIncomingPackage, GGMessageClass {

	private int sender = -1;

	private int messageSeq = -1;

	private long time = -1;

	private int msgClass = -1;

	private int offsetPlain = -1;

	private int offsetAttributes = -1;
	/** message in HTML format */
	private final String htmlMessage = null;

	/** message in plain text */
	private String plainMessage = null;

	/** messages's attributes */
	private byte[] attributes = null;

	public GGRecvMsg80(final byte[] data) {
		sender = GGUtils.byteToInt(data);
		messageSeq = GGUtils.byteToInt(data, 4);
		time = GGUtils.secondsToMillis(GGUtils.byteToInt(data, 8));
		msgClass = GGUtils.byteToInt(data, 12);
		offsetPlain = GGUtils.byteToInt(data, 16);
		offsetAttributes = GGUtils.byteToInt(data, 20);

		if (getOffsetAttributes() == data.length) {
			offsetAttributes = 0;
		} else {
			final ByteList byteList = new ArrayByteList(data.length - offsetAttributes);
			for (int i = offsetAttributes; i < data.length; i++) {
				byteList.add(data[i]);
			}
			attributes = byteList.toArray();
		}

		if (data.length > offsetPlain) {
			plainMessage = GGUtils.byteToString(data, offsetPlain);
		}

		// FIXME no htmlMessage
	}
}
