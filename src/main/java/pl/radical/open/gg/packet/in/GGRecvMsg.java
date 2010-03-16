package pl.radical.open.gg.packet.in;

import pl.radical.open.gg.packet.GGBaseIncomingPacket;
import pl.radical.open.gg.packet.GGIncomingPackage;
import pl.radical.open.gg.packet.IncomingPacket;
import pl.radical.open.gg.packet.dicts.GGMessageClass;
import pl.radical.open.gg.packet.handlers.GGMessageReceivedPacketHandler;
import pl.radical.open.gg.utils.GGUtils;

/**
 * Class representing Gadu-Gadu received message packet.
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @author <a href="mailto:lukasz.rzanek@radical.com.pl>Łukasz Rżanek</a>
 */
@IncomingPacket(type = 0x000a, label = "GG_RECV_MSG", handler = GGMessageReceivedPacketHandler.class)
@Deprecated
public class GGRecvMsg extends GGBaseIncomingPacket implements GGMessageClass, GGIncomingPackage {

	private int m_sender = -1;
	private int m_seq = -1;
	private long m_time = -1;
	private int m_msgClass = -1;
	private String m_message = "";

	public GGRecvMsg(final byte[] data) {
		m_sender = GGUtils.byteToInt(data);
		m_seq = GGUtils.byteToInt(data, 4);
		m_time = GGUtils.secondsToMillis(GGUtils.byteToInt(data, 8));
		m_msgClass = GGUtils.byteToInt(data, 12);
		m_message = GGUtils.byteToString(data, 16);
	}

	/**
	 * Returns the message.
	 * 
	 * @return String
	 */
	public String getMessage() {
		return m_message;
	}

	/**
	 * Returns the msgClass.
	 * 
	 * @return int msgClass
	 */
	public int getMsgClass() {
		return m_msgClass;
	}

	/**
	 * Returns the sender uin number.
	 * 
	 * @return int the sender uin.
	 */
	public int getSenderUin() {
		return m_sender;
	}

	/**
	 * Returns the unique message sequence number.
	 * 
	 * @return int message sequence number.
	 */
	public int getMessageSeq() {
		return m_seq;
	}

	/**
	 * Time in seconds.
	 * 
	 * @return int the time in seconds.
	 */
	public long getTime() {
		return m_time;
	}

}
