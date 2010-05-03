package pl.radical.open.gg.packet.http;

import pl.radical.open.gg.IGGConfiguration;
import pl.radical.open.gg.dicts.Encoding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.StringTokenizer;

/**
 * Created on 2005-01-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class RegisterGGAccountRequest extends AbstractTokenRequest {

	private String m_email = null;
	private String m_password = null;

	// FIXME NullPointerException
	public RegisterGGAccountRequest(final IGGConfiguration configuration, final String email, final String password, final String tokenID, final String tokenVal) throws IOException {
		super(configuration, tokenID, tokenVal);
		if (email == null) {
			// FIXME Other exception instead?
			throw new IllegalArgumentException("email cannot be null");
		}
		if (password == null) {
			// FIXME Other exception instead?
			throw new IllegalArgumentException("password cannot be null");
		}
		m_email = email;
		m_password = password;
	}

	/**
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 * @see pl.radical.open.gg.packet.http.HttpRequest#getResponse()
	 */
	@Override
	public HttpResponse getResponse() throws IOException {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(huc.getInputStream(), Encoding.WINDOWS1250.getValue()));
		final String line = reader.readLine();

		return new RegisterGGAccountResponse(line);
	}

	/**
	 * @see pl.radical.open.gg.packet.http.HttpRequest#getURL()
	 */
	@Override
	protected String getURL() {
		return ggConfiguration.getRegistrationURL();
	}

	/**
	 * @throws UnsupportedEncodingException
	 * @see pl.radical.open.gg.packet.http.HttpRequest#getRequestBody()
	 */
	@Override
	protected String getRequestBody() throws UnsupportedEncodingException {
		final StringBuffer buffer = new StringBuffer();
		buffer.append("pwd=");
		buffer.append(URLEncoder.encode(m_password, Encoding.WINDOWS1250.getValue()));
		buffer.append('&');
		buffer.append("email=");
		buffer.append(URLEncoder.encode(m_email, Encoding.WINDOWS1250.getValue()));
		buffer.append('&');
		buffer.append("tokenid=");
		buffer.append(URLEncoder.encode(tokenID, Encoding.WINDOWS1250.getValue()));
		buffer.append('&');
		buffer.append("tokenval=");
		buffer.append(URLEncoder.encode(tokenVal, Encoding.WINDOWS1250.getValue()));
		buffer.append('&');
		buffer.append("code=");
		buffer.append(getHashCode(m_email, m_password));

		return buffer.toString();
	}

	/**
	 * @see pl.radical.open.gg.packet.http.HttpRequest#wannaWrite()
	 */
	@Override
	protected boolean wannaWrite() {
		return true;
	}

	public static class RegisterGGAccountResponse extends HttpResponse {

		private String m_responseString = null;

		public RegisterGGAccountResponse(final String responseString) {
			m_responseString = responseString;
		}

		/**
		 * @see pl.radical.open.gg.packet.http.HttpResponse#isErrorResponse()
		 */
		@Override
		public boolean isOKResponse() {
			return m_responseString.startsWith("reg_success");
		}

		/**
		 * @see pl.radical.open.gg.packet.http.HttpResponse#getResponseMessage()
		 */
		@Override
		public String getResponseMessage() {
			return m_responseString;
		}

		public int getNewUin() {
			if (isOKResponse()) {
				final StringTokenizer tokenizer = new StringTokenizer(m_responseString, ":");
				tokenizer.nextToken();
				final String token2 = tokenizer.nextToken(); // new assigned uin
				return Integer.parseInt(token2);
			}
			return -1;
		}

	}

}
