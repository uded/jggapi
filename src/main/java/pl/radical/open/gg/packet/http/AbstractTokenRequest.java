package pl.radical.open.gg.packet.http;

import pl.radical.open.gg.GGNullPointerException;
import pl.radical.open.gg.IGGConfiguration;

import java.io.IOException;

/**
 * Created on 2005-01-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public abstract class AbstractTokenRequest extends HttpRequest {

	private String m_tokenID = null;
	private String m_tokenVal = null;

	protected AbstractTokenRequest(final IGGConfiguration configuration, final String tokenID, final String tokenVal) throws IOException {
		super(configuration);
		if (tokenID == null) {
			// FIXME Other exception instead?
			throw new GGNullPointerException("tokenID cannot be null");
		}
		if (tokenVal == null) {
			// FIXME Other exception instead?
			throw new GGNullPointerException("tokenVal cannot be null");
		}
		m_tokenID = tokenID;
		m_tokenVal = tokenVal;
	}

	public String getTokenID() {
		return m_tokenID;
	}

	public String getTokenVal() {
		return m_tokenVal;
	}

	protected int getHashCode(final String email, final String password) {
		if (password == null) {
			throw new GGNullPointerException("password cannot be null");
		}
		if (email == null) {
			throw new GGNullPointerException("email cannot be null");
		}

		int a, b, c;

		b = -1;

		for (int i = 0; i < email.length(); i++) {
			c = email.charAt(i);
			a = (c ^ b) + (c << 8);
			b = a >>> 24 | a << 8;
		}

		for (int i = 0; i < password.length(); i++) {
			c = password.charAt(i);
			a = (c ^ b) + (c << 8);
			b = a >>> 24 | a << 8;
		}

		return b < 0 ? -b : b;
	}

}
