/*
 * Created on 2004-11-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pl.mn.communicator.gadu.handlers;

import pl.mn.communicator.gadu.GGWelcome;

/**
 * @author mateusz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GGWelcomePacketHandler implements PacketHandler {

	/**
	 * @see pl.mn.communicator.gadu.handlers.PacketHandler#handle(pl.mn.communicator.gadu.handlers.PacketContext)
	 */
	public void handle(Context context) {
		GGWelcome welcome = new GGWelcome(context.getPackageContent());
		context.getSessionAccessor().notifyGGPacketReceived(welcome);
		context.getSessionAccessor().setIntegerAttribute(Session.SEED, welcome.getSeed());
		context.getSessionAccessor().notifyConnectionEstablished();
	}
	
}
