package pl.radical.open.gg.packet.http;

import pl.radical.open.gg.GGToken;
import pl.radical.open.gg.IGGConfiguration;
import pl.radical.open.gg.utils.GGUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;

/**
 * Created on 2005-01-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class GGTokenRequest extends HttpRequest {

	public GGTokenRequest(final IGGConfiguration configuration) throws IOException {
		super(configuration);
		m_huc.setRequestProperty("Accept", "image/gif, image/jpeg, */*");
		m_huc.setRequestProperty("Accept-Language", "pl");
	}

	/**
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 * @see pl.radical.open.gg.packet.http.HttpRequest#getResponse()
	 */
	@Override
	public HttpResponse getResponse() throws IOException {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(m_huc.getInputStream(), GGUtils.WINDOWS_ENCODING));
		final String line1 = reader.readLine();
		final String line2 = reader.readLine();
		final String line3 = reader.readLine();

		return new GGTokenResponse(line1, line2, line3);
	}

	/**
	 * @see pl.radical.open.gg.packet.http.HttpRequest#getURL()
	 */
	@Override
	protected String getURL() {
		return m_ggconfiguration.getTokenRequestURL();
		// return "http://register.gadu-gadu.pl/appsvc/regtoken.asp";
	}

	/**
	 * @throws UnsupportedEncodingException
	 * @see pl.radical.open.gg.packet.http.HttpRequest#getRequestBody()
	 */
	@Override
	protected String getRequestBody() throws UnsupportedEncodingException {
		return "";
	}

	/**
	 * @see pl.radical.open.gg.packet.http.HttpRequest#wannaWrite()
	 */
	@Override
	protected boolean wannaWrite() {
		return false;
	}

	public static class GGTokenResponse extends HttpResponse {

		private String m_responseLine1 = null;
		private String m_responseLine2 = null;
		private String m_responseLine3 = null;

		private GGTokenResponse(final String responseLine1, final String responseLine2, final String responseLine3) {
			m_responseLine1 = responseLine1;
			m_responseLine2 = responseLine2;
			m_responseLine3 = responseLine3;
		}

		/**
		 * @see pl.radical.open.gg.packet.http.HttpResponse#isErrorResponse()
		 */
		@Override
		public boolean isOKResponse() {
			return m_responseLine1 != null && m_responseLine2 != null && m_responseLine3 != null;
		}

		public GGToken getGGToken() {
			final GGToken token = new GGToken();

			final StringTokenizer tokenizer = new StringTokenizer(m_responseLine1, " ");

			final String widthString = tokenizer.nextToken();
			final String heightString = tokenizer.nextToken();
			final String lengthString = tokenizer.nextToken();

			token.setImageWidth(Integer.parseInt(widthString));
			token.setImageHeight(Integer.parseInt(heightString));
			token.setTokenLength(Integer.parseInt(lengthString));
			token.setTokenID(m_responseLine2.trim());
			token.setTokenURL(m_responseLine3.trim());

			return token;
		}

		/**
		 * @see pl.radical.open.gg.packet.http.HttpResponse#getResponseMessage()
		 */
		@Override
		public String getResponseMessage() {
			return m_responseLine1 + "\n" + m_responseLine2 + "\n" + m_responseLine3;
		}

	}

}
