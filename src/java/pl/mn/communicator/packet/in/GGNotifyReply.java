/*
 * Copyright (c) 2003-2005 <a href="http://jggapi.sourceforge.net/team-list.html">JGGApi Development Team</a> All Rights Reserved.
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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pl.mn.communicator.Status;
import pl.mn.communicator.User;
import pl.mn.communicator.packet.GGUtils;
import pl.mn.communicator.packet.out.GGNewStatus;

/**
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGNotifyReply.java,v 1.4 2004-12-19 18:50:52 winnetou25 Exp $
 */
public class GGNotifyReply implements GGIncomingPackage {

	public static final int GG_NOTIFY_REPLY = 0x000C;

    private static Log logger = LogFactory.getLog(GGNotifyReply.class);

//    struct gg_notify_reply {
//    	int uin;			/* numer */
//    	char status;		/* status danej osoby */
//    	int remote_ip;		/* adres ip delikwenta */
//    	short remote_port;	/* port, na którym słucha klient */
//    	int version;		/* wersja klienta */
//    	short unknown1;		/* znowu port? */
//    	char description[];	/* opis, nie musi wystąpić */
//    	int time;		/* czas, nie musi wystąpić */
//    };
    
    private Map m_statuses = new HashMap();

    /**
     * @param data dane do utworzenia pakietu
     */
    public GGNotifyReply(byte[] data) {
        analize(data);
    }

    public int getPacketType() {
    	return GG_NOTIFY_REPLY;
    }
    
    public Map getUsersStatus() {
        return m_statuses;
    }

    private void analize(byte[] data) {
        int offset = 0;

        while (offset < data.length) {
        	data[offset+3] = GGUtils.intToByte(0)[0];
            int uin = GGUtils.byteToInt(data, offset);
            int status = GGUtils.unsignedByteToInt(data[offset+4]);
            int remoteIP = GGUtils.byteToInt(data, offset+5);
            byte[] remoteIPByte = GGUtils.intToByte(remoteIP);
            short remotePort = GGUtils.byteToShort(data, offset+9);
            int version = GGUtils.byteToInt(data, offset+11);

            String description = null;
            long timeInMillis = -1;
            if ((status == GGNewStatus.GG_STATUS_AVAIL_DESCR) ||
                    (status == GGNewStatus.GG_STATUS_BUSY_DESCR) ||
                    (status == GGNewStatus.GG_STATUS_INVISIBLE_DESCR) ||
                    (status == GGNewStatus.GG_STATUS_NOT_AVAIL_DESCR)) {
                int descriptionSize = GGUtils.unsignedByteToInt(data[offset+14]);

                boolean isTime = data[(offset+15+descriptionSize) - 5] == 0;

                if (isTime) {
                	int timeInSeconds = GGUtils.byteToInt(data, (offset + 15 + descriptionSize) - 4);
                	timeInMillis = GGUtils.secondsToMillis(timeInSeconds);
                	descriptionSize -= 5;
                }

                byte[] descriptionBytes = new byte[descriptionSize];
                System.arraycopy(data, offset+15, descriptionBytes, 0, descriptionSize);
                description = new String(descriptionBytes);
                
                offset += (15 + descriptionSize);

                if (isTime) {
                    offset += 5;
                }
            } else {
                offset += 14; // the packet without description is 14 bytes long.
            }
            
            Status statusBiz = GGUtils.getClientStatus(status, description, timeInMillis);
            
            statusBiz.setRemoteIP(remoteIPByte);
            statusBiz.setRemotePort(remotePort);
            statusBiz.setGGVersion((byte)version);
            
            User.UserMode userMode = GGUtils.getUserMode(status);
            User user = new User(uin, userMode);
            m_statuses.put(user, statusBiz);
        }
    }

}
