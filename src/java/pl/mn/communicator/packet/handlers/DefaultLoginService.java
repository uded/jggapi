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
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import pl.mn.communicator.GGException;
import pl.mn.communicator.GGSessionException;
import pl.mn.communicator.ILocalStatus;
import pl.mn.communicator.ILoginService;
import pl.mn.communicator.LocalStatus;
import pl.mn.communicator.LoginContext;
import pl.mn.communicator.SessionState;
import pl.mn.communicator.StatusType;
import pl.mn.communicator.event.LoginListener;
import pl.mn.communicator.packet.out.GGLogin60;

/**
 * The default implementation of <code>ILoginService</code>.
 * <p>
 * Created on 2004-11-28
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: DefaultLoginService.java,v 1.11 2004-12-26 22:19:56 winnetou25 Exp $
 */
public class DefaultLoginService implements ILoginService {

	/** The session associated with this service */
	private Session m_session = null;

	/** The set of <code>LoginListener</code>'s */
	private Set m_loginListeners = null;
	
	//friendly
	DefaultLoginService(Session session) {
		if (session == null) throw new NullPointerException("session cannot be null");
		m_session = session;
		m_loginListeners = new HashSet();
	}
	
	/**
	 * @see pl.mn.communicator.ILoginService#login()
	 */
	public void login() throws GGException {
		if (m_session.getSessionState() != SessionState.AUTHENTICATION_AWAITING) {
			throw new GGSessionException(m_session.getSessionState());
		}
		
		try {
			int uin = m_session.getLoginContext().getUin();
			String password = m_session.getLoginContext().getPassword();
			int seed = m_session.getSessionAccessor().getLoginSeed();

			GGLogin60 login = new GGLogin60(uin, password.toCharArray(), seed);

			LoginContext loginContext = m_session.getLoginContext();
			login.setStatus(loginContext.getStatus());

			if (loginContext.getImageSize() != -1) {
				login.setImageSize(loginContext.getImageSize());
			}
			if (loginContext.getLocalIP() != null) {
				login.setLocalIP(loginContext.getLocalIP());
			}
			if (loginContext.getLocalPort() != -1) {
				login.setLocalPort(loginContext.getLocalPort());
			}
			if (loginContext.getExternalIP() != null) {
				login.setExternalIP(loginContext.getExternalIP());
			}
			if (loginContext.getExternalPort() != -1) {
				login.setExternalPort(loginContext.getExternalPort());
			}
			m_session.getSessionAccessor().sendPackage(login);
		} catch (IOException ex) {
			m_session.getSessionAccessor().setSessionState(SessionState.DISCONNECTED);
			throw new GGException("Unable to login, loginContext: "+m_session.getLoginContext(), ex);
		}
	}

	/**
	 * @see pl.mn.communicator.ILoginService#logout()
	 */
	public void logout() throws GGException {
		checkSessionState();
		ILocalStatus localStatus = new LocalStatus(StatusType.OFFLINE);
		m_session.getPresenceService().setStatus(localStatus);
		m_session.getSessionAccessor().setSessionState(SessionState.LOGGED_OUT);
	}
	
	/**
	 * @see pl.mn.communicator.ILoginService#logout(java.lang.String, java.util.Date returnTime)
	 */
	public void logout(String description, Date returnTime) throws GGException {
		if (description == null) throw new NullPointerException("description cannot be null");
		checkSessionState();
		if (description.length() > 0) {
			LocalStatus localStatus = new LocalStatus(StatusType.OFFLINE_WITH_DESCRIPTION, description);
			if (returnTime != null) {
				localStatus.setReturnDate(returnTime);
			}
			m_session.getPresenceService().setStatus(localStatus);
			m_session.getSessionAccessor().setSessionState(SessionState.LOGGED_OUT);
		}
	}
	
	/**
	 * @see pl.mn.communicator.ILoginService#addLoginListener(pl.mn.communicator.event.LoginListener)
	 */
	public void addLoginListener(LoginListener loginListener) {
		if (loginListener == null) throw new NullPointerException("loginListener cannot be null");
		m_loginListeners.add(loginListener);
	}
	
	/**
	 * @see pl.mn.communicator.ILoginService#removeLoginListener(pl.mn.communicator.event.LoginListener)
	 */
	public void removeLoginListener(LoginListener loginListener) {
		if (loginListener == null) throw new NullPointerException("loginListener cannot  be null");
		m_loginListeners.remove(loginListener);
	}
	
	protected void notifyLoginOK() {
		m_session.getSessionAccessor().setSessionState(SessionState.LOGGED_IN);
		for (Iterator it = m_loginListeners.iterator(); it.hasNext();) {
			LoginListener loginListener = (LoginListener) it.next();
			loginListener.loginOK();
		}
	}
	
	private void checkSessionState() {
		if (m_session.getSessionState() != SessionState.LOGGED_IN) {
			throw new GGSessionException(m_session.getSessionState());
		}
	}
	
	//TODO clone list
	protected void notifyLoginFailed() {
		m_session.getSessionAccessor().setSessionState(SessionState.CONNECTION_ERROR);
		for (Iterator it = m_loginListeners.iterator(); it.hasNext();) {
			LoginListener loginListener = (LoginListener) it.next();
			loginListener.loginFailed();
		}
	}

}
