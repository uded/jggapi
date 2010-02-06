/*
 * Copyright (c) 2003-2005 JGGApi Development Team. All Rights Reserved. This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version. This program is distributed in
 * the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a
 * copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package pl.radical.open.gg.packet.http;

import pl.radical.open.gg.IGGConfiguration;
import pl.radical.open.gg.packet.GGUtils;

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
 * @version $Id: RegisterGGAccountRequest.java,v 1.2 2007/05/07 16:22:30 winnetou25 Exp $
 */
public class RegisterGGAccountRequest extends AbstractTokenRequest {

	private String m_email = null;
	private String m_password = null;

	public RegisterGGAccountRequest(final IGGConfiguration configuration, final String email, final String password, final String tokenID, final String tokenVal) throws IOException {
		super(configuration, tokenID, tokenVal);
		if (email == null) {
			throw new NullPointerException("email cannot be null");
		}
		if (password == null) {
			throw new NullPointerException("password cannot be null");
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
		final BufferedReader reader = new BufferedReader(new InputStreamReader(m_huc.getInputStream(), GGUtils.WINDOW_ENCODING));
		final String line = reader.readLine();

		return new RegisterGGAccountResponse(line);
	}

	/**
	 * @see pl.radical.open.gg.packet.http.HttpRequest#getURL()
	 */
	@Override
	protected String getURL() {
		return m_ggconfiguration.getRegistrationURL();
	}

	/**
	 * @throws UnsupportedEncodingException
	 * @see pl.radical.open.gg.packet.http.HttpRequest#getRequestBody()
	 */
	@Override
	protected String getRequestBody() throws UnsupportedEncodingException {
		final StringBuffer buffer = new StringBuffer();
		buffer.append("pwd=");
		buffer.append(URLEncoder.encode(m_password, GGUtils.WINDOW_ENCODING));
		buffer.append('&');
		buffer.append("email=");
		buffer.append(URLEncoder.encode(m_email, GGUtils.WINDOW_ENCODING));
		buffer.append('&');
		buffer.append("tokenid=");
		buffer.append(URLEncoder.encode(getTokenID(), GGUtils.WINDOW_ENCODING));
		buffer.append('&');
		buffer.append("tokenval=");
		buffer.append(URLEncoder.encode(getTokenVal(), GGUtils.WINDOW_ENCODING));
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
