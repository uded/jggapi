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
package pl.mn.communicator.packet.in;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pl.mn.communicator.IRemoteStatus;
import pl.mn.communicator.IUser;
import pl.mn.communicator.RemoteStatus;
import pl.mn.communicator.User;
import pl.mn.communicator.packet.GGConversion;
import pl.mn.communicator.packet.GGStatuses;
import pl.mn.communicator.packet.GGUtils;

/**
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGStatus.java,v 1.1 2005-11-05 23:34:52 winnetou25 Exp $
 */
public class GGStatus implements GGIncomingPackage, GGStatuses {

	public static final int GG_STATUS = 0x02;

	private static final Log LOGGER = LogFactory.getLog(GGStatus.class);

    private IUser m_user = null;
    private RemoteStatus m_status = null;

    public GGStatus(byte[] data) {
    	handleUser(data);
    	handleStatus(data);
    }
    
	public int getPacketType() {
		return GG_STATUS;
	}

    public IUser getUser() {
        return m_user;
    }

    public IRemoteStatus getStatus() {
        return m_status;
    }
    
    private void handleUser(byte[] data) {
    	int uin = GGUtils.byteToInt(data);
        int protocolStatus = GGUtils.unsignedByteToInt(data[4]);
        User.UserMode userMode = GGConversion.getUserMode(protocolStatus);
        m_user =  new User(uin, userMode);
    }
    
    private void handleStatus(byte[] data) {
    	int protocolStatus = GGUtils.unsignedByteToInt(data[4]);
        String description = null;
        long timeInMillis = -1;
        if ((protocolStatus == GGStatus.GG_STATUS_AVAIL_DESCR) 
        	|| (protocolStatus == GGStatus.GG_STATUS_BUSY_DESCR)
			|| (protocolStatus == GGStatus.GG_STATUS_INVISIBLE_DESCR)
			|| (protocolStatus == GGStatus.GG_STATUS_NOT_AVAIL_DESCR)) {
            description = GGUtils.byteToString(data, 8);
            if (data.length > (8 + description.length())) {
                int timeInSeconds = GGUtils.byteToInt(data, data.length-4);
                timeInMillis = GGUtils.secondsToMillis(timeInSeconds);
            }
        }
        m_status = GGConversion.getClientRemoteStatus(protocolStatus, description, timeInMillis);
    }
    
}
