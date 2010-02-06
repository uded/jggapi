/*
 * Copyright (c) 2003-2005 JGGApi Development Team. All Rights Reserved. This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version. This program is distributed in
 * the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a
 * copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package pl.mn.communicator;

/**
 * Created on 2004-11-28
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: SessionState.java,v 1.1 2005/11/05 23:34:52 winnetou25 Exp $
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

	/** This state is when we have physically connected to Gadu-Gadu server. */
	public final static SessionState CONNECTED = new SessionState("connected");

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
