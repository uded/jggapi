package pl.radical.open.gg;

import java.io.IOException;
import java.io.InputStream;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

/**
 * Created on 2005-05-09
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class SimplePropertiesGGConfiguration extends AbstractGGPropertiesConfiguration {

	public SimplePropertiesGGConfiguration(final String fileName, final IGGConfiguration configuration) throws IOException, InvalidPropertiesFormatException {
		super(fileName, configuration);
	}

	public SimplePropertiesGGConfiguration(final String fileName) throws IOException, InvalidPropertiesFormatException {
		super(fileName);
	}

	@Override
	protected Properties createProperties() throws InvalidPropertiesFormatException, IOException {
		final ClassLoader cl = Thread.currentThread().getContextClassLoader();
		final InputStream is = getResourceAsStream(cl, m_fileName);

		final Properties props = new Properties();
		props.load(is);

		return props;
	}

	public static SimplePropertiesGGConfiguration createSimplePropertiesGGConfiguration() throws InvalidPropertiesFormatException, IOException {
		final SimplePropertiesGGConfiguration configuration = new SimplePropertiesGGConfiguration("jggapi.properties");

		return configuration;
	}

}
