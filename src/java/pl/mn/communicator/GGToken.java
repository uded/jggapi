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
package pl.mn.communicator;

/**
 * Created on 2005-01-26
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGToken.java,v 1.3 2005-01-31 21:22:32 winnetou25 Exp $
 */
public class GGToken {
	
	/** the width of a picture */
	private int m_imageWidth;
	
	/** the height of a picture */
	private int m_imageHeight;
	
	/** the length of token */
	private int m_tokenLength;
	
	/** the id of token */
	private String m_tokenID;
	
	/** the token URL */
	private String m_tokenURL;

	/**
	 * @return Returns the imageWidth.
	 */
	public int getImageWidth() {
		return m_imageWidth;
	}
	
	public void setImageWidth(int imageWidth) {
		if (imageWidth < 0) throw new IllegalArgumentException("imageWidth cannot be null");
		m_imageWidth = imageWidth;
	}
	
	public int getImageHeight() {
		return m_imageHeight;
	}

	public void setImageHeight(int imageHeight) {
		if (imageHeight < 0) throw new IllegalArgumentException("imageHeight cannot be null");
		m_imageHeight = imageHeight;
	}

	public String getTokenURL() {
		return m_tokenURL;
	}

	public void setTokenURL(String tokenURL) {
		m_tokenURL = tokenURL;
	}
	
	public String getTokenID() {
		return m_tokenID;
	}
	
	public void setTokenID(String tokenID) {
		m_tokenID = tokenID;
	}
	
	public int getTokenLength() {
		return m_tokenLength;
	}
	
	public void setTokenLength(int tokenLength) {
		m_tokenLength = tokenLength;
	}
	
	public String getFullTokenURL() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(getTokenURL());
		buffer.append("?tokenid=");
		buffer.append(getTokenID());
		
		return buffer.toString();
	}
	
}
