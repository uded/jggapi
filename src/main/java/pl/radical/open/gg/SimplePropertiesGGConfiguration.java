package pl.radical.open.gg;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created on 2005-05-09
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class SimplePropertiesGGConfiguration extends AbstractGGPropertiesConfiguration {

	public SimplePropertiesGGConfiguration(final String fileName, final IGGConfiguration configuration) throws IOException {
		super(fileName, configuration);
	}

	public SimplePropertiesGGConfiguration(final String fileName) throws IOException {
		super(fileName);
	}

	@Override
	protected Properties createProperties() throws IOException {
		final ClassLoader cl = Thread.currentThread().getContextClassLoader();
		final InputStream is = getResourceAsStream(cl, fileName);

		final Properties props = new Properties();
		props.load(is);

		return props;
	}

	public static SimplePropertiesGGConfiguration createSimplePropertiesGGConfiguration() throws IOException {
		final SimplePropertiesGGConfiguration configuration = new SimplePropertiesGGConfiguration("jggapi.properties");

		return configuration;
	}

}
