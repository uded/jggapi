package pl.radical.open.gg.packet.in;

import pl.radical.open.gg.packet.AbstractGGIncomingPacket;
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
public class GGRecvMsg extends AbstractGGIncomingPacket implements GGMessageClass, GGIncomingPackage {

	private int sender = -1;
	private int seq = -1;
	private long time = -1;
	private int msgClass = -1;
	private String message = "";

	public GGRecvMsg(final byte[] data) {
		sender = GGUtils.byteToInt(data);
		seq = GGUtils.byteToInt(data, 4);
		time = GGUtils.secondsToMillis(GGUtils.byteToInt(data, 8));
		msgClass = GGUtils.byteToInt(data, 12);
		message = GGUtils.byteToString(data, 16);
	}

	/**
	 * Returns the message.
	 * 
	 * @return String
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Returns the msgClass.
	 * 
	 * @return int msgClass
	 */
	public int getMsgClass() {
		return msgClass;
	}

	/**
	 * Returns the sender uin number.
	 * 
	 * @return int the sender uin.
	 */
	public int getSenderUin() {
		return sender;
	}

	/**
	 * Returns the unique message sequence number.
	 * 
	 * @return int message sequence number.
	 */
	public int getMessageSeq() {
		return seq;
	}

	/**
	 * Time in seconds.
	 * 
	 * @return int the time in seconds.
	 */
	public long getTime() {
		return time;
	}

}
