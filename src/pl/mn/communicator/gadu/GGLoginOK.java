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
public class GGLoginOK implements GGIncomingPackage {

	private static GGLoginOK m_instance = null;
	
	public final static int GG_LOGIN_OK = 3;

	private GGLoginOK() {
		//prevent instant
	}
	
	/**
	 * @see pl.mn.communicator.gadu.GGIncomingPackage#getPacketType()
	 */
	public int getPacketType() {
		return GG_LOGIN_OK;
	}
	
	public static GGLoginOK getInstance() {
		if (m_instance == null) {
			m_instance = new GGLoginOK();
		}
		return m_instance;
	}

}
