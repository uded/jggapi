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

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pl.mn.communicator.event.LoginFailedEvent;
import pl.mn.communicator.event.LoginListener;
import pl.mn.communicator.event.UserListener;
import pl.mn.communicator.packet.out.GGLogin60;

/**
 * The default implementation of <code>ILoginService</code>.
 * <p>
 * Created on 2004-11-28
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: DefaultLoginService.java,v 1.1 2005-11-05 23:34:52 winnetou25 Exp $
 */
public class DefaultLoginService implements ILoginService, UserListener {
    
    private final static Log logger = LogFactory.getLog(DefaultLoginService.class);
    
    /** The session associated with this service */
    private Session m_session = null;
    
    /** The set of <code>LoginListener</code>'s */
    private Vector m_loginListeners = new Vector();
    
    private LoginContext m_loginContext = null;
    
    //friendly
    DefaultLoginService(Session session) {
        if (session == null) throw new NullPointerException("session cannot be null");
        m_session = session;
    }
    
    /**
     * @see pl.mn.communicator.ILoginService#login()
     */
    public void login(LoginContext loginContext) throws GGException {
        logger.debug("Logging in, loginContext: "+loginContext);
        
        if (loginContext == null) throw new NullPointerException("loginContext cannot be null");
        m_loginContext = loginContext;
        
        if (m_session.getSessionState() != SessionState.AUTHENTICATION_AWAITING) {
            throw new GGSessionException(m_session.getSessionState());
        }
        
        m_session.getPresenceService().addUserListener(this);
        
        try {
            int uin = loginContext.getUin();
            String password = loginContext.getPassword();
            int seed = m_session.getSessionAccessor().getLoginSeed();
            
            GGLogin60 login = new GGLogin60(uin, password.toCharArray(), seed);
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
            throw new GGException("Unable to login, loginContext: "+loginContext, ex);
        }
    }
    
    /**
     * @see pl.mn.communicator.ILoginService#logout()
     */
    public void logout() throws GGException {
        logger.debug("Logging out, loginContext: "+m_loginContext);

        logout(false, null, null, true);
    }
    
    /**
     * @see pl.mn.communicator.ILoginService#logout(java.lang.String, java.util.Date returnTime)
     */
    public void logout(String description, Date returnTime) throws GGException {
        if (description == null) description = "";
        checkSessionState();
        logout(true, description, returnTime, true);
    }
    
    /**
     * @see pl.mn.communicator.ILoginService#getLoginContext()
     */
    public LoginContext getLoginContext() {
        return m_loginContext;
    }
    
    /**
     * @see pl.mn.communicator.ILoginService#isLoggedIn()
     */
    public boolean isLoggedIn() {
        return (m_session.getSessionState() == SessionState.LOGGED_IN);
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
    
    protected void notifyLoginOK() throws GGException {
        m_session.getSessionAccessor().setSessionState(SessionState.LOGGED_IN);
        for (Iterator it = m_loginListeners.iterator(); it.hasNext();) {
            LoginListener loginListener = (LoginListener) it.next();
            loginListener.loginOK();
        }
    }
    
    protected void notifyLoginFailed(final LoginFailedEvent loginFailedEvent) throws GGException {
        m_session.getSessionAccessor().setSessionState(SessionState.CONNECTION_ERROR);
        for (Iterator it = m_loginListeners.iterator(); it.hasNext();) {
            LoginListener loginListener = (LoginListener) it.next();
            loginListener.loginFailed(loginFailedEvent);
        }
    }
    
    protected void notifyLoggedOut() throws GGException {
        for (Iterator it = m_loginListeners.iterator(); it.hasNext();) {
            LoginListener loginListener = (LoginListener) it.next();
            loginListener.loggedOut();
        }
    }
    
    /**
     * @see pl.mn.communicator.event.UserListener#localStatusChanged(pl.mn.communicator.ILocalStatus)
     */
    public void localStatusChanged(ILocalStatus localStatus) throws GGException {
        if (localStatus.getStatusType() == StatusType.OFFLINE) {
            logout(false, null, null, false);
        } else if (localStatus.getStatusType() == StatusType.OFFLINE_WITH_DESCRIPTION) {
            logout(true, localStatus.getDescription(), localStatus.getReturnDate(), false);
        }
    }
    
    /**
     * @see pl.mn.communicator.event.UserListener#userStatusChanged(pl.mn.communicator.IUser, pl.mn.communicator.IRemoteStatus)
     */
    public void userStatusChanged(IUser user, IRemoteStatus newStatus) throws GGException { }
    
    private void checkSessionState() throws GGException {
        if (m_session.getSessionState() != SessionState.LOGGED_IN) {
            throw new GGSessionException(m_session.getSessionState());
        }
    }

    private void logout(boolean offLineWithStatus, String description, Date returnDate, boolean sendStatus) throws GGException {
        checkSessionState();

        if (!offLineWithStatus) {
            if (sendStatus) {
                ILocalStatus localStatus = new LocalStatus(StatusType.OFFLINE);
                m_session.getPresenceService().setStatus(localStatus);
            }
            m_session.getSessionAccessor().setSessionState(SessionState.LOGGED_OUT);
            m_loginContext = null;
            m_session.getSessionAccessor().notifyLoggedOut();
        } else {
            if (description == null) throw new IllegalArgumentException("description cannot be null");
            if (description.length() > 0) {
                if (sendStatus) {
                    LocalStatus localStatus = new LocalStatus(StatusType.OFFLINE_WITH_DESCRIPTION, description);
                    if (returnDate != null) {
                        localStatus.setReturnDate(returnDate);
                    }
                    m_session.getPresenceService().setStatus(localStatus);
                }
                m_loginContext = null;
                m_session.getSessionAccessor().setSessionState(SessionState.LOGGED_OUT);
                notifyLoggedOut();
            }
        }
        
    }

}
