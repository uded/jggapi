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
package pl.mn.communicator.packet.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pl.mn.communicator.GGException;
import pl.mn.communicator.GGToken;
import pl.mn.communicator.IRegistrationService;

/**
 * Created on 2004-11-29
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: DefaultRegistrationService.java,v 1.11 2005-01-25 23:52:31 winnetou25 Exp $
 */
public class DefaultRegistrationService implements IRegistrationService {

	private final static String WINDOW_ENCODING = "windows-1250";
	
	private final static Log LOG = LogFactory.getLog(DefaultRegistrationService.class);
	
	/** reference to session object */
	private Session m_session = null;
	
	//friendly
	public DefaultRegistrationService(Session session) {
		if (session == null) throw new NullPointerException("session cannot be null");
		m_session = session;
	}
	
	/**
	 * @see pl.mn.communicator.IRegistrationService#getRegToken()
	 */
	public GGToken getRegToken() throws GGException {
		
		try {
			URL url = new URL("http://register.gadu-gadu.pl/appsvc/regtoken.asp");
			HttpURLConnection huc = (HttpURLConnection) url.openConnection();

			huc.setRequestMethod("POST");
			huc.setDoInput(true);
			huc.setRequestProperty("Content-Length", "0");
			huc.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			huc.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 5.0; Windows 98)");
			huc.setRequestProperty("Accept", "image/gif, image/jpeg, */*");
			huc.setRequestProperty("Accept-Language", "pl");
			huc.setRequestProperty("Pragma", "no-cache");

			huc.connect();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(huc.getInputStream(), WINDOW_ENCODING));

			String line1 = reader.readLine();
			String line2 = reader.readLine();
			String line3 = reader.readLine();

			GGToken token = new GGToken();

			StringTokenizer tokenizer = new StringTokenizer(line1, " ");

			String widthString = tokenizer.nextToken();
			String heightString = tokenizer.nextToken();
			String lengthString = tokenizer.nextToken();
			
			token.setImageWidth(Integer.parseInt(widthString));
			token.setImageHeight(Integer.parseInt(heightString));
			token.setTokenLength(Integer.parseInt(lengthString));
			token.setTokenID(line2.trim());
			token.setTokenURL(line3.trim());
			
			return token;
		} catch (IOException ex) {
			throw new GGException("Unable to get token", ex);
		}
	}
	
	/**
	 * @see pl.mn.communicator.IRegistrationService#changePassword(java.lang.String, java.lang.String, int, java.lang.String)
	 */
	public void changePassword(String email, String password, int qa, String answer) {
		//TODO implement method
	}

	/**
	 * @see pl.mn.communicator.IRegistrationService#registerAccount(java.lang.String email, java.lang.String password, int qa, String answer)
	 */
	public int registerAccount(String email, String password, String tokenID, String code, int question, String answer) throws GGException {
		if (email == null) throw new NullPointerException("email cannot be null");
		if (password == null) throw new NullPointerException("password cannot be null");

		try {
			String registrationURL = prepareRegistrationURL(email, password, tokenID, code, question, answer);

			URL url = new URL("http://register.gadu-gadu.pl/appsvc/fmregister3.asp");
			HttpURLConnection huc = (HttpURLConnection) url.openConnection();

			huc.setRequestMethod("POST");
			huc.setDoInput(true);
			huc.setDoOutput(true);
			huc.setRequestProperty("Content-Length", String.valueOf(registrationURL.length()));
			huc.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			huc.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 5.0; Windows 98)");

			huc.connect();
			
			PrintWriter out = new PrintWriter(huc.getOutputStream(), true);

			out.println(registrationURL);
			out.close();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(huc.getInputStream(), WINDOW_ENCODING));

			String line = reader.readLine();

			reader.close();
			huc.disconnect();
			
			if (!line.startsWith("reg_success")) {
				throw new GGException("Registration error");
			} else {
				StringTokenizer tokenizer = new StringTokenizer(line, ":");
				String token1 = tokenizer.nextToken(); //reg_success
				String token2 = tokenizer.nextToken(); //new uin
				int uin = Integer.parseInt(token2);
				return uin;
			}
			
		} catch (IOException ex) {
			throw new GGException("Unable to register Gadu-Gadu account", ex);
		}
	}

	/**
	 * @see pl.mn.communicator.IRegistrationService#unregisterAccount(int, java.lang.String)
	 */
	public void unregisterAccount(int uin, String password) {
		// TODO Auto-generated method stub
	}

	/**
	 * @see pl.mn.communicator.IRegistrationService#remindAndSendPassword(int)
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

	private String prepareRegistrationURL(String email, String password, String tokenID, String code, int qa, String answer) throws UnsupportedEncodingException {
		StringBuffer buffer = new StringBuffer();
		buffer.append("pwd=");
		buffer.append(URLEncoder.encode(password, WINDOW_ENCODING));
		buffer.append('&');
		buffer.append("email=");
		buffer.append(URLEncoder.encode(email, WINDOW_ENCODING));
		buffer.append('&');
		buffer.append("tokenid=");
		buffer.append(URLEncoder.encode(tokenID, WINDOW_ENCODING));
		buffer.append('&');
		buffer.append("tokenval=");
		buffer.append(URLEncoder.encode(code, WINDOW_ENCODING));
		buffer.append('&');
		buffer.append("qa=");
		buffer.append(qa);
		buffer.append("~");
		buffer.append(URLEncoder.encode(answer, WINDOW_ENCODING));
		buffer.append('&');
		buffer.append("code=");
		buffer.append(getHashCode(email, password));
		
		return buffer.toString(); 
	}
	
}
