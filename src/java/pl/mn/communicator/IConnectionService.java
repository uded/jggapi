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
package pl.mn.communicator;

import pl.mn.communicator.event.ConnectionListener;
import pl.mn.communicator.event.GGPacketListener;
import pl.mn.communicator.event.PingListener;

/**
 * The client should use this interface if there is a need
 * to connect to Gadu-Gadu server or disconnect from it.
 * Also client can register itself as a listener for the connection
 * related events.
 * <p>
 * Created on 2004-11-27
 * 
 * @see pl.mn.communicator.event.ConnectionListener
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: IConnectionService.java,v 1.11 2005-01-31 21:22:15 winnetou25 Exp $
 */
public interface IConnectionService {

	IServer lookupServer(int uin) throws GGException;
	
	/**
	 * Tries to connect to Gadu-Gadu server that has been previously
	 * assigned to <code>Session</code> instance.
	 * 
	 * @throws GGException if error occurs while connecting to Gadu-Gadu server.
	 * @throws GGSessionException if there is an incorrect session state.
	 */
    void connect(IServer server) throws GGException;

    /**
     * Tries to close the connection to the Gadu-Gadu server.
     * 
     * @throws GGSessionException if there is an incorrect session state.
     */
    void disconnect() throws GGException;
    
    /**
     * Method to check if we are connected or not.
     * 
     * @return boolean indicating whether we are connected or not.
     */
    boolean isConnected();
    
    /**
     * Get server user is currently connected to, returns null if user is not connected
     * to any server.
     * 
     * @return
     */
    IServer getServer();
    
    /**
     * Adds <code>ConnectionListener</code> object to the list
     * of listeners that will receive notification of connection
     * related events.
     * 
     * @see pl.mn.communicator.event.ConnectionListener
     * @param connectionListener that will be added to the list of connection listeners.
     * @throws NullPointerException if the <code>ConnectionListener</code> object is null.
     */
    void addConnectionListener(ConnectionListener connectionListener);
    
    /**
     * Remove <code>ConnectionListener</code> object from the list
     * that will receive notification of connection related events.
     * The listener will no longer be notified of connection related events.
     * 
     * @see pl.mn.communicator.event.ConnectionListener
     * @param connectionListener that will be removed from the list of connection listeners.
     * @throws NullPointerException if the <code>ConnectionListener</code> objct is null.
     */
    void removeConnectionListener(ConnectionListener connectionListener);
   
    /**
     * Adds <code>GGPacketListener</code> object to the list of listeners
     * that will be notified of Gadu-Gadu packet related events.
     * 
     * @see pl.mn.communicator.event.GGPacketListener
     * @param packetListener that will be added to the list of packet listeners.
     * @throws NullPointerException if the <code>GGPacketListener</code> instance is null.
     */
    void addPacketListener(GGPacketListener packetListener);

    /**
     * Remove <code>GGPacketListener</code> object from the list of listeners
     * that will be notified of Gadu-Gadu packet related events.
     * 
     * @see pl.mn.communicator.event.GGPacketListener
     * @param packetListener that will be removed from the list of packet listeners.
     * @throws NullPointerException if the <code>GGPacketListener<code> instance is null.
     */
    void removePacketListener(GGPacketListener packetListener);
    
    void addPingListener(PingListener pingListener);
    
    void removePingListener(PingListener pingListener);

}
