/*
 * Created on 2004-12-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pl.mn.communicator.gadu.handlers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pl.mn.communicator.IStatus60;
import pl.mn.communicator.IUser;
import pl.mn.communicator.gadu.GGUtils;
import pl.mn.communicator.gadu.in.GGStatus60;

/**
 * @author mateusz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GGStatus60PacketHandler implements PacketHandler {

	private final static Log logger = LogFactory.getLog(GGStatus60PacketHandler.class);
	
	/**
	 * @see pl.mn.communicator.gadu.handlers.PacketHandler#handle(pl.mn.communicator.gadu.handlers.Context)
	 */
	public void handle(Context context) {
		logger.debug("Received GGStatus60 packet");
		logger.debug("PacketHeader: "+context.getHeader());
		logger.debug("PacketLoad: "+GGUtils.bytesToString(context.getPackageContent()));
		
		GGStatus60 status60 = new GGStatus60(context.getPackageContent());
		
		IUser user = status60.getUser();
		IStatus60 status = status60.getStatus60();
		context.getSessionAccessor().notifyUserChangedStatus(user, status);
	}

}
