package pl.radical.open.gg.packet.in;

import pl.radical.open.gg.packet.GGIncomingPackage;
import pl.radical.open.gg.packet.GGBaseIncomingPacket;
import pl.radical.open.gg.packet.IncomingPacket;
import pl.radical.open.gg.packet.handlers.GGSentMessageAckPacketHandler;
import pl.radical.open.gg.utils.GGUtils;

/**
 * Acknowledgment of successuly delivered message that is recieved from Gadu-Gadu server.
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @author <a href="mailto:lukasz.rzanek@radical.com.pl>Łukasz Rżanek</a>
 */
@IncomingPacket(type = 0x0005, handler = GGSentMessageAckPacketHandler.class)
public class GGSendMsgAck extends GGBaseIncomingPacket implements GGIncomingPackage {
	// Message statuses

	/** Message has not been delivered. */
	public final static int GG_ACK_BLOCKED = 0x0001;

	/** Message has been successfuly delivered. */
	public final static int GG_ACK_DELIVERED = 0x0002;

	/** Message has been queued for later delivery. */
	public final static int GG_ACK_QUEUED = 0x0003;

	/** Message has not been delivered because remote queue is full (max. 20 messages). */
	public final static int GG_ACK_MBOXFULL = 0x0004;

	/** Message has not been delivered. This status is only in case of GG_CLASS_CTCP */
	public final static int GG_ACK_NOT_DELIVERED = 0x0006;

	private int m_messageStatus = -1;
	private int m_recipient = -1;
	private int m_messageSeq = -1;

	public GGSendMsgAck(final byte[] data) {
		m_messageStatus = GGUtils.byteToInt(data, 0);
		m_recipient = GGUtils.byteToInt(data, 4);
		m_messageSeq = GGUtils.byteToInt(data, 8);
	}

	/**
	 * @return Status of the message. Available statuses are listed above.
	 */
	public int getMessageStatus() {
		return m_messageStatus;
	}

	/**
	 * @return Gadu-Gadu uin number of the person to whom message was sent.
	 */
	public int getRecipientUin() {
		return m_recipient;
	}

	/**
	 * @return Sequence number of the message that has been sent.
	 */
	public int getMessageSeq() {
		return m_messageSeq;
	}

}
