package pl.radical.open.gg.packet.http;

import java.util.StringTokenizer;

/**
 * Created on 2005-01-28
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @author <a href="mailto:lukasz.rzanek@radical.com.pl>Łukasz Rżanek</a>
 */
public class CommonRegisterResponse extends HttpResponse {

	private int uin = 0;

	private String responseString = null;

	public CommonRegisterResponse(final int uin, final String responseString) {
		if (uin < 1) {
			throw new IllegalArgumentException("uin cannot be less than 1");
		}
		if (responseString == null) {
			throw new IllegalArgumentException("responseString cannot be null");
		}
		this.uin = uin;
		this.responseString = responseString;
	}

	/**
	 * @see pl.radical.open.gg.packet.http.HttpResponse#isErrorResponse()
	 */
	@Override
	public boolean isOKResponse() {
		final boolean case1 = responseString.startsWith("reg_success");
		boolean case2 = false;

		if (case1) {
			final StringTokenizer tokenizer = new StringTokenizer(responseString, ":");
			tokenizer.nextToken();
			final String token2 = tokenizer.nextToken(); // uin
			if (Integer.parseInt(token2) == uin) {
				case2 = true;
			}
		}
		return case1 && case2;
	}

	/**
	 * @see pl.radical.open.gg.packet.http.HttpResponse#getResponseMessage()
	 */
	@Override
	public String getResponseMessage() {
		return responseString;
	}

}
