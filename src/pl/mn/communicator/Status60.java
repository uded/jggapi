/*
 * Created on 2004-12-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pl.mn.communicator;

import java.util.Date;

/**
 * @author mateusz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Status60 extends Status implements IStatus60 {

	private byte[] m_remoteIP = new byte[]{0,0,0,0};
	
	public Status60(StatusType statusType, String description, Date returnDate) {
		super(statusType, description, returnDate);
	}

	public Status60(StatusType statusType, String description) {
		super(statusType, description);
	}
	public Status60(StatusType statusType) {
		super(statusType);
	}

	/**
	 * @see pl.mn.communicator.IStatus60#getRemoteIP()
	 */
	public byte[] getRemoteIP() {
		return m_remoteIP;
	}

	/**
	 * @see pl.mn.communicator.IStatus60#getRemotePort()
	 */
	public int getRemotePort() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @see pl.mn.communicator.IStatus60#getGGVersion()
	 */
	public byte getGGVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @see pl.mn.communicator.IStatus60#getImageSize()
	 */
	public byte getImageSize() {
		// TODO Auto-generated method stub
		return 0;
	}

}
