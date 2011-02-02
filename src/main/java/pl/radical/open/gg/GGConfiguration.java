package pl.radical.open.gg;

/**
 * Created on 2005-05-08
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class GGConfiguration implements IGGConfiguration {

	public static final int DEFAULT_SOCKET_TIMEOUT = 25 * 1000;
	public static final int DEFAULT_PING_INTERVAL = 10 * 1000;
	public static final int DEFAULT_CONN_THREAD_SLEEP_TIME = 100;

	private int pingIntervalInMiliseconds = DEFAULT_PING_INTERVAL;
	private int socketTimeoutInSeconds = DEFAULT_SOCKET_TIMEOUT;
	private int connectionTimeOutInMiliseconds = DEFAULT_CONN_THREAD_SLEEP_TIME;

	private String serverLookupURL = "http://appmsg.gadu-gadu.pl/appsvc/appmsg.asp";
	private String serverRegistrationURL = "http://register.gadu-gadu.pl/appsvc/fmregister3.asp";
	private String tokenRequestURL = "http://register.gadu-gadu.pl/appsvc/regtoken.asp";
	private String sendPasswordURL = "http://retr.gadu-gadu.pl/appsvc/fmsendpwd3.asp";

	@Override
    public int getPingIntervalInMiliseconds() {
		return pingIntervalInMiliseconds;
	}

	@Override
    public int getSocketTimeoutInMiliseconds() {
		return socketTimeoutInSeconds;
	}

	@Override
    public String getServerLookupURL() {
		return serverLookupURL;
	}

	/**
	 * @see pl.radical.open.gg.IGGConfiguration#getRegistrationURL()
	 */
	@Override
    public String getRegistrationURL() {
		return serverRegistrationURL;
	}

	/**
	 * @see pl.radical.open.gg.IGGConfiguration#getTokenRequestURL()
	 */
	@Override
    public String getTokenRequestURL() {
		return tokenRequestURL;
	}

	@Override
    public String getSendPasswordURL() {
		return sendPasswordURL;
	}

	@Override
    public int getConnectionThreadSleepTimeInMiliseconds() {
		return connectionTimeOutInMiliseconds;
	}

}
