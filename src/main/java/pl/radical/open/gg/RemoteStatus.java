/*
 * Copyright (c) 2003-2005 JGGApi Development Team. All Rights Reserved. This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version. This program is distributed in
 * the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a
 * copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package pl.radical.open.gg;

import java.util.Date;

/**
 * The default implementation of <code>IRemoteStatus</code>
 * <p>
 * Created on 2004-12-21
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: RemoteStatus.java,v 1.1 2005/11/05 23:34:52 winnetou25 Exp $
 */
public class RemoteStatus extends AbstractStatus implements IRemoteStatus {

	private boolean m_blocked = false;

	private byte[] m_remoteIP = null;
	private int m_remotePort = -1;

	private int m_imageSize = -1;
	private int m_version = -1;
	private int m_descriptionSize = -1;

	private boolean m_supportsVoiceCommunication = false;
	private boolean m_supportsDirectCommunication = false;
	private boolean m_areWeInRemoteUserBuddyList = false;
	private boolean m_isUserBehindFirewall = false;

	public RemoteStatus(final StatusType statusType) {
		super(statusType);
	}

	public RemoteStatus(final StatusType statusType, final String description) {
		super(statusType, description);
	}

	public RemoteStatus(final StatusType statusType, final String description, final Date returnDate) {
		super(statusType, description, returnDate);
	}

	public void setBlocked(final boolean blocked) {
		m_blocked = blocked;
	}

	/**
	 * @see pl.radical.open.gg.IRemoteStatus#isBlocked()
	 */
	public boolean isBlocked() {
		return m_blocked;
	}

	/**
	 * @see pl.radical.open.gg.IRemoteStatus#getRemoteIP()
	 */
	public byte[] getRemoteIP() {
		return m_remoteIP;
	}

	public void setRemoteIP(final byte[] remoteIP) {
		if (remoteIP == null) {
			throw new NullPointerException("remoteIP cannot be null");
		}
		if (remoteIP.length != 4) {
			throw new IllegalArgumentException("remoteIP must contain 4 entries");
		}
		m_remoteIP = remoteIP;
	}

	/**
	 * @see pl.radical.open.gg.IRemoteStatus#getRemotePort()
	 */
	public int getRemotePort() {
		return m_remotePort;
	}

	public void setRemotePort(final int remotePort) {
		if (remotePort < 0 || remotePort > 65535) {
			throw new IllegalArgumentException("Incorrect remotePort number");
		}
		m_remotePort = remotePort;
	}

	/**
	 * @see pl.radical.open.gg.IRemoteStatus#getGGVersion()
	 */
	public int getGGVersion() {
		return m_version;
	}

	public void setGGVersion(final int version) {
		m_version = version;
	}

	/**
	 * @see pl.radical.open.gg.IRemoteStatus#getImageSize()
	 */
	public int getImageSize() {
		return m_imageSize;
	}

	public void setImageSize(final int imageSize) {
		if (imageSize < 0) {
			throw new IllegalArgumentException("Illegal imageSize");
		}
		m_imageSize = imageSize;
	}

	public boolean supportsVoiceCommunication() {
		return m_supportsVoiceCommunication;
	}

	public void setSupportsVoiceCommunication(final boolean supportsVoiceCommunication) {
		m_supportsVoiceCommunication = supportsVoiceCommunication;
	}

	/**
	 * @see pl.radical.open.gg.IRemoteStatus#supportsDirectCommunication()
	 */
	public boolean supportsDirectCommunication() {
		return m_supportsDirectCommunication;
	}

	public void setSupportsDirectCommunication(final boolean supportsDirectCommunication) {
		m_supportsDirectCommunication = supportsDirectCommunication;
	}

	/**
	 * @see pl.radical.open.gg.IRemoteStatus#areWeInRemoteUserBuddyList()
	 */
	public boolean areWeInRemoteUserBuddyList() {
		return m_areWeInRemoteUserBuddyList;
	}

	public void setAreWeInRemoteUserBuddyList(final boolean areWeInRemoteUserBuddyList) {
		m_areWeInRemoteUserBuddyList = areWeInRemoteUserBuddyList;
	}

	/**
	 * @see pl.radical.open.gg.IRemoteStatus#getDescription()
	 */
	public int getDescriptionSize() {
		return m_descriptionSize;
	}

	public void setDescriptionSize(final int descriptionSize) {
		if (descriptionSize < 0) {
			throw new IllegalArgumentException("descriptionSize cannot be less than 0");
		}
		m_descriptionSize = descriptionSize;
	}

	/**
	 * @see pl.radical.open.gg.IRemoteStatus#isUserBehindFirewall()
	 */
	public boolean isUserBehindFirewall() {
		return m_isUserBehindFirewall;
	}

	public void setUserBehindFirewall(final boolean userBehingFirewall) {
		m_isUserBehindFirewall = true;
	}

}
