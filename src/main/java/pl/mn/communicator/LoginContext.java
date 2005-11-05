/*
 * Copyright (c) 2003-2005 JGGApi Development Team. All Rights Reserved.
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

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class represents information that will be used during login process.
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: LoginContext.java,v 1.1 2005-11-05 23:34:52 winnetou25 Exp $
 */
public final class LoginContext {
	
	private static final Log LOGGER = LogFactory.getLog(LoginContext.class);

	/** the uin that will be used during login*/
	private int m_uin = -1;

	/** password that will be used during login */
	private String m_password = null;

	/** Initial status */
	private ILocalStatus m_localStatus = new LocalStatus(StatusType.ONLINE);
	
	/** The list of users that we are intrested in */
	private Collection m_monitoredUsers = new ArrayList();

	/** the max image size */
    private byte m_imageSize = 64;
    
    /** the local IP of the user */
    private byte[] m_localIP = null;
    
    /** the local port of the client */
    private int m_localPort = -1;
    
    /** The external IP of the client */
    private byte[] m_externalIP = null;
    
    /** the external port of the client */
    private int m_externalPort = -1;
    
    public LoginContext(int uin, String password) {
    	if (uin < 0) throw new IllegalArgumentException("uin cannot be less than 0");
    	if (password == null) throw new NullPointerException("password cannot be null");
    	m_uin = uin;
    	m_password = password;
    }
    
    public String getPassword() {
        return m_password;
    }

    public int getUin() {
        return m_uin;
    }

    public void setPassword(String password) {
    	if (password == null) throw new NullPointerException("password cannot be null");
        m_password = password;
    }

    public void setUin(int uin) {
    	if (uin < 0) throw new IllegalArgumentException("uin must be a positive number");
        m_uin = uin;
    }
 
    public void setStatus(ILocalStatus localStatus) {
    	if (localStatus == null) throw new NullPointerException("localStatus cannot be null");
    	m_localStatus = localStatus;
    }
    
    public ILocalStatus getStatus() {
    	return m_localStatus;
    }
    
    public void setImageSize(byte imageSize) {
    	if (imageSize < 0) throw new IllegalArgumentException("imageSize cannot be less than 0");
    	m_imageSize = imageSize;
    }
    
    public byte getImageSize() {
    	return m_imageSize;
    }
    
	public byte[] getExternalIP() {
		return m_externalIP;
	}

	public void setExternalIP(byte[] externalIP) {
		if (externalIP == null) throw new NullPointerException("externalIP cannot be null");
		if (externalIP.length == 4) throw new IllegalArgumentException("Incorrect address.");
		m_externalIP = externalIP;
	}
	
	public void setExternalPort(int externalPort) {
		if (externalPort < 0) throw new IllegalArgumentException("externalPort cannot be less than 0");
		m_externalPort = externalPort;
	}

	public int getExternalPort() {
		return m_externalPort;
	}
	
	public byte[] getLocalIP() {
		return m_localIP;
	}

	public void setLocalIP(byte[] localIP) {
		if (localIP == null) throw new NullPointerException("localIP cannot be null");
		m_localIP = localIP;
		if (localIP.length == 4) throw new IllegalArgumentException("Incorrect address.");
	}
	
	public void setLocalPort(int localPort) {
		if (localPort < 0) throw new IllegalArgumentException("localPort cannot be less than 0");
		m_localPort = localPort;
	}
	
	public int getLocalPort() {
		return m_localPort;
	}
	
	public Collection getMonitoredUsers() {
		return m_monitoredUsers;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("LoginContext[");
		buffer.append("uin="+m_uin);
		buffer.append(",password=xxx(masking)]");
		
		return buffer.toString();
	}
	
}
