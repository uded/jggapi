package pl.radical.open.gg.packet.http;

import pl.radical.open.gg.IGGConfiguration;
import pl.radical.open.gg.dicts.Encoding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created on 2005-01-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class UnregisterGGPasswordRequest extends AbstractTokenRequest {

	private int uin = 0;
	private String password = null;

	// FIXME IllegalArgumentException
	public UnregisterGGPasswordRequest(final IGGConfiguration configuration, final int uin, final String password, final String tokenID, final String tokenVal) throws IOException {
		super(configuration, tokenID, tokenVal);
		if (uin < 1) {
			throw new IllegalArgumentException("uin cannot be less than 1");
		}
		if (password == null) {
			// FIXME Other exception instead?
			throw new IllegalArgumentException("password cannot be null");
		}
		this.uin = uin;
		this.password = password;
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

		return new CommonRegisterResponse(uin, line);
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
		buffer.append("fmnumber=");
		buffer.append(uin);
		buffer.append('&');
		buffer.append("fmpwd=");
		buffer.append(URLEncoder.encode(password, Encoding.WINDOWS1250.getValue()));
		buffer.append('&');
		buffer.append("delete=1");
		buffer.append('&');
		buffer.append("email=");
		buffer.append("deletedaccount@gadu-gadu.pl");
		buffer.append('&');
		buffer.append("pwd=");
		buffer.append("2D388046464"); // TODO losowa liczba?
		buffer.append('&');
		buffer.append("tokenid=");
		buffer.append(tokenID);
		buffer.append('&');
		buffer.append("tokenval=");
		buffer.append(tokenVal);
		buffer.append('&');
		buffer.append("code=");
		buffer.append(getHashCode("deletedaccount@gadu-gadu.pl", "2D388046464"));

		return buffer.toString();
	}

	/**
	 * @see pl.radical.open.gg.packet.http.HttpRequest#wannaWrite()
	 */
	@Override
	protected boolean wannaWrite() {
		return true;
	}

}
