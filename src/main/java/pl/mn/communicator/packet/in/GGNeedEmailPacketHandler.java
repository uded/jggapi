/*
 * Copyright (c) 2003-2005 JGGApi Development Team. All Rights Reserved. This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version. This program is distributed in
 * the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a
 * copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package pl.mn.communicator.packet.in;

import pl.mn.communicator.GGException;
import pl.mn.communicator.event.LoginFailedEvent;
import pl.mn.communicator.packet.GGUtils;
import pl.mn.communicator.packet.handlers.GGWelcomePacketHandler;
import pl.mn.communicator.packet.handlers.PacketContext;
import pl.mn.communicator.packet.handlers.PacketHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on 2005-10-25
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGNeedEmailPacketHandler.java,v 1.1 2005/11/05 23:34:52 winnetou25 Exp $
 */
public class GGNeedEmailPacketHandler implements PacketHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(GGWelcomePacketHandler.class);

	public void handle(final PacketContext context) throws GGException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("GGNeedEmail packet received.");
			LOGGER.debug("PacketHeader: " + context.getHeader());
			LOGGER.debug("PacketLoad: " + GGUtils.prettyBytesToString(context.getPackageContent()));
		}

		final GGNeedEmail needEmail = GGNeedEmail.getInstance();
		context.getSessionAccessor().notifyGGPacketReceived(needEmail);
		final LoginFailedEvent loginFailedEvent = new LoginFailedEvent(this);
		loginFailedEvent.setReason(LoginFailedEvent.NEED_EMAIL_REASON);
		context.getSessionAccessor().notifyLoginFailed(loginFailedEvent);
	}

}
