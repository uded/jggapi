/*
 * Copyright (c) 2003-2005 <a href="http://jggapi.sourceforge.net/team-list.html">JGGApi Development Team</a> All Rights Reserved.
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
package pl.mn.communicator.packet.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.StringTokenizer;

import pl.mn.communicator.GGException;
import pl.mn.communicator.IRegistrationService;

/**
 * Created on 2004-11-29
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: DefaultRegistrationService.java,v 1.6 2004-12-19 18:50:51 winnetou25 Exp $
 */
public class DefaultRegistrationService implements IRegistrationService {

	private Session m_session;
	
	//friendly
	public DefaultRegistrationService(Session session) {
		if (session == null) throw new NullPointerException("session cannot be null");
		m_session = session;
	}
	
	/**
	 * @see pl.mn.communicator.IRegistrationService#changePassword(java.lang.String, java.lang.String, int, java.lang.String)
	 */
	public void changePassword(String email, String password, int qa, String answer) {
		//TODO implement method
	}

	/**
	 * @see pl.mn.communicator.IRegistrationService.registerAccount(String email, String password, String qa, String answer)
	 */
	public int registerAccount(String email, String password, int qa, String answer) throws GGException {
		if (email == null) throw new NullPointerException("email cannot be null");
		if (password == null) throw new NullPointerException("password cannot be null");
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("pwd=");
			buffer.append(URLEncoder.encode(password, "windows-1250"));
			buffer.append('&');
			buffer.append("email=");
			buffer.append(URLEncoder.encode(email, "windows-1250"));
			buffer.append('&');
			buffer.append("qa=");
			buffer.append(qa);
			buffer.append("~");
			buffer.append(URLEncoder.encode(answer, "windows-1250"));
			buffer.append('&');
			buffer.append("code=");
			buffer.append(getHashCode(email, password));

			URL url = new URL("http://register.gadu-gadu.pl/appsvc/fmregister3.asp");
			HttpURLConnection huc = (HttpURLConnection) url.openConnection();

			huc.setRequestMethod("POST");
			huc.setDoInput(true);
			huc.setDoOutput(true);
			huc.setRequestProperty("Content-Length", String.valueOf(buffer.toString().length()));
			huc.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			huc.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 5.0; Windows 98)");

			huc.connect();
			
			PrintWriter out = new PrintWriter(huc.getOutputStream(), true);
			//		pwd=sekret&email=moj@adres.email.pl&qa=5~Maria&code=1104465363

			out.println(buffer.toString());
			out.close();
			
			System.out.println("ResponseCode: "+huc.getResponseCode());
			System.out.println("ResponseMessage: "+huc.getResponseMessage());
			InputStream is = huc.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "windows-1250"));

			String line = reader.readLine();
			line = reader.readLine();
			line = reader.readLine();

			StringTokenizer tokenizer = new StringTokenizer(line, ":");
			
			if (tokenizer.countTokens()!=2) {
				//zonk
			}
			String token1 = tokenizer.nextToken();
			String token2 = tokenizer.nextToken();
			
			int uin = -1;
			
			try {
				uin = Integer.parseInt(token2);
			} catch(NumberFormatException ex) {
				ex.printStackTrace();
			}		 
			
			huc.disconnect();
			return uin;
		} catch (IOException ex) {
			throw new GGException("Unable to register Gadu-Gadu account", ex);
		}
	}

	private void getGGToken() {
//		URL url = new URL("http://register.gadu-gadu.pl/appsvc/fmregister3.asp");
//		HttpURLConnection huc = (HttpURLConnection) url.openConnection();
//
//		huc.setRequestMethod("GET");
//		huc.setDoInput(true);
//		huc.setDoOutput(true);
//		huc.setRequestProperty("Content-Length", String.valueOf(buffer.toString().length()));
//		huc.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
//		huc.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 5.0; Windows 98)");
//
//		huc.connect();
//		
//		PrintWriter out = new PrintWriter(huc.getOutputStream(), true);
//		//		pwd=sekret&email=moj@adres.email.pl&qa=5~Maria&code=1104465363
//
//		out.println(buffer.toString());
//		out.close();
//
//
//		//		struct gg_http *gg_token(int async)
//		//		{
//		//			struct gg_http *h;
//		//			const char *query;
//		//
//		//			query = "Host: " GG_REGISTER_HOST "\r\n"
//		//				"Content-Type: application/x-www-form-urlencoded\r\n"
//		//				"User-Agent: " GG_HTTP_USERAGENT "\r\n"
//		//				"Content-Length: 0\r\n"
//		//				"Pragma: no-cache\r\n"
//		//				"\r\n";
//		//
//		//			if (!(h = gg_http_connect(GG_APPMSG_HOST, GG_APPMSG_PORT, async, "POST", "/appsvc/regtoken.asp", query))) {
//		//				gg_debug(GG_DEBUG_MISC, "=> token, gg_http_connect() failed mysteriously\n");
//		//				return NULL;
//		//			}
//		//
//		//			h->type = GG_SESSION_TOKEN;
//		//
//		//			h->callback = gg_token_watch_fd;
//		//			h->destroy = gg_token_free;
//		//			
//		//			if (!async)
//		//				gg_token_watch_fd(h);
//		//			
//		//			return h;
	}
	
	/**
	 * @see pl.mn.communicator.IRegistrationService#unregisterGGUser(int, char[], java.lang.String)
	 */
	public void unregisterAccount(int uin, String password) {
		// TODO Auto-generated method stub
	}

	/**
	 * @see pl.mn.communicator.IRegistrationService#sendPassword(int)
	 */
	public void remindAndSendPassword(int uin) {
		// TODO Auto-generated method stub
		
	}
	
	private int getHashCode(String email, String password) {
		if (password == null) throw new NullPointerException("password cannot be null");
		if (email == null) throw new NullPointerException("email cannot be null");
    	int a,b,c;
    	
    	b=-1;
    	
    	for(int i=0;i<email.length();i++) {
		   c = (int) email.charAt(i);	
    	   a = (c ^ b) + (c << 8);
    	   b = (a >>> 24 ) | (a << 8);
    	}
    	    	
		for(int i=0;i<password.length(); i++){
			   c = (int) password.charAt(i);
			   a = (c ^ b) + (c << 8);
			   b = (a >>> 24 ) | (a << 8);
		}
    	
    	return ( b < 0 ? -b : b);
	}
	
	private class GGToken {

		/** the width of a picture */
		private int m_width;
		/** the height of a picture */
		private int m_height;
		/** the length of token */
		private int m_length;
		/** the id of token */
		private String m_tokenid;
		
	}
	

}
