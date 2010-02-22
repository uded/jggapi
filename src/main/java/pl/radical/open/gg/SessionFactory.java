package pl.radical.open.gg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on 2004-12-12 This is the factory class that helps developers to create a new instance of a session class.
 * <p>
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class SessionFactory {

	private final static Logger LOGGER = LoggerFactory.getLogger(SessionFactory.class);

	public static ISession createSession() {
		try {
			final IGGConfiguration configuration = SimplePropertiesGGConfiguration.createSimplePropertiesGGConfiguration();
			return new Session(configuration);
		} catch (final Exception ex) {
			LOGGER.warn("Unable to load jggapi.properties!", ex);
			LOGGER.warn("Falling back to default properties.");
			return new Session();
		}
	}

}
