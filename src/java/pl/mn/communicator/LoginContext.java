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
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: LoginContext.java,v 1.4 2004-12-19 17:14:36 winnetou25 Exp $
 */
public final class LoginContext {
	
	private static Log logger = LogFactory.getLog(LoginContext.class);

	private int m_uin = -1;

	private String m_password = null;

	private Status m_localStatus = new Status(StatusType.ONLINE);
	
	private Collection m_monitoredUsers = new ArrayList();
	
    private byte m_imageSize = 64;
    
    private byte[] m_localIP = new byte[] {(byte) 0, (byte)0, (byte) 0, (byte) 0};
    private int m_localPort = 1550;
    private byte[] m_externalIP = new byte[] {(byte) 0, (byte)0, (byte) 0, (byte) 0};
    private int m_externalPort = 1550;
    
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
 
    public void setStatus(Status localStatus) {
    	if (localStatus == null) throw new NullPointerException("localStatus cannot be null");
    	m_localStatus = localStatus;
    }
    
    public Status getStatus() {
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
	
}
