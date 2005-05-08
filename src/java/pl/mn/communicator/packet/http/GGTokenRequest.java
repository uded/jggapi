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
import java.util.StringTokenizer;

import pl.mn.communicator.GGToken;
import pl.mn.communicator.IGGConfiguration;

/**
 * Created on 2005-01-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGTokenRequest.java,v 1.3 2005-05-08 14:28:01 winnetou25 Exp $
 */
public class GGTokenRequest extends HttpRequest {

	private String m_email = null;
	private String m_password = null;
	
	public GGTokenRequest(IGGConfiguration configuration) throws IOException {
		super(configuration);
		m_huc.setRequestProperty("Accept", "image/gif, image/jpeg, */*");
		m_huc.setRequestProperty("Accept-Language", "pl");
	}
	
	/**
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 * @see pl.mn.communicator.packet.http.HttpRequest#getResponse()
	 */
	public HttpResponse getResponse() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(m_huc.getInputStream(), WINDOW_ENCODING));
		String line1 = reader.readLine();
		String line2 = reader.readLine();
		String line3 = reader.readLine();
		
		return new GGTokenResponse(line1, line2, line3);
	}
	
	/**
	 * @see pl.mn.communicator.packet.http.HttpRequest#getURL()
	 */
	protected String getURL() {
	    return m_ggconfiguration.getTokenRequestURL();
	    //return "http://register.gadu-gadu.pl/appsvc/regtoken.asp";
	}
	
	/**
	 * @throws UnsupportedEncodingException
	 * @see pl.mn.communicator.packet.http.HttpRequest#getRequestBody()
	 */
	protected String getRequestBody() throws UnsupportedEncodingException {
		return "";
	}
	
	/**
	 * @see pl.mn.communicator.packet.http.HttpRequest#wannaWrite()
	 */
	protected boolean wannaWrite() {
		return false;
	}
	
	public static class GGTokenResponse extends HttpResponse {
		
		private String m_responseLine1 = null;
		private String m_responseLine2 = null;
		private String m_responseLine3 = null;
		
		private GGTokenResponse(String responseLine1, String responseLine2, String responseLine3) {
			m_responseLine1 = responseLine1;
			m_responseLine2 = responseLine2;
			m_responseLine3 = responseLine3;
		}
		
		/**
		 * @see pl.mn.communicator.packet.http.HttpResponse#isErrorResponse()
		 */
		public boolean isOKResponse() {
			return (m_responseLine1 != null && m_responseLine2 != null && m_responseLine3 != null);
		}
		
		public GGToken getGGToken() {
			GGToken token = new GGToken();
			
			StringTokenizer tokenizer = new StringTokenizer(m_responseLine1, " ");
			
			String widthString = tokenizer.nextToken();
			String heightString = tokenizer.nextToken();
			String lengthString = tokenizer.nextToken();
			
			token.setImageWidth(Integer.parseInt(widthString));
			token.setImageHeight(Integer.parseInt(heightString));
			token.setTokenLength(Integer.parseInt(lengthString));
			token.setTokenID(m_responseLine2.trim());
			token.setTokenURL(m_responseLine3.trim());
			
			return token;
		}
		
		/**
		 * @see pl.mn.communicator.packet.http.HttpResponse#getResponseMessage()
		 */
		public String getResponseMessage() {
			return m_responseLine1+"\n"+m_responseLine2+"\n"+m_responseLine3;
		}
		
	}
	
}
