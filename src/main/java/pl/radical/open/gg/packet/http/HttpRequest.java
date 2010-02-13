package pl.radical.open.gg.packet.http;

import pl.radical.open.gg.IGGConfiguration;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created on 2005-01-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public abstract class HttpRequest {

	protected final IGGConfiguration m_ggconfiguration;

	protected final HttpURLConnection m_huc;

	protected HttpRequest(final IGGConfiguration configuration) throws IOException {
		if (configuration == null) {
			throw new IllegalArgumentException("configuration cannot be null");
		}
		m_ggconfiguration = configuration;

		final URL url = new URL(getURL());
		m_huc = (HttpURLConnection) url.openConnection();
		// available only in JDK 1.5
		// m_huc.setConnectTimeout(m_ggconfiguration.getSocketTimeoutInMiliseconds());
		// m_huc.setReadTimeout(m_ggconfiguration.getSocketTimeoutInMiliseconds());

		m_huc.setRequestMethod("POST");
		m_huc.setDoInput(true);
		if (wannaWrite()) {
			m_huc.setDoOutput(true);
		}
		m_huc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		m_huc.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows 98)");
	}

	public HttpURLConnection connect() throws IOException {
		m_huc.setRequestProperty("Content-Length", String.valueOf(getRequestBody().length()));
		m_huc.connect();

		return m_huc;
	}

	public HttpURLConnection sendRequest() throws IOException {
		if (wannaWrite()) {
			final PrintWriter out = new PrintWriter(m_huc.getOutputStream(), true);

			out.println(getRequestBody());
			out.close();
		}

		return m_huc;
	}

	public HttpURLConnection disconnect() {
		if (m_huc == null) {
			throw new IllegalStateException("must call connect() and sendRequest() first");
		}
		m_huc.disconnect();

		return m_huc;
	}

	public abstract HttpResponse getResponse() throws IOException;

	protected abstract String getURL();

	protected abstract String getRequestBody() throws UnsupportedEncodingException;

	protected abstract boolean wannaWrite();

}
