package pl.radical.open.gg.event;

import pl.radical.open.gg.packet.dicts.SessionState;

import java.util.EventListener;

/**
 * The listener interface that is used by classes that want to be notified of <code>SessionState</code> related events.
 * <p>
 * Created on 2004-11-28
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public interface SessionStateListener extends EventListener {

	/**
	 * Messaged when session state changes.
	 * 
	 * @param oldSessionState
	 *            the previous <code>SessionState</code> instance.
	 * @param newSessionState
	 *            the actual <code>SessionState</code> instance.
	 */
	void sessionStateChanged(SessionState oldSessionState, SessionState newSessionState);

}
