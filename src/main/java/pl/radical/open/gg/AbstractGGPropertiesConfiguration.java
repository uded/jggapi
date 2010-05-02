package pl.radical.open.gg;

import java.io.IOException;
import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on 2005-05-09
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public abstract class AbstractGGPropertiesConfiguration implements IGGConfiguration {
	private final Logger log = LoggerFactory.getLogger(getClass());

	private IGGConfiguration m_defaultGGConfiguration = new GGConfiguration();

	private Properties m_prop = null;
	protected String m_fileName = null;

	public AbstractGGPropertiesConfiguration(final String fileName, final IGGConfiguration configuration) throws IOException, InvalidPropertiesFormatException {
		this(fileName);
		m_defaultGGConfiguration = configuration;
	}

	public AbstractGGPropertiesConfiguration(final String fileName) throws IOException, InvalidPropertiesFormatException {
		if (fileName == null) {
			log.error("Filename is required!");
			throw new IOException("Filename is required!");
		}
		m_fileName = fileName;
		m_prop = createProperties();
	}

	/**
	 * @see pl.radical.open.gg.IGGConfiguration#getConnectionThreadSleepTimeInMiliseconds()
	 */
	public int getConnectionThreadSleepTimeInMiliseconds() {
		final String connectionThreadSleepTime = String.valueOf(m_defaultGGConfiguration.getConnectionThreadSleepTimeInMiliseconds());
		return Integer.valueOf(m_prop.getProperty("connection.thread.sleep.time", connectionThreadSleepTime));
	}

	/**
	 * @see pl.radical.open.gg.IGGConfiguration#getPingIntervalInMiliseconds()
	 */
	public int getPingIntervalInMiliseconds() {
		final String pingInterval = String.valueOf(m_defaultGGConfiguration.getPingIntervalInMiliseconds());
		return Integer.valueOf(m_prop.getProperty("ping.interval", pingInterval));
	}

	/**
	 * @see pl.radical.open.gg.IGGConfiguration#getSocketTimeoutInMiliseconds()
	 */
	public int getSocketTimeoutInMiliseconds() {
		final String defaultSocketTimeout = String.valueOf(m_defaultGGConfiguration.getSocketTimeoutInMiliseconds());
		return Integer.valueOf(m_prop.getProperty("socket.timeout", defaultSocketTimeout));
	}

	/**
	 * @see pl.radical.open.gg.IGGConfiguration#getRegistrationURL()
	 */
	public String getRegistrationURL() {
		final String registrationURL = m_defaultGGConfiguration.getRegistrationURL();
		return m_prop.getProperty("server.registration.url", registrationURL);
	}

	/**
	 * @see pl.radical.open.gg.IGGConfiguration#getSendPasswordURL()
	 */
	public String getSendPasswordURL() {
		final String sendPasswordURL = m_defaultGGConfiguration.getSendPasswordURL();
		return m_prop.getProperty("send.password.url", sendPasswordURL);
	}

	/**
	 * s
	 * 
	 * @see pl.radical.open.gg.IGGConfiguration#getServerLookupURL()
	 */
	public String getServerLookupURL() {
		final String serverLookupURL = m_defaultGGConfiguration.getServerLookupURL();
		return m_prop.getProperty("server.lookup.url", serverLookupURL);
	}

	/**
	 * @see pl.radical.open.gg.IGGConfiguration#getTokenRequestURL()
	 */
	public String getTokenRequestURL() {
		final String tokenRequestURL = m_defaultGGConfiguration.getServerLookupURL();
		return m_prop.getProperty("token.request.url", tokenRequestURL);
	}

	protected abstract Properties createProperties() throws IOException, InvalidPropertiesFormatException;

	@SuppressWarnings("unchecked")
	protected InputStream getResourceAsStream(final ClassLoader loader, final String name) {
		return (InputStream) AccessController.doPrivileged(new PrivilegedAction() {
			public Object run() {
				if (loader != null) {
					return loader.getResourceAsStream(name);
				} else {
					return ClassLoader.getSystemResourceAsStream(name);
				}
			}
		});
	}

}
