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

import java.util.StringTokenizer;

/**
 * Created on 2005-01-28
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: CommonRegisterResponse.java,v 1.1 2005-01-28 22:08:49 winnetou25 Exp $
 */
public class CommonRegisterResponse extends HttpResponse {
	
	private int m_uin = -1;
	private String m_responseString = null;
	
	public CommonRegisterResponse(int uin, String responseString) {
		if (uin < -1) throw new IllegalArgumentException("uin cannot be less than 0");
		if (responseString == null) throw new NullPointerException("responseString cannot be null");
		m_uin = uin;
		m_responseString = responseString;
	}
	
	/**
	 * @see pl.mn.communicator.packet.http.HttpResponse#isErrorResponse()
	 */
	public boolean isOKResponse() {
		boolean c1 = m_responseString.startsWith("reg_success");
		boolean c2 = false;
		
		StringTokenizer tokenizer = new StringTokenizer(m_responseString, ":");
		String token1 = tokenizer.nextToken(); //reg_success string
		String token2 = tokenizer.nextToken(); //uin
		if (Integer.parseInt(token2) == m_uin) {
			c2 = true;
		}
		return c1 && c2;
	}
	
}
