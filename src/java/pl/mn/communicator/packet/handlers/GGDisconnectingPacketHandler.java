/*
 * Copyright (c) 2003 Marcin Naglik (mnaglik@gazeta.pl)
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

import pl.mn.communicator.packet.in.GGDisconnecting;

/**
 * Created on 2004-11-28
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGDisconnectingPacketHandler.java,v 1.2 2004-12-18 16:47:20 winnetou25 Exp $
 */
public class GGDisconnectingPacketHandler implements PacketHandler {

	private final static Log logger = LogFactory.getLog(GGDisconnectingPacketHandler.class);
	
	/**
	 * @see pl.mn.communicator.packet.handlers.PacketHandler#handle(pl.mn.communicator.gadu.handlers.Context)
	 */
	public void handle(Context context) {
		if (logger.isDebugEnabled()) {
			logger.debug("GGDisconnecting packet received.");
		}

		GGDisconnecting disconnecting = GGDisconnecting.getInstance();
		context.getSessionAccessor().notifyGGPacketReceived(disconnecting);
		context.getSessionAccessor().notifyConnectionClosed();
	}

}
