package pl.mn.communicator.gadu;

/**
 * Wiadomosc otrzymywana zaraz po polaczeniu z serwerem gg
 * zawiera seed (int) potrzebny do zakodowania hasla
 * 
 * @version $Revision: 1.2 $
 * @author mnaglik
 */
class GGWelcome {
	private int seed;

	/**
	 * Constructor for Welcome.
	 */
	public GGWelcome(byte[] data) {
		this.seed = GGConversion.byteToInt(data);
	}

	public int getSeed() {
		return seed;
	}
}
