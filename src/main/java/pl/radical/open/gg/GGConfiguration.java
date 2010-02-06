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

/**
 * Created on 2005-05-08
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGConfiguration.java,v 1.1 2005/11/05 23:34:52 winnetou25 Exp $
 */
public class GGConfiguration implements IGGConfiguration {

	public static final int DEFAULT_SOCKET_TIMEOUT = 25 * 1000;
	public static final int DEFAULT_PING_INTERVAL = 10 * 1000;
	public static final int DEFAULT_CONNECTION_THREAD_SLEEP_TIME = 100;

	private int m_pingIntervalInMiliseconds = DEFAULT_PING_INTERVAL;
	private int m_socketTimeoutInSeconds = DEFAULT_SOCKET_TIMEOUT;
	private int m_connectionTimeOutInMiliseconds = DEFAULT_CONNECTION_THREAD_SLEEP_TIME;

	private String m_serverLookupURL = "http://appmsg.gadu-gadu.pl/appsvc/appmsg.asp";
	private String m_serverRegistrationURL = "http://register.gadu-gadu.pl/appsvc/fmregister3.asp";
	private String m_tokenRequestURL = "http://register.gadu-gadu.pl/appsvc/regtoken.asp";
	private String m_sendPasswordURL = "http://retr.gadu-gadu.pl/appsvc/fmsendpwd3.asp";

	public int getPingIntervalInMiliseconds() {
		return m_pingIntervalInMiliseconds;
	}

	public int getSocketTimeoutInMiliseconds() {
		return m_socketTimeoutInSeconds;
	}

	public String getServerLookupURL() {
		return m_serverLookupURL;
	}

	/**
	 * @see pl.radical.open.gg.IGGConfiguration#getRegistrationURL()
	 */
	public String getRegistrationURL() {
		return m_serverRegistrationURL;
	}

	/**
	 * @see pl.radical.open.gg.IGGConfiguration#getTokenRequestURL()
	 */
	public String getTokenRequestURL() {
		return m_tokenRequestURL;
	}

	public String getSendPasswordURL() {
		return m_sendPasswordURL;
	}

	public int getConnectionThreadSleepTimeInMiliseconds() {
		return m_connectionTimeOutInMiliseconds;
	}

	public void setConnectionThreadSleepTimeInMiliseconds(final int connectionThreadSleepTimeInMilis) {
		m_connectionTimeOutInMiliseconds = connectionThreadSleepTimeInMilis;
	}

	public void setPingIntervalInMiliseconds(final int intervalInMiliseconds) {
		m_pingIntervalInMiliseconds = intervalInMiliseconds;
	}

	public void setServerLookupURL(final String url) {
		m_serverLookupURL = url;
	}

	public void setTokenRequestURL(final String url) {
		m_tokenRequestURL = url;
	}

	public void setSendPasswordURL(final String url) {
		m_sendPasswordURL = url;
	}

	public void setRegistrationURL(final String url) {
		m_serverRegistrationURL = url;
	}

	public void setSocketTimeoutInMiliseconds(final int timeoutInSeconds) {
		m_socketTimeoutInSeconds = timeoutInSeconds;
	}

}
