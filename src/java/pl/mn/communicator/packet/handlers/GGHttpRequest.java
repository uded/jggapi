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

import java.io.IOException;

/**
 * Created on 2005-01-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGHttpRequest.java,v 1.1 2005-01-27 23:56:43 winnetou25 Exp $
 */
public abstract class GGHttpRequest extends HttpRequest {

	public final static String WINDOW_ENCODING = "windows-1250";

	private String m_tokenID;
	private String m_tokenVal;

	protected GGHttpRequest() throws IOException {
		super();
	}

	public void setTokenID(String tokenID) {
		m_tokenID = tokenID;
	}
	
	public void setTokenValue(String tokenVal) {
		m_tokenVal = tokenVal;
	}
	
	public String getTokenID() {
		return m_tokenID;
	}
	
	public String getTokenVal() {
		return m_tokenVal;
	}
	
	protected int getHashCode(String email, String password) {
		if (password == null) throw new NullPointerException("password cannot be null");
		if (email == null) throw new NullPointerException("email cannot be null");

		int a,b,c;
		
		b=-1;
		
		for(int i=0; i<email.length(); i++) {
			c = (int) email.charAt(i);	
			a = (c ^ b) + (c << 8);
			b = (a >>> 24 ) | (a << 8);
		}
		
		for(int i=0; i<password.length(); i++){
			c = (int) password.charAt(i);
			a = (c ^ b) + (c << 8);
			b = (a >>> 24 ) | (a << 8);
		}
		
		return ( b < 0 ? -b : b);
	}
	
}
