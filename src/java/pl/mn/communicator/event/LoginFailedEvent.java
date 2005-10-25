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
package pl.mn.communicator.event;

import java.util.EventObject;

/**
 * Created on 2005-10-25
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: LoginFailedEvent.java,v 1.1 2005-10-25 22:17:37 winnetou25 Exp $
 */
public class LoginFailedEvent extends EventObject {

	public final static int NEED_EMAIL_REASON = 0;
	public final static int INCORRECT_PASSWORD = 1;
	
	private int m_reason = INCORRECT_PASSWORD;
	
	public LoginFailedEvent(final Object source) {
		super(source);
	}
	
	public int getReason() {
		return m_reason;
	}
	
	public void setReason(int reason) {
		if (reason == NEED_EMAIL_REASON || reason == INCORRECT_PASSWORD) {
			m_reason = reason;
		} else {
			throw new IllegalArgumentException("Incorrect reason");
		}
		
	}

}
