package pl.radical.open.gg;

/**
 * Created on 2005-05-08
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
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
