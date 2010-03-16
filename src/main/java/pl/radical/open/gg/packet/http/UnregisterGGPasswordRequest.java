package pl.radical.open.gg.packet.http;

import pl.radical.open.gg.GGNullPointerException;
import pl.radical.open.gg.IGGConfiguration;
import pl.radical.open.gg.utils.GGUtils;

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

	private int m_uin = -1;
	private String m_password = null;

	// FIXME IllegalArgumentException
	public UnregisterGGPasswordRequest(final IGGConfiguration configuration, final int uin, final String password, final String tokenID, final String tokenVal) throws IOException {
		super(configuration, tokenID, tokenVal);
		if (uin < 0) {
			throw new IllegalArgumentException("uin cannot be less than 0");
		}
		if (password == null) {
			// FIXME Other exception instead?
			throw new GGNullPointerException("password cannot be null");
		}
		m_uin = uin;
		m_password = password;
	}

	/**
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 * @see pl.radical.open.gg.packet.http.HttpRequest#getResponse()
	 */
	@Override
	public HttpResponse getResponse() throws IOException {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(m_huc.getInputStream(), GGUtils.WINDOWS_ENCODING));
		final String line = reader.readLine();

		return new CommonRegisterResponse(m_uin, line);
	}

	/**
	 * @see pl.radical.open.gg.packet.http.HttpRequest#getURL()
	 */
	@Override
	protected String getURL() {
		return m_ggconfiguration.getRegistrationURL();
		// return "http://register.gadu-gadu.pl/appsvc/fmregister3.asp";
	}

	/**
	 * @throws UnsupportedEncodingException
	 * @see pl.radical.open.gg.packet.http.HttpRequest#getRequestBody()
	 */
	@Override
	protected String getRequestBody() throws UnsupportedEncodingException {
		final StringBuffer buffer = new StringBuffer();
		buffer.append("fmnumber=");
		buffer.append(m_uin);
		buffer.append('&');
		buffer.append("fmpwd=");
		buffer.append(URLEncoder.encode(m_password, GGUtils.WINDOWS_ENCODING));
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
		buffer.append(getTokenID());
		buffer.append('&');
		buffer.append("tokenval=");
		buffer.append(getTokenVal());
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
