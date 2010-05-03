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

	private static final Logger LOG = LoggerFactory.getLogger(DefaultLoginService.class);

	/** The session associated with this service */
	private Session session = null;

	/** The set of <code>LoginListener</code>'s */
	private final Set<LoginListener> loginListeners = new HashSet<LoginListener>();

	private LoginContext loginContext = null;

	// friendly
	DefaultLoginService(final Session session) {
		if (session == null) {
			throw new IllegalArgumentException("session cannot be null");
		}
		this.session = session;
	}

	/**
	 * @see pl.radical.open.gg.ILoginService#login()
	 */
	public void login(final LoginContext loginContext) throws GGException {
		LOG.debug("Logging in, loginContext: " + loginContext);

		if (loginContext == null) {
			throw new IllegalArgumentException("loginContext cannot be null");
		}
		this.loginContext = loginContext;

		if (session.getSessionState() != SessionState.AUTHENTICATION_AWAITING) {
			throw new GGSessionException(session.getSessionState());
		}

		session.getPresenceService().addUserListener(this);

		try {
			final int uin = loginContext.getUin();
			final String password = loginContext.getPassword();
			final int seed = session.getSessionAccessor().getLoginSeed();

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
			session.getSessionAccessor().sendPackage(login);
		} catch (final IOException ex) {
			session.getSessionAccessor().setSessionState(SessionState.DISCONNECTED);
			throw new GGException("Unable to login, loginContext: " + loginContext, ex);
		}
	}

	/**
	 * @see pl.radical.open.gg.ILoginService#logout()
	 */
	public void logout() throws GGException {
		LOG.debug("Logging out, loginContext: " + loginContext);

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
		return loginContext;
	}

	/**
	 * @see pl.radical.open.gg.ILoginService#isLoggedIn()
	 */
	public boolean isLoggedIn() {
		return session.getSessionState() == SessionState.LOGGED_IN;
	}

	/**
	 * @see pl.radical.open.gg.ILoginService#addLoginListener(pl.radical.open.gg.event.LoginListener)
	 */
	public void addLoginListener(final LoginListener loginListener) {
		if (loginListener == null) {
			throw new IllegalArgumentException("loginListener cannot be null");
		}
		loginListeners.add(loginListener);
	}

	/**
	 * @see pl.radical.open.gg.ILoginService#removeLoginListener(pl.radical.open.gg.event.LoginListener)
	 */
	public void removeLoginListener(final LoginListener loginListener) {
		if (loginListener == null) {
			throw new IllegalArgumentException("loginListener cannot  be null");
		}
		loginListeners.remove(loginListener);
	}

	protected void notifyLoginOK() throws GGException {
		session.getSessionAccessor().setSessionState(SessionState.LOGGED_IN);
		for (final Object element : loginListeners) {
			final LoginListener loginListener = (LoginListener) element;
			loginListener.loginOK();
		}
	}

	protected void notifyLoginFailed(final LoginFailedEvent loginFailedEvent) throws GGException {
		session.getSessionAccessor().setSessionState(SessionState.CONNECTION_ERROR);
		for (final Object element : loginListeners) {
			final LoginListener loginListener = (LoginListener) element;
			loginListener.loginFailed(loginFailedEvent);
		}
	}

	protected void notifyLoggedOut() throws GGException {
		for (final Object element : loginListeners) {
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
		if (session.getSessionState() != SessionState.LOGGED_IN) {
			throw new GGSessionException(session.getSessionState());
		}
	}

	private void logout(final boolean offLineWithStatus, final String description, final Date returnDate, final boolean sendStatus) throws GGException {
		checkSessionState();

		if (!offLineWithStatus) {
			if (sendStatus) {
				final ILocalStatus localStatus = new LocalStatus(StatusType.OFFLINE);
				session.getPresenceService().setStatus(localStatus);
			}
			session.getSessionAccessor().setSessionState(SessionState.LOGGED_OUT);
			loginContext = null;
			session.getSessionAccessor().notifyLoggedOut();
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
					session.getPresenceService().setStatus(localStatus);
				}
				loginContext = null;
				session.getSessionAccessor().setSessionState(SessionState.LOGGED_OUT);
				notifyLoggedOut();
			}
		}

	}

}
