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
package pl.mn.communicator.gadu.handlers;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import pl.mn.communicator.GGException;
import pl.mn.communicator.GGSessionException;
import pl.mn.communicator.ILoginService;
import pl.mn.communicator.SessionState;
import pl.mn.communicator.Status60;
import pl.mn.communicator.StatusType;
import pl.mn.communicator.event.LoginListener;
import pl.mn.communicator.gadu.out.GGLogin60;

/**
 * Created on 2004-11-28
 * 
 * @author mateusz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
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
		if (m_session.getSessionState() == SessionState.AUTHENTICATION_AWAITING) {
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
		} else {
			throw new GGSessionException("Unable to login, wrong session state: "+m_session.getSessionState());
		}
	}

	/**
	 * @see pl.mn.communicator.ILoginService#logout()
	 */
	public void logout() throws GGException {
		if (m_session.getSessionState() == SessionState.LOGGED_IN) {
			m_session.getPresenceService().setStatus(new Status60(StatusType.OFFLINE));
			m_session.getSessionAccessor().setSessionState(SessionState.LOGGED_OUT);
		} else {
			throw new GGSessionException("Unable to logout, wrong session state: "+m_session.getSessionState());
		}
	}
	
	/**
	 * @see pl.mn.communicator.ILoginService#logout(java.lang.String)
	 */
	public void logout(String description) throws GGException {
		if (description == null) throw new NullPointerException("description cannot be null");
		if (m_session.getSessionState() == SessionState.LOGGED_IN) {
			if (description.length() > 0) {
				Status60 localStatus = new Status60(StatusType.OFFLINE_WITH_DESCRIPTION);
				localStatus.setDescription(description);
				m_session.getPresenceService().setStatus(localStatus);
				m_session.getSessionAccessor().setSessionState(SessionState.LOGGED_OUT);
			}
		} else {
			throw new GGSessionException("Unable to logout, wrong session state: "+m_session.getSessionState());
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
	
	//TODO sprawdzic czy przy zlym zalogowaniu mozna sie jeszcze raz zalogowac czy juz jest po sesji
	//i trzeba sie rozlaczyc
	//jezeli tak to service ConnectionListener moglby miec LoginHandler na loginFailed event
	//i jezeli takowy sie zdarzy wtedy od razu wywolac disconnect metode.
	protected void notifyLoginFailed() {
		m_session.getSessionAccessor().setSessionState(SessionState.AUTHENTICATION_AWAITING);
		for (Iterator it = m_loginListeners.iterator(); it.hasNext();) {
			LoginListener loginListener = (LoginListener) it.next();
			loginListener.loginFailed();
		}
	}

}
