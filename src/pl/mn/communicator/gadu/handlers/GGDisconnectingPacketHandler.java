/*
 * Created on 2004-11-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pl.mn.communicator.gadu.handlers;

import pl.mn.communicator.gadu.GGDisconnecting;

/**
 * @author mateusz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GGDisconnectingPacketHandler implements PacketHandler {

	/**
	 * @see pl.mn.communicator.gadu.handlers.PacketHandler#handle(pl.mn.communicator.gadu.handlers.Context)
	 */
	public void handle(Context context) {
		GGDisconnecting disconnecting = GGDisconnecting.getInstance();
		context.getSessionAccessor().notifyGGPacketReceived(disconnecting);
		context.getSessionAccessor().notifyConnectionClosed();
	}

}
