/*
 * Created on 2004-12-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pl.mn.communicator.gadu;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author mateusz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GGUserListReply implements GGIncomingPackage {

	public final static int GG_USERLIST_REPLY  = 0x0010;
	
	private final static int GG_USERLIST_PUT_REPLY  = 0x00 ;        /* początek eksportu listy */
	private final static int GG_USERLIST_PUT_MORE_REPLY  = 0x02;    /* kontynuacja */

	private final static int GG_USERLIST_GET_MORE_REPLY  = 0x04;    /* początek importu listy */
	private final static int GG_USERLIST_GET_REPLY = 0x06; 			/* ostatnia część importu */ 
	
	private byte m_type;
	
	private Collection m_users;
	
	public GGUserListReply(byte[] data) {
		m_type = (byte) data[0];
		if (isGetMoreReply()) {
			m_users = createUsersCollection();
		}
	}
	
	/**
	 * @see pl.mn.communicator.gadu.GGIncomingPackage#getPacketType()
	 */
	public int getPacketType() {
		return GG_USERLIST_PUT_REPLY;
	}
	
	private Collection createUsersCollection() {
		ArrayList list = new ArrayList();
		//TODO
		
		return list;
	}
	
	public Collection getContactList() {
		return m_users;
	}
	
	public boolean isPutReply() {
		return m_type == GG_USERLIST_PUT_REPLY;
	}
	
	public boolean isPutMoreReply() {
		return m_type == GG_USERLIST_PUT_MORE_REPLY;
	}
	
	public boolean isGetReply() {
		return m_type == GG_USERLIST_GET_REPLY;
	}
	
	public boolean isGetMoreReply() {
		return m_type == GG_USERLIST_GET_MORE_REPLY;
	}
	
}
