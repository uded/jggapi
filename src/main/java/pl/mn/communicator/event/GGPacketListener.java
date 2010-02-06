/*
 * Copyright (c) 2003-2005 JGGApi Development Team. All Rights Reserved. This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version. This program is distributed in
 * the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a
 * copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package pl.mn.communicator.event;

import pl.mn.communicator.packet.in.GGIncomingPackage;
import pl.mn.communicator.packet.out.GGOutgoingPackage;

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
 * @version $Id: GGPacketListener.java,v 1.1 2005/11/05 23:34:53 winnetou25 Exp $
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
		 * @see pl.mn.communicator.event.GGPacketListener#receivedPacket(GGIncomingPackage)
		 */
		public void sentPacket(final GGOutgoingPackage outgoingPacket) {
		}

		/**
		 * @see pl.mn.communicator.event.GGPacketListener#receivedPacket(GGIncomingPackage)
		 */
		public void receivedPacket(final GGIncomingPackage incomingPacket) {
		}

	}

}
