/*
 * Created on 2004-11-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pl.mn.communicator;

/**
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
