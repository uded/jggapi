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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pl.mn.communicator.GGUser;
import pl.mn.communicator.GGUserMode;
import pl.mn.communicator.IUser;
import pl.mn.communicator.Status60;
import pl.mn.communicator.gadu.GGIncomingPackage;
import pl.mn.communicator.gadu.GGUtils;
import pl.mn.communicator.gadu.out.GGNewStatus;

/**
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGNotifyReply60.java,v 1.2 2004-12-12 18:02:55 winnetou25 Exp $
 */
public class GGNotifyReply60 implements GGIncomingPackage {

	public static final int GG_NOTIFY_REPLY60 = 0x0011;

    private static Log logger = LogFactory.getLog(GGNotifyReply60.class);

    private Map m_statuses = new HashMap();

//  struct gg_notify_reply60 {
//	int uin;		/* numerek plus flagi w najstarszym bajcie */
//	char status;		/* status danej osoby */
//	int remote_ip;		/* adres IP bezpośrednich połączeń */
//	short remote_port;	/* port bezpośrednich połączeń */
//	char version;		/* wersja klienta */
//	char image_size;	/* maksymalny rozmiar obrazków w KB */
//	char unknown1;		/* 0x00 */
//	char description_size;	/* rozmiar opisu i czasu, nie musi wystąpić */
//	char description[];	/* opis, nie musi wystąpić */
//	int time;		/* czas, nie musi wystąpić */
//};
    
    public GGNotifyReply60(byte[] data) {
    	handlePacket(data);
    	//analize(data);
    }

    public int getPacketType() {
    	return GG_NOTIFY_REPLY60;
    }
    
    /**
     * Zwr�c map� status�w u�ytkownik�w.
     * Kluczem jest <code>User</code> a warto�ci� <code>Status</code>.
     * @return statusy u�ytkownik�w
     */
    public Map getUsersStatus() {
        return m_statuses;
    }
    
    private void handlePacket(byte[] data) {
        int offset = 0;
        while (offset < data.length) {
        	int uin = GGUtils.byteToInt(data, offset);
        	int status = GGUtils.unsignedByteToInt(data[offset+4]);
        	GGUserMode userMode = GGUtils.getUserMode(status);

        	String description = null;
        	int descriptionSize = -1;
        	int timeInSeconds = -1;
            long timeInMillis = -1;

            if ((status == GGNewStatus.GG_STATUS_AVAIL_DESCR)
            	|| (status == GGNewStatus.GG_STATUS_BUSY_DESCR)
				|| (status == GGNewStatus.GG_STATUS_INVISIBLE_DESCR)
				|| (status == GGNewStatus.GG_STATUS_NOT_AVAIL_DESCR)) {
                descriptionSize = GGUtils.unsignedByteToInt(data[offset + 14]);

                byte[] descBytes = new byte[descriptionSize];
                System.arraycopy(data, offset+15, descBytes, 0, descriptionSize);
                description = new String(descBytes);
                
                boolean isTimeSet = data[(offset+15+descriptionSize)-5]==0;

                if (isTimeSet) {
                    timeInSeconds = GGUtils.byteToInt(data, (offset+15+descriptionSize)-4);
                    timeInMillis = GGUtils.secondsToMillis(timeInSeconds);
                    descriptionSize -= 5;
                }

                offset+=(15+descriptionSize);

                if (isTimeSet) {
                    offset += 5;
                }
            }
        	IUser user = new GGUser(uin, userMode);
            Status60 status60 = GGUtils.getClientStatus(status, description, timeInMillis);

            if (descriptionSize != -1) {
            	status60.setDescriptionSize(descriptionSize);
            }
            
        	if (data[offset+3] == 0x40) {
        		status60.setSupportsVoiceCommunication(true);
        	}
        	
        	m_statuses.put(user, status60);
        }
    }

//    /**
//     * Analizuj m_data pakietu
//     */
//    private void analize(byte[] data) {
//        // usun flagi z najstarszego bajtu
//        int przesuniecie = 0;
//
//        while (przesuniecie < data.length) {
//        	data[przesuniecie + 3] = GGUtils.intToByte(0)[0];
//
//            int nr = GGUtils.byteToInt(data, przesuniecie);
//            logger.debug("Nr usera zmieniajacego status: " + nr);
//
//            int status = GGUtils.unsignedByteToInt(data[przesuniecie + 4]);
//            logger.debug("Status u�ytkownika to: " + status);
//
//            // dla statusow opisowych pobierz opis
//            String description = null;
//            Date returnTime = null;
//
//            if ((status == GGNewStatus.GG_STATUS_AVAIL_DESCR) ||
//                    (status == GGNewStatus.GG_STATUS_BUSY_DESCR) ||
//                    (status == GGNewStatus.GG_STATUS_INVISIBLE_DESCR) ||
//                    (status == GGNewStatus.GG_STATUS_NOT_AVAIL_DESCR)) {
//                int descriptionSize = GGUtils.unsignedByteToInt(data[przesuniecie + 14]);
//
//                
//                logger.debug("U�ytkownik ma status opisowy o dlugosci: " +descriptionSize);
//
//                boolean jestCzas = data[(przesuniecie + 15 + descriptionSize) - 5] == 0;
//
//                if (jestCzas) {
//                    logger.debug("Ustawiony czas powrotu: " +data[(przesuniecie + 15 + descriptionSize) - 4]);
//
//                    long czas = GGUtils.byteToInt(data, (przesuniecie + 15 + descriptionSize) - 4);
//                    czas *= 1000;
//                    returnTime = new Date();
//                    returnTime.setTime(czas);
//                    descriptionSize -= 5;
//                    logger.debug("Czas: " + czas + ":" + returnTime);
//                }
//
//                
//                byte[] opis = new byte[descriptionSize];
//                System.arraycopy(data, przesuniecie + 15, opis, 0,
//                    descriptionSize);
//                description = new String(opis);
//                
//                logger.debug("Opis[" + description + "]");
//
//                przesuniecie += (15 + descriptionSize);
//
//                if (jestCzas) {
//                    przesuniecie += 5;
//                }
//            } else {
//                logger.debug("U�ytkownik NIE ma statusu opisowego");
//                przesuniecie += 14; // pakiet bez opisu ma dlugosc 14 bajtow
//            }
//            
//            IStatus statusBiz = GGUtils.getClientStatus(status, description, returnTime.getTime());
//            GGUser uzytkownik = new GGUser(nr, GGUserMode.BUDDY);
//            m_statuses.put(uzytkownik, statusBiz);
//        }
//    }


//    #define GG_NOTIFY_REPLY60 0x0011
	
}
