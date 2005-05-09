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

/**
 * Created on 2005-05-08
 * 
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGConfiguration.java,v 1.2 2005-05-09 20:59:08 winnetou25 Exp $
 */
public class GGConfiguration implements IGGConfiguration {

	public static final int DEFAULT_SOCKET_TIMEOUT = 25*1000;
	public static final int DEFAULT_PING_INTERVAL = 10*1000;
	public static final int DEFAULT_CONNECTION_THREAD_SLEEP_TIME = 100;

	private int m_pingIntervalInMiliseconds = DEFAULT_PING_INTERVAL;
	private int m_socketTimeoutInSeconds = DEFAULT_SOCKET_TIMEOUT;
	private int m_connectionTimeOutInMiliseconds = DEFAULT_CONNECTION_THREAD_SLEEP_TIME;
	
	private String m_serverLookupURL = "http://appmsg.gadu-gadu.pl/appsvc/appmsg.asp";
	private String m_serverRegistrationURL = "http://register.gadu-gadu.pl/appsvc/fmregister3.asp";
	private String m_tokenRequestURL = "http://register.gadu-gadu.pl/appsvc/regtoken.asp";
	private String m_sendPasswordURL = "http://retr.gadu-gadu.pl/appsvc/fmsendpwd3.asp";
	
	public GGConfiguration() {
	}

	public int getPingIntervalInMiliseconds() {
		return m_pingIntervalInMiliseconds;
	}
	
	public void setPingIntervalInMiliseconds(int intervalInMiliseconds) {
		m_pingIntervalInMiliseconds = intervalInMiliseconds;
	}

	public void setSocketTimeoutInMiliseconds(int timeoutInSeconds) {
		m_socketTimeoutInSeconds = timeoutInSeconds;
	}
	
	public int getSocketTimeoutInMiliseconds() {
		return m_socketTimeoutInSeconds;
	}

	public String getServerLookupURL() {
		return m_serverLookupURL;
	}
	
	/**
     * @see pl.mn.communicator.IGGConfiguration#getRegistrationURL()
     */
    public String getRegistrationURL() {
        return m_serverRegistrationURL;
    }
    
    /**
     * @see pl.mn.communicator.IGGConfiguration#setRegistrationURL(java.lang.String)
     */
    public void setRegistrationURL(String url) {
        m_serverRegistrationURL = url;
    }
    
    /**
     * @see pl.mn.communicator.IGGConfiguration#getTokenRequestURL()
     */
    public String getTokenRequestURL() {
        return m_tokenRequestURL;
    }
    
    public String getSendPasswordURL() {
        return m_sendPasswordURL;
    }
    
    public void setSendPasswordURL(String url) {
        m_sendPasswordURL = url;
    }
    
    /**
     * @see pl.mn.communicator.IGGConfiguration#setTokenRequest(java.lang.String)
     */
    public void setTokenRequestURL(String url) {
        m_tokenRequestURL = url;
    }
	
	public void setServerLookupURL(String url) {
		m_serverLookupURL = url;
	}
	
	public int getConnectionThreadSleepTimeInMiliseconds() {
		return m_connectionTimeOutInMiliseconds;
	}
	
	public void setConnectionThreadSleepTimeInMiliseconds(int connectionThreadSleepTimeInMilis) {
		m_connectionTimeOutInMiliseconds = connectionThreadSleepTimeInMilis;
	}
	
}
