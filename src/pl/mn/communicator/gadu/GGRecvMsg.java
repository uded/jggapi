package pl.mn.communicator.gadu;

/**
 * Wiadomosc otrzymana z serwera gg (tekstowa)
 * 
 * @author mnaglik
 */
class GGRecvMsg {
	private int sender;
	private int seq;
	private int time;
	private int msgClass;
	private String message = "";

	public GGRecvMsg(byte[] data) {
		this.sender = GGConversion.byteToInt(data);
		this.seq = GGConversion.byteToInt(data, 4);
		this.time = GGConversion.byteToInt(data, 8);
		this.msgClass = GGConversion.byteToInt(data, 12);

		byte[] messageBytes = new byte[data.length - 17];
		for (int i = 16, j = 0; i < data.length - 1; i++)
			messageBytes[j++] = data[i];

		message = new String(messageBytes);
	}

	/**
	 * Returns the message.
	 * @return String
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Returns the msgClass.
	 * @return int
	 */
	public int getMsgClass() {
		return msgClass;
	}

	/**
	 * Returns the sender.
	 * @return int
	 */
	public int getSender() {
		return sender;
	}

	/**
	 * Returns the seq.
	 * @return int
	 */
	public int getSeq() {
		return seq;
	}

	/**
	 * Returns the time.
	 * @return int
	 */
	public int getTime() {
		return time;
	}

}
