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

import pl.mn.communicator.IStatus60;
import pl.mn.communicator.IUser;
import pl.mn.communicator.packet.GGUtils;
import pl.mn.communicator.packet.in.GGStatus60;

/**
 * Created on 2004-12-12
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGStatus60PacketHandler.java,v 1.1 2004-12-14 19:29:56 winnetou25 Exp $
 */
public class GGStatus60PacketHandler implements PacketHandler {

	private final static Log logger = LogFactory.getLog(GGStatus60PacketHandler.class);
	
	/**
	 * @see pl.mn.communicator.packet.handlers.PacketHandler#handle(pl.mn.communicator.gadu.handlers.Context)
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
