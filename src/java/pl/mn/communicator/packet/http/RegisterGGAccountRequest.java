/*
 * Copyright (c) 2003-2005 JGGApi Development Team. All Rights Reserved.
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package pl.mn.communicator.packet.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.StringTokenizer;

import pl.mn.communicator.IGGConfiguration;

/**
 * Created on 2005-01-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: RegisterGGAccountRequest.java,v 1.3 2005-05-08 14:28:01 winnetou25 Exp $
 */
public class RegisterGGAccountRequest extends AbstractTokenRequest {

	private String m_email = null;
	private String m_password = null;
	
	public RegisterGGAccountRequest(IGGConfiguration configuration, String email, String password, String tokenID, String tokenVal) throws IOException {
		super(configuration, tokenID, tokenVal);
		if (email == null) throw new NullPointerException("email cannot be null");
		if (password == null) throw new NullPointerException("password cannot be null");
		m_email = email;
		m_password = password;
	}
	
	/**
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 * @see pl.mn.communicator.packet.http.HttpRequest#getResponse()
	 */
	public HttpResponse getResponse() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(m_huc.getInputStream(), WINDOW_ENCODING));
		String line = reader.readLine();

		return new RegisterGGAccountResponse(line);
	}
	
	/**
	 * @see pl.mn.communicator.packet.http.HttpRequest#getURL()
	 */
	protected String getURL() {
	    return m_ggconfiguration.getRegistrationURL();
	    //return "http://register.gadu-gadu.pl/appsvc/fmregister3.asp";
	}

	/**
	 * @throws UnsupportedEncodingException
	 * @see pl.mn.communicator.packet.http.HttpRequest#getRequestBody()
	 */
	protected String getRequestBody() throws UnsupportedEncodingException {
		StringBuffer buffer = new StringBuffer();
		buffer.append("pwd=");
		buffer.append(URLEncoder.encode(m_password, WINDOW_ENCODING));
		buffer.append('&');
		buffer.append("email=");
		buffer.append(URLEncoder.encode(m_email, WINDOW_ENCODING));
		buffer.append('&');
		buffer.append("tokenid=");
		buffer.append(URLEncoder.encode(getTokenID(), WINDOW_ENCODING));
		buffer.append('&');
		buffer.append("tokenval=");
		buffer.append(URLEncoder.encode(getTokenVal(), WINDOW_ENCODING));
		buffer.append('&');
		buffer.append("code=");
		buffer.append(getHashCode(m_email, m_password));
		
		return buffer.toString(); 
	}
	
	/**
	 * @see pl.mn.communicator.packet.http.HttpRequest#wannaWrite()
	 */
	protected boolean wannaWrite() {
		return true;
	}
	
	private int getHashCode(String uin) {
		if (uin == null) throw new NullPointerException("uin cannot be null");

		int a,b,c;
		
		b=-1;

		for(int i=0; i<uin.length(); i++) {
			c = (int) uin.charAt(i);	
			a = (c ^ b) + (c << 8);
			b = (a >>> 24 ) | (a << 8);
		}

		return ( b < 0 ? -b : b);
	}
	
	public static class RegisterGGAccountResponse extends HttpResponse {
		
		private String m_responseString = null;
		
		public RegisterGGAccountResponse(String responseString) {
			m_responseString = responseString;
		}

		/**
		 * @see pl.mn.communicator.packet.http.HttpResponse#isErrorResponse()
		 */
		public boolean isOKResponse() {
			return m_responseString.startsWith("reg_success");
		}
		
		/**
		 * @see pl.mn.communicator.packet.http.HttpResponse#getResponseMessage()
		 */
		public String getResponseMessage() {
			return m_responseString;
		}
		
		public int getNewUin() {
			if (isOKResponse()) {
				StringTokenizer tokenizer = new StringTokenizer(m_responseString, ":");
				String token1 = tokenizer.nextToken(); //reg_success string
				String token2 = tokenizer.nextToken(); //new assigned uin
				return Integer.parseInt(token2);
			} 
			return -1;
		}
		
	}
	
}
