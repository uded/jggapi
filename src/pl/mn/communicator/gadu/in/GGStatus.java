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
package pl.mn.communicator.gadu.in;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pl.mn.communicator.GGUser;
import pl.mn.communicator.GGUserMode;
import pl.mn.communicator.IStatus;
import pl.mn.communicator.IUser;
import pl.mn.communicator.gadu.GGIncomingPackage;
import pl.mn.communicator.gadu.GGStatusEnabled;
import pl.mn.communicator.gadu.GGUtils;

/**
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGStatus.java,v 1.1 2004-12-12 16:21:54 winnetou25 Exp $
 */
public class GGStatus implements GGIncomingPackage, GGStatusEnabled {

	public static final int GG_STATUS = 0x02;

	private static Log logger = LogFactory.getLog(GGStatus.class);

    private IUser m_user = null;
    private IStatus m_status = null;

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

    public IStatus getStatus() {
        return m_status;
    }
    
    private void handleUser(byte[] data) {
        //usun flage
        //m_data[3] = GGUtils.intToByte(0)[0];
    	int uin = GGUtils.byteToInt(data);
        int protocolStatus = GGUtils.unsignedByteToInt(data[4]);
        GGUserMode userMode = GGUtils.getUserMode(protocolStatus);
        m_user =  new GGUser(uin, userMode);
    }
    
    private void handleStatus(byte[] data) {
    	int protocolStatus = GGUtils.unsignedByteToInt(data[4]);
        String description = null;
        int timeInSeconds = -1;
        long timeInMillis = -1;
        if ((protocolStatus == GGStatus.GG_STATUS_AVAIL_DESCR) 
        	|| (protocolStatus == GGStatus.GG_STATUS_BUSY_DESCR)
			|| (protocolStatus == GGStatus.GG_STATUS_INVISIBLE_DESCR)
			|| (protocolStatus == GGStatus.GG_STATUS_NOT_AVAIL_DESCR)) {
            description = GGUtils.byteToString(data, 14);
            if (data.length > (14 + description.length())) {
                timeInSeconds = GGUtils.byteToInt(data, data.length - 4);
            }
        }
        if (timeInSeconds != -1) {
        	timeInMillis = GGUtils.secondsToMillis(timeInSeconds);
        }
        m_status = GGUtils.getClientStatus(protocolStatus, description, timeInMillis);
    }
    
}
