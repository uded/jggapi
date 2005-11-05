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
package pl.mn.communicator.event;

import java.util.EventListener;

import pl.mn.communicator.GGException;

/**
 * The listener interface for receiving connection related events.
 * It notifies whether connection is established, closed or an error
 * occurs during connection.
 * <p>
 * This listener also notifies that pong has been received from 
 * Gadu-Gadu server.
 * <p>
 * Created on 2004-12-11
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: ConnectionListener.java,v 1.1 2005-11-05 23:34:53 winnetou25 Exp $
 */
public interface ConnectionListener extends EventListener {
	
    /** 
     * The notification that connection to the server
     * has been successfuly established.
     */
    void connectionEstablished() throws GGException;

    /** 
     * The notification that connection to the server
     * has been delibately closed.
     */
    void connectionClosed() throws GGException;

    /**
     * Notification that there was an unexpected error
     * with the connection.
     */
    void connectionError(Exception e) throws GGException;
   
    public static class Stub implements ConnectionListener {

		/**
		 * @see pl.mn.communicator.event.ConnectionListener#connectionEstablished()
		 */
		public void connectionEstablished() throws GGException { }

		/**
		 * @see pl.mn.communicator.event.ConnectionListener#connectionClosed()
		 */
		public void connectionClosed() throws GGException { }

		/**
		 * @see pl.mn.communicator.event.ConnectionListener#connectionError(Exception)
		 */
		public void connectionError(Exception ex) throws GGException { }
		
    }
    
}
