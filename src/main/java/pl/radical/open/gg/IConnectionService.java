package pl.radical.open.gg;

import pl.radical.open.gg.event.ConnectionListener;
import pl.radical.open.gg.event.GGPacketListener;
import pl.radical.open.gg.event.PingListener;

/**
 * The client should use this interface if there is a need to connect to Gadu-Gadu server or disconnect from it. Also
 * client can register itself as a listener for the connection related events.
 * <p>
 * Created on 2004-11-27
 * 
 * @see pl.radical.open.gg.event.ConnectionListener
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public interface IConnectionService {

	/**
	 * Looks up IServer for particular UIN, queries Gadu-Gadu service. Note: Do not use this method, if you want to use
	 * your own bespoke GG server.
	 * 
	 * @param uin
	 *            - uin to lookup IServer instance for
	 * @return - instance of IServer
	 * @throws GGException
	 *             - exception in case there was a problem during lookup
	 */
	IServer lookupServer(int uin) throws GGException;

	/**
	 * Tries to connect to Gadu-Gadu server that has been previously assigned to <code>Session</code> instance.
	 * 
	 * @throws GGException
	 *             if error occurs while connecting to Gadu-Gadu server.
	 * @throws GGSessionException
	 *             if there is an incorrect session state.
	 */
	void connect(IServer server) throws GGException;

	/**
	 * Tries to close the connection to the Gadu-Gadu server.
	 * 
	 * @throws GGSessionException
	 *             if there is an incorrect session state.
	 */
	void disconnect() throws GGException;

	/**
	 * Method to check if we are asyncOp or not.
	 * 
	 * @return boolean indicating whether we are asyncOp or not.
	 */
	boolean isConnected();

	/**
	 * Get server user is currently asyncOp to, returns null if user is not asyncOp to any server.
	 * 
	 * @return
	 */
	IServer getServer();

	/**
	 * Adds <code>ConnectionListener</code> object to the list of listeners that will receive notification of connection
	 * related events.
	 * 
	 * @see pl.radical.open.gg.event.ConnectionListener
	 * @param connectionListener
	 *            that will be added to the list of connection listeners.
	 * @throws NullPointerException
	 *             if the <code>ConnectionListener</code> object is null.
	 */
	void addConnectionListener(ConnectionListener connectionListener);

	/**
	 * Remove <code>ConnectionListener</code> object from the list that will receive notification of connection related
	 * events. The listener will no longer be notified of connection related events.
	 * 
	 * @see pl.radical.open.gg.event.ConnectionListener
	 * @param connectionListener
	 *            that will be removed from the list of connection listeners.
	 * @throws NullPointerException
	 *             if the <code>ConnectionListener</code> objct is null.
	 */
	void removeConnectionListener(ConnectionListener connectionListener);

	/**
	 * Adds <code>GGPacketListener</code> object to the list of listeners that will be notified of Gadu-Gadu packet
	 * related events.
	 * 
	 * @see pl.radical.open.gg.event.GGPacketListener
	 * @param packetListener
	 *            that will be added to the list of packet listeners.
	 * @throws NullPointerException
	 *             if the <code>GGPacketListener</code> instance is null.
	 */
	void addPacketListener(GGPacketListener packetListener);

	/**
	 * Remove <code>GGPacketListener</code> object from the list of listeners that will be notified of Gadu-Gadu packet
	 * related events.
	 * 
	 * @see pl.radical.open.gg.event.GGPacketListener
	 * @param packetListener
	 *            that will be removed from the list of packet listeners.
	 * @throws NullPointerException
	 *             if the <code>GGPacketListener<code> instance is null.
	 */
	void removePacketListener(GGPacketListener packetListener);

	void addPingListener(PingListener pingListener);

	void removePingListener(PingListener pingListener);

}
