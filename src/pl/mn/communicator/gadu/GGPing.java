package pl.mn.communicator.gadu;

/**
 * @version $Revision: 1.2 $
 * @author mnaglik
 */
class GGPing implements GGOutgoingPackage {
	private byte[] data;
	private static GGPing ggPing = new GGPing();

	private GGPing(){
		// SINGLETON use getPing instead
	}
	/**
	 * @see pl.mn.communicator.gadu.GGOutgoingPackage#getHeader()
	 */
	public int getHeader() {
		return 0x08;
	}

	/**
	 * @see pl.mn.communicator.gadu.GGOutgoingPackage#getLength()
	 */
	public int getLength() {
		return 0x00;
	}
	
	/**
	 * @see pl.mn.communicator.gadu.GGOutgoingPackage#getContents()
	 */
	public byte[] getContents() {
		return new byte[0];
	}

	public static GGPing getPing() {
		return ggPing;
	}
}
