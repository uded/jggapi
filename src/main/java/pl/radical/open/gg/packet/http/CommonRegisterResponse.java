package pl.radical.open.gg.packet.http;

import pl.radical.open.gg.GGNullPointerException;

import java.util.StringTokenizer;

/**
 * Created on 2005-01-28
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class CommonRegisterResponse extends HttpResponse {

	private int m_uin = -1;
	private String m_responseString = null;

	public CommonRegisterResponse(final int uin, final String responseString) {
		if (uin < -1) {
			// FIXME IllegalArgumentException
			throw new IllegalArgumentException("uin cannot be less than 0");
		}
		if (responseString == null) {
			throw new GGNullPointerException("responseString cannot be null");
		}
		m_uin = uin;
		m_responseString = responseString;
	}

	/**
	 * @see pl.radical.open.gg.packet.http.HttpResponse#isErrorResponse()
	 */
	@Override
	public boolean isOKResponse() {
		final boolean c1 = m_responseString.startsWith("reg_success");
		boolean c2 = false;

		if (c1) {
			final StringTokenizer tokenizer = new StringTokenizer(m_responseString, ":");
			tokenizer.nextToken();
			final String token2 = tokenizer.nextToken(); // uin
			if (Integer.parseInt(token2) == m_uin) {
				c2 = true;
			}
		}
		return c1 && c2;
	}

	/**
	 * @see pl.radical.open.gg.packet.http.HttpResponse#getResponseMessage()
	 */
	@Override
	public String getResponseMessage() {
		return m_responseString;
	}

}
