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
package pl.mn.communicator.gadu.handlers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pl.mn.communicator.gadu.GGUtils;
import pl.mn.communicator.gadu.in.GGLoginOK;

/**
 * Created on 2004-11-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGLoginOKPacketHandler.java,v 1.7 2004-12-13 23:44:02 winnetou25 Exp $
 */
public class GGLoginOKPacketHandler implements PacketHandler {

	private static final Log logger = LogFactory.getLog(GGLoginOKPacketHandler.class);
	
	/**
	 * @see pl.mn.communicator.gadu.handlers.PacketHandler#handle(pl.mn.communicator.gadu.handlers.PacketContext)
	 */
	public void handle(Context context) {
		logger.debug("LoginOK packet received.");
		logger.debug("PacketHeader: "+context.getHeader());
		logger.debug("PacketLoad: "+GGUtils.bytesToString(context.getPackageContent()));

		GGLoginOK loginOk = GGLoginOK.getInstance();
		context.getSessionAccessor().notifyGGPacketReceived(loginOk);
		context.getSessionAccessor().notifyLoginOK();
	}

}
