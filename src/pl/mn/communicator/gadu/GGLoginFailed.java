/*
 * Created on 2004-12-11
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
public class GGLoginFailed implements GGIncomingPackage {

	public final static int GG_LOGIN_FAILED = 9;

	private static GGLoginFailed m_instance = null;
	
	private GGLoginFailed() {
		//prevent instant
	}
	
	/**
	 * @see pl.mn.communicator.gadu.GGIncomingPackage#getPacketType()
	 */
	public int getPacketType() {
		return GG_LOGIN_FAILED;
	}
	
	public static GGLoginFailed getInstance() {
		if (m_instance == null) {
			m_instance = new GGLoginFailed();
		}
		return m_instance;
	}

}
