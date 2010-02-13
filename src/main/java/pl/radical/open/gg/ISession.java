package pl.radical.open.gg;

import pl.radical.open.gg.event.SessionStateListener;

/**
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public interface ISession {

	/**
	 * Returns state of this session.
	 * 
	 * @return <code>SessionState</code>
	 */
	SessionState getSessionState();

	IGGConfiguration getGGConfiguration();

	IConnectionService getConnectionService();

	ILoginService getLoginService();

	IMessageService getMessageService();

	IPresenceService getPresenceService();

	IContactListService getContactListService();

	IPublicDirectoryService getPublicDirectoryService();

	IRegistrationService getRegistrationService();

	void addSessionStateListener(SessionStateListener sessionStateListener);

	void removeSessionStateListener(SessionStateListener sessionStateListener);

}
