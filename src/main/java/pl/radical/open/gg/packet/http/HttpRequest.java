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

	protected final IGGConfiguration ggConfiguration;

	protected final HttpURLConnection huc;

	protected HttpRequest(final IGGConfiguration configuration) throws IOException {
		if (configuration == null) {
			throw new IllegalArgumentException("configuration cannot be null");
		}
		ggConfiguration = configuration;

		final URL url = new URL(getURL());
		huc = (HttpURLConnection) url.openConnection();
		huc.setConnectTimeout(ggConfiguration.getSocketTimeoutInMiliseconds());
		huc.setReadTimeout(ggConfiguration.getSocketTimeoutInMiliseconds());

		huc.setRequestMethod("POST");
		huc.setDoInput(true);
		if (wannaWrite()) {
			huc.setDoOutput(true);
		}
		huc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		huc.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows 98)");
	}

	public HttpURLConnection connect() throws IOException {
		huc.setRequestProperty("Content-Length", String.valueOf(getRequestBody().length()));
		huc.connect();

		return huc;
	}

	public HttpURLConnection sendRequest() throws IOException {
		if (wannaWrite()) {
			final PrintWriter out = new PrintWriter(huc.getOutputStream(), true);

			out.println(getRequestBody());
			out.close();
		}

		return huc;
	}

	public HttpURLConnection disconnect() {
		if (huc == null) {
			throw new IllegalStateException("must call connect() and sendRequest() first");
		}
		huc.disconnect();

		return huc;
	}

	public abstract HttpResponse getResponse() throws IOException;

	protected abstract String getURL();

	protected abstract String getRequestBody() throws UnsupportedEncodingException;

	protected abstract boolean wannaWrite();

}
