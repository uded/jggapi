package pl.radical.open.gg.packet.http;

import pl.radical.open.gg.IGGConfiguration;
import pl.radical.open.gg.dicts.Encoding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created on 2005-01-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class SendAndRemindPasswordRequest extends AbstractTokenRequest {

	private int uin = 0;
	private String email = null;

	public SendAndRemindPasswordRequest(final IGGConfiguration configuration, final int uin, final String email, final String tokenID, final String tokenVal) throws IOException {
		super(configuration, tokenID, tokenVal);
		if (uin < 1) {
			throw new IllegalArgumentException("uin cannot be less than 1");
		}
		if (email == null) {
			throw new IllegalArgumentException("email cannot be null");
		}
		this.uin = uin;
		this.email = email;
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

		return new SendAndRemindPasswordResponse(line);
	}

	/**
	 * @see pl.radical.open.gg.packet.http.HttpRequest#getURL()
	 */
	@Override
	protected String getURL() {
		return ggConfiguration.getSendPasswordURL();
		// return "http://retr.gadu-gadu.pl/appsvc/fmsendpwd3.asp";
	}

	/**
	 * @see pl.radical.open.gg.packet.http.HttpRequest#getRequestBody()
	 */
	@Override
	protected String getRequestBody() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append("userid=");
		buffer.append(uin);
		buffer.append("&email=");
		buffer.append(email);
		buffer.append("&tokenid=");
		buffer.append(tokenID);
		buffer.append("&tokenval=");
		buffer.append(tokenVal);
		buffer.append("&code=");
		buffer.append(getHashCode(String.valueOf(uin)));

		return buffer.toString();
	}

	/**
	 * @see pl.radical.open.gg.packet.http.HttpRequest#wannaWrite()
	 */
	@Override
	protected boolean wannaWrite() {
		return true;
	}

	private int getHashCode(final String uin) {
		if (uin == null) {
			throw new IllegalArgumentException("uin cannot be null");
		}

		int a, b, c;

		b = -1;

		for (int i = 0; i < uin.length(); i++) {
			c = uin.charAt(i);
			a = (c ^ b) + (c << 8);
			b = a >>> 24 | a << 8;
		}

		return b < 0 ? -b : b;
	}

	public static class SendAndRemindPasswordResponse extends HttpResponse {

		private final String responseString;

		public SendAndRemindPasswordResponse(final String responseString) {
			this.responseString = responseString;
		}

		/**
		 * @see pl.radical.open.gg.packet.http.HttpResponse#isErrorResponse()
		 */
		@Override
		public boolean isOKResponse() {
			return responseString.equals("pwdsend_success");
		}

		/**
		 * @see pl.radical.open.gg.packet.http.HttpResponse#getResponseMessage()
		 */
		@Override
		public String getResponseMessage() {
			return responseString;
		}

	}

}
