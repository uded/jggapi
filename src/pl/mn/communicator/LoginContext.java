/*
 * Copyright (c) 2003 Marcin Naglik (mnaglik@gazeta.pl)
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: LoginContext.java,v 1.3 2004-12-11 19:40:36 winnetou25 Exp $
 */
public final class LoginContext {
	
	private static Log logger = LogFactory.getLog(LoginContext.class);

	private int m_uin = -1;

	private String m_password = null;

	private IStatus m_status = new Status(StatusConst.ONLINE);
	
    private int m_imageSize = 64;

    public LoginContext(int uin, String password) {
    	if (uin < 0) throw new IllegalArgumentException("uin cannot be less than 0");
    	if (password == null) throw new NullPointerException("password cannot be null");
    	m_uin = uin;
    	m_password = password;
    }
    
    /**
     * @return String password
     */
    public String getPassword() {
        return m_password;
    }

    /**
     * @return int uin
     */
    public int getUin() {
        return m_uin;
    }

    public void setPassword(String password) {
    	if (password == null) throw new NullPointerException("password cannot be null");
        m_password = password;
    }

    public void setUin(int uin) {
    	if (uin < 0) throw new IllegalArgumentException("userNo must be a positive number");
        m_uin = uin;
    }
 
    public void setStatus(IStatus status) {
    	if (status == null) throw new NullPointerException("status cannot be null");
    	m_status = status;
    }
    
    public IStatus getStatus() {
    	return m_status;
    }
    
    public void setImageSize(int imageSize) {
    	if (imageSize < 0) throw new IllegalArgumentException("imageSize cannot be less than 0");
    	m_imageSize = imageSize;
    }
    
    public int getImageSize() {
    	return m_imageSize;
    }
    
}
