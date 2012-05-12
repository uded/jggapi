package pl.radical.open.gg;

import pl.radical.open.gg.config.SimplePropertiesGGConfiguration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the factory class that helps developers to create a new instance of a session class.
 * <p>
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class SessionFactory {

	private static final Logger LOG = LoggerFactory.getLogger(SessionFactory.class);

	public static ISession createSession() throws GGException {
		try {
			final IGGConfiguration configuration = SimplePropertiesGGConfiguration.createSimplePropertiesGGConfiguration();
			return new Session(configuration);
		} catch (final Exception ex) {
			LOG.warn("Unable to load jggapi.properties!", ex);
			LOG.warn("Falling back to default properties.");
			return new Session();
		}
	}

}
