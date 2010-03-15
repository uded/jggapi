package pl.radical.open.gg.event;

import pl.radical.open.gg.packet.GGIncomingPackage;
import pl.radical.open.gg.packet.GGOutgoingPackage;

import java.util.EventListener;

/**
 * The listener interface for receiving packet related events.
 * <p>
 * The class that implements this interface, so-called GGPacketHandler is notified of packet sent and received events.
 * <p>
 * It is highly recommended that this listener is only used by an experienced developer that really knows how to handle
 * events.
 * <p>
 * Created on 2004-12-11
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public interface GGPacketListener extends EventListener {

	/**
	 * Notification that Gadu-Gadu outgoing packet has been sent to the Gadu-Gadu server.
	 * 
	 * @param outgoingPacket
	 *            the packet that has been sent to the Gadu-Gadu server.
	 */
	void sentPacket(GGOutgoingPackage outgoingPacket);

	/**
	 * Notification that Gadu-Gadu incoming packet has been received.
	 * 
	 * @param incomingPacket
	 *            the packet that has been received from the Gadu-Gadu server.
	 */
	void receivedPacket(GGIncomingPackage incomingPacket);

	public static class Stub implements GGPacketListener {

		/**
		 * @see pl.radical.open.gg.event.GGPacketListener#receivedPacket(GGIncomingPackage)
		 */
		public void sentPacket(final GGOutgoingPackage outgoingPacket) {
		}

		/**
		 * @see pl.radical.open.gg.event.GGPacketListener#receivedPacket(GGIncomingPackage)
		 */
		public void receivedPacket(final GGIncomingPackage incomingPacket) {
		}

	}

}
