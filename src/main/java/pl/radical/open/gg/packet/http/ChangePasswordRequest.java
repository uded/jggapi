package pl.radical.open.gg.packet.http;

import pl.radical.open.gg.GGNullPointerException;
import pl.radical.open.gg.IGGConfiguration;
import pl.radical.open.gg.packet.GGUtils;

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
public class ChangePasswordRequest extends AbstractTokenRequest {

	private int m_uin = -1;
	private String m_email = null;
	private String m_oldPassword = null;
	private String m_newPassword = null;

	// FIXME IllegalArgumentException
	public ChangePasswordRequest(final IGGConfiguration configuration, final int uin, final String email, final String oldPassword, final String newPassword, final String tokenID, final String tokenVal) throws IOException {
		super(configuration, tokenID, tokenVal);
		if (uin < 0) {
			throw new IllegalArgumentException("uin cannot be less than 0");
		}
		if (email == null) {
			// FIXME Other exception instead?
			throw new GGNullPointerException("email cannot be null");
		}
		if (oldPassword == null) {
			// FIXME Other exception instead?
			throw new GGNullPointerException("oldPassword cannot be null");
		}
		if (newPassword == null) {
			// FIXME Other exception instead?
			throw new GGNullPointerException("newPassword cannot be null");
		}
		m_uin = uin;
		m_email = email;
		m_oldPassword = oldPassword;
		m_newPassword = newPassword;
	}

	/**
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 * @see pl.radical.open.gg.packet.http.HttpRequest#getResponse()
	 */
	@Override
	public HttpResponse getResponse() throws IOException {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(m_huc.getInputStream(), GGUtils.WINDOW_ENCODING));
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
		buffer.append(URLEncoder.encode(m_oldPassword, GGUtils.WINDOW_ENCODING));
		buffer.append('&');
		buffer.append("pwd=");
		buffer.append(URLEncoder.encode(m_newPassword, GGUtils.WINDOW_ENCODING));
		buffer.append('&');
		buffer.append("email=");
		buffer.append(URLEncoder.encode(m_email, GGUtils.WINDOW_ENCODING));
		buffer.append('&');
		buffer.append("tokenid=");
		buffer.append(getTokenID());
		buffer.append('&');
		buffer.append("tokenval=");
		buffer.append(getTokenVal());
		buffer.append('&');
		buffer.append("code=");
		buffer.append(getHashCode(m_email, m_newPassword));

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
