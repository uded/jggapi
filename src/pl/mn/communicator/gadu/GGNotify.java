package pl.mn.communicator.gadu;

import pl.mn.communicator.IUser;

/**
 * @author mnaglik
 */
class GGNotify implements GGOutgoingPackage {
	public static final int GG_USER_OFFLINE = 0x01;
	public static final int GG_USER_NORMAL = 0x03;
	public static final int GG_USER_BLOCKED = 0x04;
	
	private IUser[] users;
	
	GGNotify(IUser[] users) {
		this.users = users;
	}
	
	/**
	 * @see pl.mn.communicator.gadu.GGOutgoingPackage#getHeader()
	 */
	public int getHeader() {
		return 0x10;
	}

	/**
	 * @see pl.mn.communicator.gadu.GGOutgoingPackage#getLength()
	 */
	public int getLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @see pl.mn.communicator.gadu.GGOutgoingPackage#getContents()
	 */
	public byte[] getContents() {
		// TODO Auto-generated method stub
		return null;
	}

}
