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
	private int m_remotePort = 1555;
	
	private byte m_imageSize = -1;
	private byte m_version = -1;
	private int m_descriptionSize = 0;

	private boolean m_supportsVoiceCommunication = false;
	private boolean m_supportsDirectCommunication = false;
	private boolean m_areWeInRemoteUserBuddyList = false;
	
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
	
	public void setRemoteIP(byte[] remoteIP) {
		if (remoteIP == null) throw new NullPointerException("remoteIP cannot be null");
		if (remoteIP.length != 4) throw new IllegalArgumentException("remoteIP must contain 4 entries");
		m_remoteIP = remoteIP;
	}

	/**
	 * @see pl.mn.communicator.IStatus60#getRemotePort()
	 */
	public int getRemotePort() {
		return m_remotePort;
	}

	public void setRemotePort(int remotePort) {
		if (remotePort < 0 || remotePort > 65535) throw new IllegalArgumentException("Incorrect remotePort number");
		m_remotePort = remotePort;
	}
	
	/**
	 * @see pl.mn.communicator.IStatus60#getGGVersion()
	 */
	public byte getGGVersion() {
		return m_version;
	}
	
	public void setGGVersion(byte version) {
		m_version = version;
	}

	/**
	 * @see pl.mn.communicator.IStatus60#getImageSize()
	 */
	public byte getImageSize() {
		return m_imageSize;
	}

	public void setImageSize(byte imageSize) {
		if (imageSize < 0) throw new IllegalArgumentException("Illegal imageSize");
		m_imageSize = imageSize;
	}
	
	public boolean supportsVoiceCommunication() {
		return m_supportsVoiceCommunication;
	}
	
	public void setSupportsVoiceCommunication(boolean supportsVoiceCommunication) {
		m_supportsVoiceCommunication = supportsVoiceCommunication;
	}
	
	/**
	 * @see pl.mn.communicator.IStatus60#supportsDirectCommunication()
	 */
	public boolean supportsDirectCommunication() {
		return m_supportsDirectCommunication;
	}

	public void setSupportsDirectCommunication(boolean supportsDirectCommunication) {
		m_supportsDirectCommunication = supportsDirectCommunication;
	}
	
	/**
	 * @see pl.mn.communicator.IStatus60#areWeInRemoteUserBuddyList()
	 */
	public boolean areWeInRemoteUserBuddyList() {
		return m_areWeInRemoteUserBuddyList;
	}

	public void setAreWeInRemoteUserBuddyList(boolean areWeInRemoteUserBuddyList) {
		m_areWeInRemoteUserBuddyList = areWeInRemoteUserBuddyList;
	}
	
	/**
	 * @see pl.mn.communicator.IStatus#getDescription()
	 */
	public int getDescriptionSize() {
		return m_descriptionSize;
	}
	
	public void setDescriptionSize(int descriptionSize) {
		if (descriptionSize < 0) throw new IllegalArgumentException("descriptionSize cannot be less than 0");
		m_descriptionSize = descriptionSize;
	}
	
}
