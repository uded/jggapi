/*
 * Copyright (c) 2003-2005 <a href="http://jggapi.sourceforge.net/team-list.html">JGGApi Development Team</a> All Rights Reserved.
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package pl.mn.communicator;

import java.util.Date;

/**
 * Created on 2004-12-12
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: Status.java,v 1.3 2004-12-19 18:50:44 winnetou25 Exp $
 */
public class Status implements IStatus {

	private StatusType m_statusType = StatusType.ONLINE;
	private boolean m_friendsOnly = false;
	private boolean m_blocked = false;
	
	/** Status description */
	private String m_description = null;

	/** Return time */
	private Date m_returnTime = null;

	private byte[] m_remoteIP = new byte[]{0,0,0,0};
	private int m_remotePort = 1555;
	
	private byte m_imageSize = -1;
	private byte m_version = -1;
	private int m_descriptionSize = 0;

	private boolean m_supportsVoiceCommunication = false;
	private boolean m_supportsDirectCommunication = false;
	private boolean m_areWeInRemoteUserBuddyList = true;
	private boolean m_isUserBehindFirewall = false;
	
	public Status(StatusType statusType, String description, Date returnDate) {
		if (statusType == null) throw new NullPointerException("statusType cannot be null");
		m_statusType = statusType;
		m_description = description;
		m_returnTime = returnDate;
	}

	public Status(StatusType statusType, String description) {
		this(statusType, description, null);
	}
	public Status(StatusType statusType) {
		this(statusType, null, null);
	}

	/**
	 * @see pl.mn.communicator.IStatus#getStatusType()
	 */
	public StatusType getStatusType() {
		return m_statusType;
	}

	/**
	 * @see pl.mn.communicator.IStatus#setStatusType(int)
	 */
	public void setStatusType(StatusType status) {
		m_statusType = status;
	}

	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return m_description;
	}

	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		m_description = description;
	}

	/**
	 * @return Returns the returnTime.
	 */
	public Date getReturnDate() {
		return m_returnTime;
	}

	/**
	 * @param returnTime The returnTime to set.
	 */
	public void setReturnDate(Date returnTime) {
		if (returnTime == null)
			throw new NullPointerException("returnTime cannot be null");
		m_returnTime = returnTime;
	}

	/**
	 * @see pl.mn.communicator.IStatus#isDescriptionSet()
	 */
	public boolean isDescriptionSet() {
		return m_description != null;
	}

	/**
	 * @see pl.mn.communicator.IStatus#isReturnDateSet()
	 */
	public boolean isReturnDateSet() {
		return m_returnTime != null;
	}
	
	/**
	 * @see pl.mn.communicator.IStatus#isFriendsOnly()
	 */
	public boolean isFriendsOnly() {
		return m_friendsOnly;
	}
	
	/**
	 * @see pl.mn.communicator.IStatus#setFriendsOnly(boolean)
	 */
	public void setFriendsOnly(boolean bool) {
		m_friendsOnly = bool;
	}
	
	/**
	 * @see pl.mn.communicator.IStatus#setBlocked(boolean)
	 */
	public void setBlocked(boolean blocked) {
		m_blocked = blocked;
	}
	
	/**
	 * @see pl.mn.communicator.IStatus#isBlocked()
	 */
	public boolean isBlocked() {
		return m_blocked;
	}

	/**
	 * @see pl.mn.communicator.IStatus#getRemoteIP()
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
	 * @see pl.mn.communicator.IStatus#getRemotePort()
	 */
	public int getRemotePort() {
		return m_remotePort;
	}

	public void setRemotePort(int remotePort) {
		if (remotePort < 0 || remotePort > 65535) throw new IllegalArgumentException("Incorrect remotePort number");
		m_remotePort = remotePort;
	}
	
	/**
	 * @see pl.mn.communicator.IStatus#getGGVersion()
	 */
	public byte getGGVersion() {
		return m_version;
	}
	
	public void setGGVersion(byte version) {
		m_version = version;
	}

	/**
	 * @see pl.mn.communicator.IStatus#getImageSize()
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
	 * @see pl.mn.communicator.IStatus#supportsDirectCommunication()
	 */
	public boolean supportsDirectCommunication() {
		return m_supportsDirectCommunication;
	}

	public void setSupportsDirectCommunication(boolean supportsDirectCommunication) {
		m_supportsDirectCommunication = supportsDirectCommunication;
	}
	
	/**
	 * @see pl.mn.communicator.IStatus#areWeInRemoteUserBuddyList()
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
	
	/**
	 * @see pl.mn.communicator.IStatus#isUserBehingFirewall()
	 */
	public boolean isUserBehindFirewall() {
		return m_isUserBehindFirewall;
	}
	
	public void setUserBehindFirewall(boolean userBehingFirewall) {
		m_isUserBehindFirewall = true;
	}
	
}
