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

/**
 * Created on 2005-01-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: UnregisterGGPasswordRequest.java,v 1.2 2005-01-28 23:07:32 winnetou25 Exp $
 */
public class UnregisterGGPasswordRequest extends AbstractTokenRequest {
	
	private int m_uin = -1;
	private String m_password = null;
	
	public UnregisterGGPasswordRequest(int uin, String password, String tokenID, String tokenVal) throws IOException {
		super(tokenID, tokenVal);
		if (uin < 0) throw new IllegalArgumentException("uin cannot be less than 0");
		if (password == null) throw new NullPointerException("password cannot be null");
		m_uin = uin;
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
		
		return new CommonRegisterResponse(m_uin, line);
	}
	
	/**
	 * @see pl.mn.communicator.packet.http.HttpRequest#getURL()
	 */
	protected String getURL() {
		return "http://register.gadu-gadu.pl/appsvc/fmregister3.asp";
	}
	
	/**
	 * @throws UnsupportedEncodingException
	 * @see pl.mn.communicator.packet.http.HttpRequest#getRequestBody()
	 */
	protected String getRequestBody() throws UnsupportedEncodingException {
		StringBuffer buffer = new StringBuffer();
		buffer.append("fmnumber=");
		buffer.append(m_uin);
		buffer.append('&');
		buffer.append("fmpwd=");
		buffer.append(URLEncoder.encode(m_password, WINDOW_ENCODING));
		buffer.append('&');
		buffer.append("delete=1");
		buffer.append('&');
		buffer.append("email=");
		buffer.append("deletedaccount@gadu-gadu.pl");
		buffer.append('&');
		buffer.append("pwd=");
		buffer.append("2D388046464"); //TODO losowa liczba?
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
	
}
