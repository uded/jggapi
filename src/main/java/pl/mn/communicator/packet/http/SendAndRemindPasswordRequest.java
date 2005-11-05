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

import pl.mn.communicator.IGGConfiguration;

/**
 * Created on 2005-01-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: SendAndRemindPasswordRequest.java,v 1.1 2005-11-05 23:34:53 winnetou25 Exp $
 */
public class SendAndRemindPasswordRequest extends AbstractTokenRequest {

	private int m_uin = -1;
	private String m_email = null;

	public SendAndRemindPasswordRequest(IGGConfiguration configuration, int uin, String email, String tokenID, String tokenVal) throws IOException {
		super(configuration, tokenID, tokenVal);
		if (uin < 0) throw new IllegalArgumentException("uin cannot be less than 0");
		if (email == null) throw new NullPointerException("email cannot be null");
		m_uin = uin;
		m_email = email;
	}
	
	/**
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 * @see pl.mn.communicator.packet.http.HttpRequest#getResponse()
	 */
	public HttpResponse getResponse() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(m_huc.getInputStream(), WINDOW_ENCODING));
		String line = reader.readLine();

		return new SendAndRemindPasswordResponse(line);
	}
	
	/**
	 * @see pl.mn.communicator.packet.http.HttpRequest#getURL()
	 */
	protected String getURL() {
	    return m_ggconfiguration.getSendPasswordURL();
		//return "http://retr.gadu-gadu.pl/appsvc/fmsendpwd3.asp";
	}

	/**
	 * @see pl.mn.communicator.packet.http.HttpRequest#getRequestBody()
	 */
	protected String getRequestBody() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("userid=");
		buffer.append(m_uin);
		buffer.append('&');
		buffer.append("email=");
		buffer.append(m_email);
		buffer.append('&');
		buffer.append("tokenid=");
		buffer.append(getTokenID());
		buffer.append('&');
		buffer.append("tokenval=");
		buffer.append(getTokenVal());
		buffer.append('&');
		buffer.append("code=");
		buffer.append(getHashCode(String.valueOf(m_uin)));
		
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
	
	public static class SendAndRemindPasswordResponse extends HttpResponse {
		
		private String m_responseString;
		
		public SendAndRemindPasswordResponse(String responseString) {
			m_responseString = responseString;
		}

		/**
		 * @see pl.mn.communicator.packet.http.HttpResponse#isErrorResponse()
		 */
		public boolean isOKResponse() {
			return m_responseString.equals("pwdsend_success");
		}
		
		/**
		 * @see pl.mn.communicator.packet.http.HttpResponse#getResponseMessage()
		 */
		public String getResponseMessage() {
			return m_responseString;
		}
		
	}
	
}
