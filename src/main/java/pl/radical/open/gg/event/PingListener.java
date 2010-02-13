package pl.radical.open.gg.event;

import pl.radical.open.gg.IServer;

import java.util.EventListener;

/**
 * Created on 2005-01-31
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public interface PingListener extends EventListener {

	void pingSent(IServer server);

	void pongReceived(IServer server);

	static class Stub implements PingListener {

		/**
		 * @see pl.radical.open.gg.event.PingListener#pingSent()
		 */
		public void pingSent(final IServer server) {
		}

		/**
		 * @see pl.radical.open.gg.event.PingListener#pongReceived()
		 */
		public void pongReceived(final IServer server) {
		}

	}

}
