package pl.mn.communicator.gadu;

import pl.mn.communicator.IMessage;

/**
 * @version $Revision: 1.4 $
 * @author mnaglik
 */
class GGSendMsg implements GGOutgoingPackage {
	private int user;
	private String text;
	
	private static int seqNo;
	private int seq;
	private int msgClass = 0x0004;

	public GGSendMsg(IMessage message) {
		this.user = message.getUser();
		this.text = message.getText();
		seq = seqNo++;
	}
	
	/**
	 * @see pl.mn.communicator.gadu.GGOutgoingPackage#getHeader()
	 */
	public int getHeader() {
		return 0x0b;
	}

	/**
	 * @see pl.mn.communicator.gadu.GGOutgoingPackage#getLength()
	 */
	public int getLength() {
		return 12+text.length()+1;
	}

	/**
	 * @see pl.mn.communicator.gadu.GGOutgoingPackage#getContents()
	 */
	public byte[] getContents() {
		byte [] toSend = new byte[12+text.length()+1];

		toSend[3] = (byte) (user >> 24 & 0xFF);
		toSend[2] = (byte) (user >> 16 & 0xFF);
		toSend[1] = (byte) (user >> 8 & 0xFF);
		toSend[0] = (byte) (user & 0xFF);

		toSend[7] = (byte) (seq >> 24 & 0xFF);
		toSend[6] = (byte) (seq >> 16 & 0xFF);
		toSend[5] = (byte) (seq >> 8 & 0xFF);
		toSend[4] = (byte) (seq & 0xFF);

		toSend[11]= (byte) (msgClass >> 24 & 0xFF);
		toSend[10]= (byte) (msgClass >> 16 & 0xFF);
		toSend[9] = (byte) (msgClass >> 8 & 0xFF);
		toSend[8] = (byte) (msgClass & 0xFF);
		
		byte [] textBytes = text.getBytes(); 
		for (int i=0; i<text.length(); i++)
			toSend[12+i] = textBytes[i];

		return toSend;
	}
}
