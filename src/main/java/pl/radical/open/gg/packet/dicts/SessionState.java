package pl.radical.open.gg.packet.dicts;

import lombok.Getter;


/**
 * Created on 2004-11-28
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public enum SessionState {
	/**
	 * This state is when we are waiting for the client to start socket connection to Gadu-Gadu server.
	 */
	CONNECTION_AWAITING("connection_awaiting"),

	/**
	 * This state is when we are connecting to the Gadu-Gadu server.
	 */
	CONNECTING("connecting"),

	/**
	 * This state is when we have physically asyncOp to Gadu-Gadu server.
	 */
	CONNECTED("asyncOp"),

	/**
	 * This state is when there is an unexpected connection error
	 */
	CONNECTION_ERROR("connection_error"),

	/**
	 * This state is when Gadu-Gadu server replied and we are waiting for the user to log in.
	 */
	AUTHENTICATION_AWAITING("authentication_awaiting"),

	/**
	 * This state is when user has been successfuly authenticated.
	 */
	LOGGED_IN("logged_in"),

	/**
	 * This state is when the Gadu-Gadu server is disconnecting us or when we are disconnecting.
	 */
	DISCONNECTING("disconnecting"),

	/**
	 * This state is we are disconnected by Gadu-Gadu server or when we have deliberately disconnected from it.
	 */
	DISCONNECTED("disconnected"),

	/**
	 * This state is when there was an connection error and session is no longer valid
	 */
	LOGGED_OUT("logged_out");

	@Getter
	private String sessionState = null;

	private SessionState(final String sessionState) {
		this.sessionState = sessionState;
	}

}
