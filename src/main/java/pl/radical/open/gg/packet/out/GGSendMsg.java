package pl.radical.open.gg.packet.out;

import pl.radical.open.gg.IOutgoingMessage;
import pl.radical.open.gg.packet.GGOutgoingPackage;
import pl.radical.open.gg.packet.OutgoingPacket;
import pl.radical.open.gg.packet.dicts.GGMessageClass;
import pl.radical.open.gg.utils.GGConversion;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.primitives.ArrayByteList;
import org.apache.commons.collections.primitives.ByteList;

/**
 * Class representing packet that will send Gadu-Gadu message.
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
@OutgoingPacket(type = 0x0B, label = "GG_SEND_MSG")
public class GGSendMsg implements GGOutgoingPackage, GGMessageClass {

	public static final int GG_SEND_MSG = 0x0B;

	private int seq = -1;
	private int recipientUin = -1;
	private final List<Integer> additionalRecipients = new ArrayList<Integer>();
	private String text = "";
	private int protocolMessageClass = GG_CLASS_MSG;

	public GGSendMsg(final IOutgoingMessage outgoingMessage) {
		if (outgoingMessage == null) {
			throw new IllegalArgumentException("outgoingMessage cannot be null");
		}
		text = outgoingMessage.getMessageBody();
		seq = outgoingMessage.getMessageID();
		recipientUin = outgoingMessage.getRecipientUin();
		protocolMessageClass = GGConversion.getProtocolMessageClass(outgoingMessage.getMessageClass());
	}

	public void addAdditionalRecipient(final int uin) {
		if (uin != recipientUin) {
			additionalRecipients.add(Integer.valueOf(uin));
		}
	}

	public void removeAdditionalRecipient(final int uin) {
		if (uin != recipientUin) {
			additionalRecipients.remove(Integer.valueOf(uin));
		}
	}

	/**
	 * @see pl.radical.open.gg.packet.GGOutgoingPackage#getPacketType()
	 */
	public int getPacketType() {
		return GG_SEND_MSG;
	}

	/**
	 * @see pl.radical.open.gg.packet.GGOutgoingPackage#getLength()
	 */
	public int getLength() {
		return 4 + 4 + 4 + text.length() + 1 + 5 + additionalRecipients.size() * 4;
	}

	/**
	 * @see pl.radical.open.gg.packet.GGOutgoingPackage#getContents()
	 */
	public byte[] getContents() {
		final ByteList byteList = new ArrayByteList(getLength());

		byteList.add((byte) (recipientUin & 0xFF));
		byteList.add((byte) (recipientUin >> 8 & 0xFF));
		byteList.add((byte) (recipientUin >> 16 & 0xFF));
		byteList.add((byte) (recipientUin >> 24 & 0xFF));

		byteList.add((byte) (seq & 0xFF));
		byteList.add((byte) (seq >> 8 & 0xFF));
		byteList.add((byte) (seq >> 16 & 0xFF));
		byteList.add((byte) (seq >> 24 & 0xFF));

		byteList.add((byte) (protocolMessageClass & 0xFF));
		byteList.add((byte) (protocolMessageClass >> 8 & 0xFF));
		byteList.add((byte) (protocolMessageClass >> 16 & 0xFF));
		byteList.add((byte) (protocolMessageClass >> 24 & 0xFF));

		// TODO check if this conversion needs charset
		final byte[] textBytes = text.getBytes();

		for (int i = 0; i < text.length(); i++) {
			byteList.add(textBytes[i]);
		}

		final int recipientCount = additionalRecipients.size();

		if (recipientCount > 0) {
			byteList.add((byte) 0x01);

			byteList.add((byte) (recipientCount & 0xFF));
			byteList.add((byte) (recipientCount >> 8 & 0xFF));
			byteList.add((byte) (recipientCount >> 16 & 0xFF));
			byteList.add((byte) (recipientCount >> 24 & 0xFF));

			for (int i = 0; i < recipientCount; i++) {
				final int recipientUin = additionalRecipients.get(i).intValue();
				byteList.add((byte) (recipientUin & 0xFF));
				byteList.add((byte) (recipientUin >> 8 & 0xFF));
				byteList.add((byte) (recipientUin >> 16 & 0xFF));
				byteList.add((byte) (recipientUin >> 24 & 0xFF));
			}
		}

		return byteList.toArray();
	}

}
