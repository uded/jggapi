package pl.mn.communicator.gadu;

import java.util.Date;

/**
 * Status uzytkownika gg
 * 
 * @version $Revision: 1.2 $
 * @author mnaglik
 */
class GGStatus implements GGOutgoingPackage {
	private int status;
	private byte[] opis = new byte[40];
	private int czas;

	/** Status dostepny */
	public final static int GG_STATUS_AVAIL = 0x00000002;
	/** Status dostepny z opisem */
	public final static int GG_STATUS_AVAIL_DESCR = 0x00000004;
	/** Status niedostepny */
	public final static int GG_STATUS_NOT_AVAIL = 0x00000001;
	/** Status niedostepny z opisem */
	public final static int GG_STATUS_NOT_AVAIL_DESCR = 0x00000015;
	/** Status zajety */
	public final static int GG_STATUS_BUSY = 0x00000003;
	/** Status zajety z opisem */
	public final static int GG_STATUS_BUSY_DESCR = 0x00000005;
	/** Status niewidoczny */
	public final static int GG_STATUS_INVISIBLE = 0x00000014;
	/** Status niewidoczny z opisem */
	public final static int GG_STATUS_INVISIBLE_DESCR = 0x00000016;
	/** Status zablokowany */
	public final static int GG_STATUS_BLOCKED = 0x00000006;
	/** Maska bitowa oznaczajaca tryb tylko dla przyjaciol */
	public final static int GG_STATUS_FRIENDS_MASK = 0x00008000;
	
	/**
	 * Konstruktor statusu.
	 * @param status status
	 */
	public GGStatus(int status){
		this.status = status;
	}
	
	/**
	 * Konstruktor statusu.
	 * @param status status
	 * @param opis opis tekstowy (maks. 40 znakow)
	 */
	public GGStatus(int status, String opis) {
		// TODO implement
	}
	
	/**
	 * Konstruktor statusu.
	 * @param status status
	 * @param opis opis tekstowy (maks. 40 znakow)
	 * @param czas czas powrotu
	 */
	public GGStatus(int status, String opis, Date czas) {
		// TODO implement
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
			case Status.BUSY:
				statusToSend = GG_STATUS_BUSY;
				break;
			case Status.ON_LINE:
				statusToSend = GG_STATUS_AVAIL;
				break;
			case Status.OFF_LINE:
				statusToSend = GG_STATUS_NOT_AVAIL;
				break;
			case Status.NOT_VISIBLE:
				statusToSend = GG_STATUS_INVISIBLE;
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
