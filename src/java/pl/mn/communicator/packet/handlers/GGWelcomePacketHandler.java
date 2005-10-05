/*
 * Copyright (c) 2003-2005 JGGApi Development Team. All Rights Reserved.
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package pl.mn.communicator.packet.handlers;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pl.mn.communicator.GGException;
import pl.mn.communicator.SessionState;
import pl.mn.communicator.packet.GGUtils;
import pl.mn.communicator.packet.in.GGWelcome;

/**
 * Created on 2004-11-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGWelcomePacketHandler.java,v 1.13 2005-10-05 20:04:20 winnetou25 Exp $
 */
public class GGWelcomePacketHandler implements PacketHandler {

	private static final Log logger = LogFactory.getLog(GGWelcomePacketHandler.class);

	/**
	 * @see pl.mn.communicator.packet.handlers.PacketHandler#handle(pl.mn.communicator.packet.handlers.Context)
	 */
	public void handle(PacketContext context) throws GGException {
		if (logger.isDebugEnabled()) {
			logger.debug("GGWelcome packet received.");
			logger.debug("PacketHeader: "+context.getHeader());
			logger.debug("PacketLoad: "+GGUtils.prettyBytesToString(context.getPackageContent()));
		}

		GGWelcome welcome = new GGWelcome(context.getPackageContent());
		context.getSessionAccessor().notifyGGPacketReceived(welcome);
		context.getSessionAccessor().setLoginSeed(welcome.getSeed());
		context.getSessionAccessor().setSessionState(SessionState.CONNECTED);
		context.getSessionAccessor().notifyConnectionEstablished();
	}

}
