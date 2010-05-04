package pl.radical.open.gg.event;

import pl.radical.open.gg.GGException;

import java.util.EventListener;

/**
 * The listener interface for receiving connection related events. It notifies whether connection is established, closed
 * or an error occurs during connection.
 * <p>
 * This listener also notifies that pong has been received from Gadu-Gadu server.
 * <p>
 * Created on 2004-12-11
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public interface ConnectionListener extends EventListener {

	/**
	 * The notification that connection to the server has been successfuly established.
	 */
	void connectionEstablished() throws GGException;

	/**
	 * The notification that connection to the server has been delibately closed.
	 */
	void connectionClosed() throws GGException;

	/**
	 * Notification that there was an unexpected error with the connection.
	 */
	void connectionError(Exception e) throws GGException;

	class Stub implements ConnectionListener {

		/**
		 * @see pl.radical.open.gg.event.ConnectionListener#connectionEstablished()
		 */
		public void connectionEstablished() throws GGException {
		}

		/**
		 * @see pl.radical.open.gg.event.ConnectionListener#connectionClosed()
		 */
		public void connectionClosed() throws GGException {
		}

		/**
		 * @see pl.radical.open.gg.event.ConnectionListener#connectionError(Exception)
		 */
		public void connectionError(final Exception ex) throws GGException {
		}

	}

}
