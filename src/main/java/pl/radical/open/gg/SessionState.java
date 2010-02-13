package pl.radical.open.gg;

/**
 * Created on 2004-11-28
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class SessionState {

	private String m_sessionState = null;

	private SessionState(final String sessionState) {
		m_sessionState = sessionState;
	}

	/** This state is when we are waiting for the client to start socket connection to Gadu-Gadu server. */
	public final static SessionState CONNECTION_AWAITING = new SessionState("connection_awaiting");

	/** This state is when we are connecting to the Gadu-Gadu server. */
	public final static SessionState CONNECTING = new SessionState("connecting");

	/** This state is when we have physically asyncOp to Gadu-Gadu server. */
	public final static SessionState CONNECTED = new SessionState("asyncOp");

	/** This state is when there is an unexpected connection error */
	public final static SessionState CONNECTION_ERROR = new SessionState("connection_error");

	/** This state is when Gadu-Gadu server replied and we are waiting for the user to log in. */
	public final static SessionState AUTHENTICATION_AWAITING = new SessionState("authentication_awaiting");

	/** This state is when user has been successfuly authenticated. */
	public final static SessionState LOGGED_IN = new SessionState("logged_in");

	/** This state is when the Gadu-Gadu server is disconnecting us or when we are disconnecting. */
	public final static SessionState DISCONNECTING = new SessionState("disconnecting");

	/** This state is we are disconnected by Gadu-Gadu server or when we have deliberately disconnected from it. */
	public final static SessionState DISCONNECTED = new SessionState("disconnected");

	/** This state is when there was an connection error and session is no longer valid */
	public final static SessionState LOGGED_OUT = new SessionState("logged_out");

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return m_sessionState;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return m_sessionState.hashCode();
	}

}
