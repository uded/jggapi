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
package pl.mn.communicator;

/**
 * Created on 2004-11-28
 * 
 * @author mateusz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SessionState {

	/** This state is when we are waiting for the client to start socket connection to Gadu-Gadu server. */ 
	public final static int CONNECTION_AWAITING = 0;

	/** This state is when we are connecting to the Gadu-Gadu server. */
	public final static int CONNECTING = 1;

	/** This state is when we have physically connected to Gadu-Gadu server. */
	public final static int CONNECTED = 2;
	
	/** This state is when there is an unexpected connection error */
	public final static int CONNECTION_ERROR = 3;

	/** This state is when Gadu-Gadu server replied and we are waiting for the user to log in. */
	public final static int AUTHENTICATION_AWAITING = 4;
	
	/** This state is when user has been successfuly authenticated. */
	public final static int AUTHENTICATED = 5;
	
	/** This state is when the Gadu-Gadu server is disconnecting us or when we are disconnecting. */
	public final static int DISCONNECTING = 6;

	/** This state is we are disconnected by Gadu-Gadu server or when we have deliberately disconnected from it. */
	public final static int DISCONNECTED = 7;
	
	/** This state is when there was an connection error and session is no longer valid */
	public final static int SESSION_INVALID = 8;

	public static String getState(int state) {
		switch (state) {
			case CONNECTION_AWAITING: return "connection_awaiting";
			case CONNECTING: return "connecting";
			case CONNECTED: return "connected";
			case CONNECTION_ERROR: return "connection_error";
			case AUTHENTICATION_AWAITING: return "authentication_awaiting";
			case AUTHENTICATED: return "authenticated";
			case DISCONNECTING: return "disconnecting";
			case DISCONNECTED: return "disconnected";
			case SESSION_INVALID: return "session_invalid";
			default: throw new RuntimeException("Unable to convert: "+state);
		}
	}
	
}
