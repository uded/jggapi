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

import pl.mn.communicator.event.ConnectionListener;
import pl.mn.communicator.event.GGPacketListener;

/**
 * Created on 2004-11-27
 * 
 * @author mateusz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface IConnectionService {

	/**
	 * Connects to Gadu-Gadu server.
	 * @throws GGException if there is a error while connecting to Gadu-Gadu server.
	 */
    void connect() throws GGException;

    /**
     * Closes connection with Gadu-Gadu server.
     */
    void disconnect();
    
    boolean isConnected();
    
    /**
     * Adds <code>ConnectionListener</code> object
     * @param connectionListener
     */
    void addConnectionListener(ConnectionListener connectionListener);
    
    /**
     * Removed <code>ConnectionListener</code> object
     * @param connectionListener
     */
    void removeConnectionListener(ConnectionListener connectionListener);
   
    /**
     * Adds <code>GGPacketListener</code> object
     * @param packetListener
     */
    void addPacketListener(GGPacketListener packetListener);

    /**
     * Remove <code>GGPacketListener</code> object
     * @param packetListener
     */
    void removePacketListener(GGPacketListener packetListener);

}
