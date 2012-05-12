package pl.radical.open.gg.config;

import pl.radical.open.gg.GGConfiguration;
import pl.radical.open.gg.IGGConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on 2005-05-09
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public abstract class AbstractGGPropertiesConfiguration implements IGGConfiguration {
	private final Logger log = LoggerFactory.getLogger(getClass()); // NOPMD by LRzanek on 03.05.10 16:35

	private IGGConfiguration configuration = new GGConfiguration();

	private Properties properties = null;
	protected String fileName = null;

	public AbstractGGPropertiesConfiguration(final String fileName, final IGGConfiguration configuration) throws IOException {
		this(fileName);
		this.configuration = configuration;
	}

	public AbstractGGPropertiesConfiguration(final String fileName) throws IOException {
		if (fileName == null) {
			log.error("Filename is required!");
			throw new IOException("Filename is required!");
		}
		this.fileName = fileName;
		properties = createProperties();
	}

	/**
	 * @see pl.radical.open.gg.IGGConfiguration#getConnectionThreadSleepTimeInMiliseconds()
	 */
	public final int getConnectionThreadSleepTimeInMiliseconds() {
		final String connThreadSleepTime = String.valueOf(configuration.getConnectionThreadSleepTimeInMiliseconds());
		return Integer.valueOf(properties.getProperty("connection.thread.sleep.time", connThreadSleepTime));
	}

	/**
	 * @see pl.radical.open.gg.IGGConfiguration#getPingIntervalInMiliseconds()
	 */
	public final int getPingIntervalInMiliseconds() {
		final String pingInterval = String.valueOf(configuration.getPingIntervalInMiliseconds());
		return Integer.valueOf(properties.getProperty("ping.interval", pingInterval));
	}

	/**
	 * @see pl.radical.open.gg.IGGConfiguration#getSocketTimeoutInMiliseconds()
	 */
	public final int getSocketTimeoutInMiliseconds() {
		final String defaultSocketTimeout = String.valueOf(configuration.getSocketTimeoutInMiliseconds());
		return Integer.valueOf(properties.getProperty("socket.timeout", defaultSocketTimeout));
	}

	/**
	 * @see pl.radical.open.gg.IGGConfiguration#getRegistrationURL()
	 */
	public final String getRegistrationURL() {
		final String registrationURL = configuration.getRegistrationURL();
		return properties.getProperty("server.registration.url", registrationURL);
	}

	/**
	 * @see pl.radical.open.gg.IGGConfiguration#getSendPasswordURL()
	 */
	public final String getSendPasswordURL() {
		final String sendPasswordURL = configuration.getSendPasswordURL();
		return properties.getProperty("send.password.url", sendPasswordURL);
	}

	/**
	 * s
	 * 
	 * @see pl.radical.open.gg.IGGConfiguration#getServerLookupURL()
	 */
	public final String getServerLookupURL() {
		final String serverLookupURL = configuration.getServerLookupURL();
		return properties.getProperty("server.lookup.url", serverLookupURL);
	}

	/**
	 * @see pl.radical.open.gg.IGGConfiguration#getTokenRequestURL()
	 */
	public final String getTokenRequestURL() {
		final String tokenRequestURL = configuration.getServerLookupURL();
		return properties.getProperty("token.request.url", tokenRequestURL);
	}

	protected abstract Properties createProperties() throws IOException;

	@SuppressWarnings("unchecked")
	protected final InputStream getResourceAsStream(final ClassLoader loader, final String name) {
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
