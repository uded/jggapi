package pl.mn.communicator.gadu;

/**
 * Status uzytkownika gg
 * 
 * @author mnaglik
 */

class GGStatus implements GGOutgoingPackage {
	private int status;

	public final static int GG_STATUS_AVAIL = 0x00000002;
	public final static int GG_STATUS_NOT_AVAIL = 0x00000001;

	public GGStatus(int status){
		this.status = status;
	}

	/**
	 * @see pl.mn.communicator.gadu.GGOutgoingPackage#getHeader()
	 */
	public int getHeader() {
		return 0x02;
	}

	/**
	 * @see pl.mn.communicator.gadu.GGOutgoingPackage#getLength()
	 */
	public int getLength() {
		return 4;
	}

	/**
	 * @see pl.mn.communicator.gadu.GGOutgoingPackage#getContents()
	 */
	public byte[] getContents() {
		int statusToSend = 0;
		switch(status){
			case Status.ON_LINE:
				statusToSend = GG_STATUS_AVAIL;
				break;
			case Status.OFF_LINE:
				statusToSend = GG_STATUS_NOT_AVAIL;
				break;
			case Status.NOT_VISIBLE:
				statusToSend = GG_STATUS_NOT_AVAIL;
				break;
		}

		byte [] toSend = new byte[4];

		toSend[3] = (byte) (statusToSend >> 24 & 0xFF);
		toSend[2] = (byte) (statusToSend >> 16 & 0xFF);
		toSend[1] = (byte) (statusToSend >> 8 & 0xFF);
		toSend[0] = (byte) (statusToSend & 0xFF);
		
		return toSend;
	}


}
