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

import pl.mn.communicator.IStatus60;
import pl.mn.communicator.IUser;
import pl.mn.communicator.gadu.in.GGStatus;

/**
 * Created on 2004-11-28
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGStatusPacketHandler.java,v 1.5 2004-12-13 23:44:02 winnetou25 Exp $
 */
public class GGStatusPacketHandler implements PacketHandler {

	/**
	 * @see pl.mn.communicator.gadu.handlers.PacketHandler#handle(pl.mn.communicator.gadu.handlers.Context)
	 */
	public void handle(Context context) {
		GGStatus status = new GGStatus(context.getPackageContent());
		context.getSessionAccessor().notifyGGPacketReceived(status);
		IUser user = status.getUser();
		IStatus60 statusBiz = status.getStatus();
		context.getSessionAccessor().notifyUserChangedStatus(user, statusBiz);
	}

}
