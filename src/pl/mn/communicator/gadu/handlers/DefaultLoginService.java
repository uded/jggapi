/*
 * Created on 2004-11-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pl.mn.communicator.gadu.handlers;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import pl.mn.communicator.GGException;
import pl.mn.communicator.GGSessionException;
import pl.mn.communicator.ILoginService;
import pl.mn.communicator.IStatus;
import pl.mn.communicator.LoginContext;
import pl.mn.communicator.LoginListener;
import pl.mn.communicator.SessionState;
import pl.mn.communicator.Status;
import pl.mn.communicator.StatusConst;
import pl.mn.communicator.gadu.GGLogin;

/**
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
	public void login(LoginContext loginContext) throws GGException {
		if (m_session.getSessionState() == SessionState.AUTHENTICATION_AWAITING) {
			try {
				int uin = loginContext.getUin();
				String password = loginContext.getPassword();
				int seed = m_session.getIntegerAttribute(Session.SEED);
				//how to access socket in order to get localAddress and localPort
				GGLogin login = new GGLogin(new byte[0], -1, uin, password.toCharArray(), seed);
				m_session.getSessionAccessor().sendPackage(login);
			} catch (IOException ex) {
				m_session.getSessionAccessor().setSessionState(SessionState.DISCONNECTED);
				throw new GGException("Unable to login, loginContext: "+loginContext, ex);
			}
		} else {
			throw new GGSessionException("Unable to login, wrong session state: "+SessionState.getState(m_session.getSessionState()));
		}
	}

	/**
	 * @see pl.mn.communicator.ILoginService#logout()
	 */
	public void logout() throws GGException {
		if (m_session.getSessionState() == SessionState.AUTHENTICATED) {
			m_session.getPresenceService().setStatus(new Status(StatusConst.OFFLINE));
			m_session.getSessionAccessor().setSessionState(SessionState.SESSION_INVALID);
		} else {
			throw new GGSessionException("Unable to logout, wrong session state: "+SessionState.getState(m_session.getSessionState()));
		}
	}
	
	/**
	 * @see pl.mn.communicator.ILoginService#logout(java.lang.String)
	 */
	public void logout(String description) throws GGException {
		if (description == null) throw new NullPointerException("description cannot be null");
		if (m_session.getSessionState() == SessionState.AUTHENTICATED) {
			if (description.length() > 0) {
				IStatus status = new Status(StatusConst.OFFLINE_WITH_DESCRIPTION);
				status.setDescription(description);
				m_session.getPresenceService().setStatus(status);
				m_session.getSessionAccessor().setSessionState(SessionState.SESSION_INVALID);
			}
		} else {
			throw new GGSessionException("Unable to logout, wrong session state: "+SessionState.getState(m_session.getSessionState()));
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
		m_session.getSessionAccessor().setSessionState(SessionState.AUTHENTICATED);
		for (Iterator it = m_loginListeners.iterator(); it.hasNext();) {
			LoginListener loginListener = (LoginListener) it.next();
			loginListener.loginOK();
		}
	}
	
	protected void notifyLoginFailed() {
		m_session.getSessionAccessor().setSessionState(SessionState.AUTHENTICATION_AWAITING);
		for (Iterator it = m_loginListeners.iterator(); it.hasNext();) {
			LoginListener loginListener = (LoginListener) it.next();
			loginListener.loginFailed();
		}
	}

}
