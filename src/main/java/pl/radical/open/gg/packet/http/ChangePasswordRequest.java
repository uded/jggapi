package pl.radical.open.gg.packet.http;

import pl.radical.open.gg.IGGConfiguration;
import pl.radical.open.gg.dicts.Encoding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on 2005-01-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @author <a href="mailto:lukasz.rzanek@radical.com.pl>Łukasz Rżanek</a>
 */
public class ChangePasswordRequest extends AbstractTokenRequest {
	private static final Logger log = LoggerFactory.getLogger(ChangePasswordRequest.class);

	private int uin = 0;
	private String email = null;
	private String oldPassword = null;
	private String newPassword = null;

	public ChangePasswordRequest(final IGGConfiguration configuration, final int uin, final String email, final String oldPassword, final String newPassword, final String tokenID, final String tokenVal) throws IOException {
		super(configuration, tokenID, tokenVal);

		if (log.isTraceEnabled()) {
			log.trace("Creating {} object", getClass());
		}

		if (uin < 1) {
			throw new IllegalArgumentException("uin cannot be less than 1");
		}
		if (email == null) {
			throw new IllegalArgumentException("email cannot be null");
		}
		if (oldPassword == null) {
			throw new IllegalArgumentException("oldPassword cannot be null");
		}
		if (newPassword == null) {
			throw new IllegalArgumentException("newPassword cannot be null");
		}

		this.uin = uin;
		this.email = email;
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}

	/**
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 * @see pl.radical.open.gg.packet.http.HttpRequest#getResponse()
	 */
	@Override
	public HttpResponse getResponse() throws IOException {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(huc.getInputStream(), Encoding.WINDOWS1250.getValue()));
			final String line = reader.readLine();

			return new CommonRegisterResponse(uin, line);
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
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
		buffer.append(URLEncoder.encode(oldPassword, Encoding.WINDOWS1250.getValue()));
		buffer.append('&');
		buffer.append("pwd=");
		buffer.append(URLEncoder.encode(newPassword, Encoding.WINDOWS1250.getValue()));
		buffer.append('&');
		buffer.append("email=");
		buffer.append(URLEncoder.encode(email, Encoding.WINDOWS1250.getValue()));
		buffer.append('&');
		buffer.append("tokenid=");
		buffer.append(tokenID);
		buffer.append('&');
		buffer.append("tokenval=");
		buffer.append(tokenVal);
		buffer.append('&');
		buffer.append("code=");
		buffer.append(getHashCode(email, newPassword));

		if (log.isDebugEnabled()) {
			log.debug("Request body: {}", buffer.toString());
		}

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
