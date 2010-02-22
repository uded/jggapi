package pl.radical.open.gg.packet.out;

import pl.radical.open.gg.GGNullPointerException;
import pl.radical.open.gg.IOutgoingMessage;
import pl.radical.open.gg.packet.GGConversion;
import pl.radical.open.gg.packet.GGMessageClass;

import java.util.ArrayList;

import org.apache.commons.collections.primitives.ArrayByteList;
import org.apache.commons.collections.primitives.ByteList;

/**
 * Class representing packet that will send Gadu-Gadu message.
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class GGSendMsg implements GGOutgoingPackage, GGMessageClass {

	public static final int GG_SEND_MSG = 0x0B;

	private int m_seq = -1;
	private int m_recipientUin = -1;
	private final ArrayList<Integer> m_additionalRecipients = new ArrayList<Integer>();
	private String m_text = "";
	private int m_protocolMessageClass = GG_CLASS_MSG;

	public GGSendMsg(final IOutgoingMessage outgoingMessage) {
		if (outgoingMessage == null) {
			throw new GGNullPointerException("outgoingMessage cannot be null");
		}
		m_text = outgoingMessage.getMessageBody();
		m_seq = outgoingMessage.getMessageID();
		m_recipientUin = outgoingMessage.getRecipientUin();
		m_protocolMessageClass = GGConversion.getProtocolMessageClass(outgoingMessage.getMessageClass());
	}

	public void addAdditionalRecipient(final int uin) {
		if (uin != m_recipientUin) {
			m_additionalRecipients.add(Integer.valueOf(uin));
		}
	}

	public void removeAdditionalRecipient(final int uin) {
		if (uin != m_recipientUin) {
			m_additionalRecipients.remove(Integer.valueOf(uin));
		}
	}

	/**
	 * @see pl.radical.open.gg.packet.out.GGOutgoingPackage#getPacketType()
	 */
	public int getPacketType() {
		return GG_SEND_MSG;
	}

	/**
	 * @see pl.radical.open.gg.packet.out.GGOutgoingPackage#getLength()
	 */
	public int getLength() {
		return 4 + 4 + 4 + m_text.length() + 1 + 5 + m_additionalRecipients.size() * 4;
	}

	/**
	 * @see pl.radical.open.gg.packet.out.GGOutgoingPackage#getContents()
	 */
	public byte[] getContents() {
		final ByteList byteList = new ArrayByteList(getLength());

		byteList.add((byte) (m_recipientUin & 0xFF));
		byteList.add((byte) (m_recipientUin >> 8 & 0xFF));
		byteList.add((byte) (m_recipientUin >> 16 & 0xFF));
		byteList.add((byte) (m_recipientUin >> 24 & 0xFF));

		byteList.add((byte) (m_seq & 0xFF));
		byteList.add((byte) (m_seq >> 8 & 0xFF));
		byteList.add((byte) (m_seq >> 16 & 0xFF));
		byteList.add((byte) (m_seq >> 24 & 0xFF));

		byteList.add((byte) (m_protocolMessageClass & 0xFF));
		byteList.add((byte) (m_protocolMessageClass >> 8 & 0xFF));
		byteList.add((byte) (m_protocolMessageClass >> 16 & 0xFF));
		byteList.add((byte) (m_protocolMessageClass >> 24 & 0xFF));

		// TODO check if this conversion needs charset
		final byte[] textBytes = m_text.getBytes();

		for (int i = 0; i < m_text.length(); i++) {
			byteList.add(textBytes[i]);
		}

		final int recipientCount = m_additionalRecipients.size();

		if (recipientCount > 0) {
			byteList.add((byte) 0x01);

			byteList.add((byte) (recipientCount & 0xFF));
			byteList.add((byte) (recipientCount >> 8 & 0xFF));
			byteList.add((byte) (recipientCount >> 16 & 0xFF));
			byteList.add((byte) (recipientCount >> 24 & 0xFF));

			for (int i = 0; i < recipientCount; i++) {
				final int recipientUin = m_additionalRecipients.get(i).intValue();
				byteList.add((byte) (recipientUin & 0xFF));
				byteList.add((byte) (recipientUin >> 8 & 0xFF));
				byteList.add((byte) (recipientUin >> 16 & 0xFF));
				byteList.add((byte) (recipientUin >> 24 & 0xFF));
			}
		}

		return byteList.toArray();
	}

}
