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
package pl.mn.communicator.packet.handlers;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import pl.mn.communicator.GGException;
import pl.mn.communicator.GGSessionException;
import pl.mn.communicator.ILoginService;
import pl.mn.communicator.SessionState;
import pl.mn.communicator.Status;
import pl.mn.communicator.StatusType;
import pl.mn.communicator.event.LoginListener;
import pl.mn.communicator.packet.out.GGLogin60;

/**
 * Created on 2004-11-28
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: DefaultLoginService.java,v 1.3 2004-12-14 20:10:49 winnetou25 Exp $
 */
public class DefaultLoginService implements ILoginService {

	private Session m_session = null;
	private Set m_loginListeners = null;
	
	public DefaultLoginService(Session session) {
		if (session == null) throw new NullPointerException("session cannot be null");
		m_session = session;
		m_loginListeners = new HashSet();
	}
	
	/**
	 * @see pl.mn.communicator.ILoginService#login(pl.mn.communicator.LoginContext)
	 */
	public void login() throws GGException {
		if (m_session.getSessionState() != SessionState.AUTHENTICATION_AWAITING) {
			throw new GGSessionException("Unable to login, wrong session state: "+m_session.getSessionState());
		}
		
		try {
			int uin = m_session.getLoginContext().getUin();
			String password = m_session.getLoginContext().getPassword();
			int seed = m_session.getSessionAccessor().getLoginSeed();
			
			GGLogin60 login = new GGLogin60(uin, password.toCharArray(), seed);
			login.setImageSize(m_session.getLoginContext().getImageSize());
			login.setStatus(m_session.getLoginContext().getStatus());
			login.setLocalIP(m_session.getLoginContext().getLocalIP());
			login.setLocalPort(m_session.getLoginContext().getLocalPort());
			login.setExternalIP(m_session.getLoginContext().getExternalIP());
			login.setExternalPort(m_session.getLoginContext().getExternalPort());
			
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
		m_session.getPresenceService().setStatus(new Status(StatusType.OFFLINE));
		m_session.getSessionAccessor().setSessionState(SessionState.LOGGED_OUT);
	}
	
	/**
	 * @see pl.mn.communicator.ILoginService#logout(java.lang.String, java.util.Date returnTime)
	 */
	public void logout(String description, Date returnTime) throws GGException {
		if (description == null) throw new NullPointerException("description cannot be null");
		checkSessionState();
		if (description.length() > 0) {
			Status localStatus = new Status(StatusType.OFFLINE_WITH_DESCRIPTION);
			localStatus.setDescription(description);
			if (returnTime != null) {
				localStatus.setReturnDate(returnTime);
			}
			m_session.getPresenceService().setStatus(localStatus);
			m_session.getSessionAccessor().setSessionState(SessionState.LOGGED_OUT);
		}
	}
	
	/**
	 * @see pl.mn.communicator.ILoginService#addLoginListener(pl.mn.communicator.LoginListener)
	 */
	public void addLoginListener(LoginListener loginListener) {
		if (loginListener == null) throw new NullPointerException("loginListener cannot be null");
		m_loginListeners.add(loginListener);
	}
	
	/**
	 * @see pl.mn.communicator.ILoginService#removeLoginListener(pl.mn.communicator.LoginListener)
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
			throw new GGSessionException("Incorrect session state: "+m_session.getSessionState());
		}
	}
	
	protected void notifyLoginFailed() {
		m_session.getSessionAccessor().setSessionState(SessionState.CONNECTION_ERROR);
		for (Iterator it = m_loginListeners.iterator(); it.hasNext();) {
			LoginListener loginListener = (LoginListener) it.next();
			loginListener.loginFailed();
		}
	}

}
