/*
 * Created on 2004-12-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pl.mn.communicator;

import java.util.EventListener;

import pl.mn.communicator.gadu.GGIncomingPackage;
import pl.mn.communicator.gadu.GGOutgoingPackage;

/**
 * @author mateusz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface GGPacketListener extends EventListener {

	void sentPacket(GGOutgoingPackage outgoingPacket);
	
	void receivedPacket(GGIncomingPackage incomingPacket);
	
	public static class Stub implements GGPacketListener {

		/**
		 * @see pl.mn.communicator.GGPacketListener#sentPacket(pl.mn.communicator.gadu.GGOutgoingPackage)
		 */
		public void sentPacket(GGOutgoingPackage outgoingPacket) { }

		/**
		 * @see pl.mn.communicator.GGPacketListener#receivedPacket(pl.mn.communicator.gadu.GGIncomingPackage)
		 */
		public void receivedPacket(GGIncomingPackage incomingPacket) { }
		
	}
	
}
