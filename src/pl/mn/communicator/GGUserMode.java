/*
 * Created on 2004-12-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pl.mn.communicator;

/**
 * @author mateusz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GGUserMode {

	private String m_type;
	
	private GGUserMode(String type) {
		m_type = type;
	}

	public final static GGUserMode BUDDY = new GGUserMode("user_mode_buddy");
	public final static GGUserMode FRIEND = new GGUserMode("user_mode_friend");
	public final static GGUserMode BLOCKED = new GGUserMode("user_mode_blocked");

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return m_type;
	}
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return m_type.hashCode();
	}
	
}
