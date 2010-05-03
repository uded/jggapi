package pl.radical.open.gg;

import pl.radical.open.gg.event.LoginFailedEvent;
import pl.radical.open.gg.event.LoginListener;
import pl.radical.open.gg.event.UserListener;
import pl.radical.open.gg.packet.out.GGLogin80;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The default implementation of <code>ILoginService</code>.
 * <p>
 * Created on 2004-11-28
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @author <a href="mailto:lukasz.rzanek@radical.com.pl>Łukasz Rżanek</a>
 */
public class DefaultLoginService implements ILoginService, UserListener {

	private static final Logger log = LoggerFactory.getLogger(DefaultLoginService.class);

	/** The session associated with this service */
	private Session m_session = null;

	/** The set of <code>LoginListener</code>'s */
	private final Set<LoginListener> m_loginListeners = new HashSet<LoginListener>();

	private LoginContext m_loginContext = null;

	// friendly
	DefaultLoginService(final Session session) {
		if (session == null) {
			throw new IllegalArgumentException("session cannot be null");
		}
		m_session = session;
	}

	/**
	 * @see pl.radical.open.gg.ILoginService#login()
	 */
	public void login(final LoginContext loginContext) throws GGException {
		log.debug("Logging in, loginContext: " + loginContext);

		if (loginContext == null) {
			throw new IllegalArgumentException("loginContext cannot be null");
		}
		m_loginContext = loginContext;

		if (m_session.getSessionState() != SessionState.AUTHENTICATION_AWAITING) {
			throw new GGSessionException(m_session.getSessionState());
		}

		m_session.getPresenceService().addUserListener(this);

		try {
			final int uin = loginContext.getUin();
			final String password = loginContext.getPassword();
			final int seed = m_session.getSessionAccessor().getLoginSeed();

			final GGLogin80 login = new GGLogin80(uin, password.toCharArray(), seed);
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
		} catch (final IOException ex) {
			m_session.getSessionAccessor().setSessionState(SessionState.DISCONNECTED);
			throw new GGException("Unable to login, loginContext: " + loginContext, ex);
		}
	}

	/**
	 * @see pl.radical.open.gg.ILoginService#logout()
	 */
	public void logout() throws GGException {
		log.debug("Logging out, loginContext: " + m_loginContext);

		logout(false, null, null, true);
	}

	/**
	 * @see pl.radical.open.gg.ILoginService#logout(java.lang.String, java.util.Date returnTime)
	 */
	public void logout(String description, final Date returnTime) throws GGException {
		if (description == null) {
			description = "";
		}
		checkSessionState();
		logout(true, description, returnTime, true);
	}

	/**
	 * @see pl.radical.open.gg.ILoginService#getLoginContext()
	 */
	public LoginContext getLoginContext() {
		return m_loginContext;
	}

	/**
	 * @see pl.radical.open.gg.ILoginService#isLoggedIn()
	 */
	public boolean isLoggedIn() {
		return m_session.getSessionState() == SessionState.LOGGED_IN;
	}

	/**
	 * @see pl.radical.open.gg.ILoginService#addLoginListener(pl.radical.open.gg.event.LoginListener)
	 */
	public void addLoginListener(final LoginListener loginListener) {
		if (loginListener == null) {
			throw new IllegalArgumentException("loginListener cannot be null");
		}
		m_loginListeners.add(loginListener);
	}

	/**
	 * @see pl.radical.open.gg.ILoginService#removeLoginListener(pl.radical.open.gg.event.LoginListener)
	 */
	public void removeLoginListener(final LoginListener loginListener) {
		if (loginListener == null) {
			throw new IllegalArgumentException("loginListener cannot  be null");
		}
		m_loginListeners.remove(loginListener);
	}

	protected void notifyLoginOK() throws GGException {
		m_session.getSessionAccessor().setSessionState(SessionState.LOGGED_IN);
		for (final Object element : m_loginListeners) {
			final LoginListener loginListener = (LoginListener) element;
			loginListener.loginOK();
		}
	}

	protected void notifyLoginFailed(final LoginFailedEvent loginFailedEvent) throws GGException {
		m_session.getSessionAccessor().setSessionState(SessionState.CONNECTION_ERROR);
		for (final Object element : m_loginListeners) {
			final LoginListener loginListener = (LoginListener) element;
			loginListener.loginFailed(loginFailedEvent);
		}
	}

	protected void notifyLoggedOut() throws GGException {
		for (final Object element : m_loginListeners) {
			final LoginListener loginListener = (LoginListener) element;
			loginListener.loggedOut();
		}
	}

	/**
	 * @see pl.radical.open.gg.event.UserListener#localStatusChanged(pl.radical.open.gg.ILocalStatus)
	 */
	public void localStatusChanged(final ILocalStatus localStatus) throws GGException {
		if (localStatus.getStatusType() == StatusType.OFFLINE) {
			logout(false, null, null, false);
		} else if (localStatus.getStatusType() == StatusType.OFFLINE_WITH_DESCRIPTION) {
			logout(true, localStatus.getDescription(), localStatus.getReturnDate(), false);
		}
	}

	/**
	 * @see pl.radical.open.gg.event.UserListener#userStatusChanged(pl.radical.open.gg.IUser,
	 *      pl.radical.open.gg.IRemoteStatus)
	 */
	public void userStatusChanged(final IUser user, final IRemoteStatus newStatus) throws GGException {
	}

	private void checkSessionState() throws GGException {
		if (m_session.getSessionState() != SessionState.LOGGED_IN) {
			throw new GGSessionException(m_session.getSessionState());
		}
	}

	private void logout(final boolean offLineWithStatus, final String description, final Date returnDate, final boolean sendStatus) throws GGException {
		checkSessionState();

		if (!offLineWithStatus) {
			if (sendStatus) {
				final ILocalStatus localStatus = new LocalStatus(StatusType.OFFLINE);
				m_session.getPresenceService().setStatus(localStatus);
			}
			m_session.getSessionAccessor().setSessionState(SessionState.LOGGED_OUT);
			m_loginContext = null;
			m_session.getSessionAccessor().notifyLoggedOut();
		} else {
			if (description == null) {
				throw new IllegalArgumentException("description cannot be null");
			}
			if (description.length() > 0) {
				if (sendStatus) {
					final LocalStatus localStatus = new LocalStatus(StatusType.OFFLINE_WITH_DESCRIPTION, description);
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
