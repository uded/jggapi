/*
 * Created on 2004-11-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pl.mn.communicator.gadu;

/**
 * @author mateusz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GGPong implements GGIncomingPackage {

	public final static int GG_PONG  = 0x0007;

	private static GGPong m_instance = null;
	
	private GGPong() {
		//private contructor
	}
	
	/**
	 * @see pl.mn.communicator.gadu.GGIncomingPackage#getPacketType()
	 */
	public int getPacketType() {
		return GG_PONG;
	}
	
	public static GGPong getInstance() {
		if (m_instance == null) {
			m_instance = new GGPong();
		}
		return m_instance;
	}

}
