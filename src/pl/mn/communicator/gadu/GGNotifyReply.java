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
package pl.mn.communicator.gadu;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pl.mn.communicator.IStatus;
import pl.mn.communicator.User;

/**
 * Pakiet z list± u¿ytkowników jako odpowied¼ na <code>GGNotify</code>.
 * W obecnej implementacji interesuj± nas tylko pola:
 * numer, status, opis i czas.
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGNotifyReply.java,v 1.20 2004-12-11 16:25:58 winnetou25 Exp $
 */
public class GGNotifyReply implements GGIncomingPackage {

    private static Log logger = LogFactory.getLog(GGNotifyReply.class);

	public static final int GG_NOTIFY_REPLY = 0x000C;

	private byte[] m_data;
    private Map m_statuses = new HashMap();

    /**
     * Tworz pakiet odpowiedzi na listê u¿ytkonwików dostêpnych.
     * @param m_data dane do utworzenia pakietu
     */
    public GGNotifyReply(byte[] data) {
        logger.debug("Pakiet zmiany stanu u¿ytkownika");
        m_data = data;
        analize();
    }

    public int getPacketType() {
    	return GG_NOTIFY_REPLY;
    }
    
    /**
     * Zwróc mapê statusów u¿ytkowników.
     * Kluczem jest <code>User</code> a warto¶ci± <code>Status</code>.
     * @return statusy u¿ytkowników
     */
    public Map getUsersStatus() {
        return m_statuses;
    }

    /**
     * Analizuj m_data pakietu
     */
    private void analize() {
        // usun flagi z najstarszego bajtu
        int przesuniecie = 0;

        while (przesuniecie < m_data.length) {
        	m_data[przesuniecie + 3] = GGUtils.intToByte(0)[0];

            int nr = GGUtils.byteToInt(m_data, przesuniecie);
            logger.debug("Nr usera zmieniajacego status: " + nr);

            int status = GGUtils.unsignedByteToInt(m_data[przesuniecie + 4]);
            logger.debug("Status u¿ytkownika to: " + status);

            // dla statusow opisowych pobierz opis
            String description = null;
            Date returnTime = null;

            if ((status == GGNewStatus.GG_STATUS_AVAIL_DESCR) ||
                    (status == GGNewStatus.GG_STATUS_BUSY_DESCR) ||
                    (status == GGNewStatus.GG_STATUS_INVISIBLE_DESCR) ||
                    (status == GGNewStatus.GG_STATUS_NOT_AVAIL_DESCR)) {
                int descriptionSize = GGUtils.unsignedByteToInt(m_data[przesuniecie + 14]);

                logger.debug("U¿ytkownik ma status opisowy o dlugosci " +
                    descriptionSize);

                boolean jestCzas = m_data[(przesuniecie + 15 + descriptionSize) - 5] == 0;

                if (jestCzas) {
                    logger.debug("Ustawiony czas powrotu " +
                    		m_data[(przesuniecie + 15 + descriptionSize) - 4]);

                    long czas = GGUtils.byteToInt(m_data,
                            (przesuniecie + 15 + descriptionSize) - 4);
                    czas *= 1000;
                    returnTime = new Date();
                    returnTime.setTime(czas);
                    descriptionSize -= 5;
                    logger.debug("Czas: " + czas + ":" + returnTime);
                }

                
                byte[] opis = new byte[descriptionSize];
                System.arraycopy(m_data, przesuniecie + 15, opis, 0,
                    descriptionSize);
                description = new String(opis);
                
                logger.debug("Opis[" + description + "]");

                przesuniecie += (15 + descriptionSize);

                if (jestCzas) {
                    przesuniecie += 5;
                }
            } else {
                logger.debug("U¿ytkownik NIE ma statusu opisowego");
                przesuniecie += 14; // pakiet bez opisu ma dlugosc 14 bajtow
            }
            
            IStatus statusBiz = GGUtils.getClientStatus(status, description, returnTime);
            User uzytkownik = new User(nr);
            m_statuses.put(uzytkownik, statusBiz);
        }
    }

    //TODO
    private int getUserNumber() {
    	return -1;
    }
    
    //TODO
    private int getStatus() {
    	return -1;
    }
    
    //TODO
    private String getStatusDescription() {
    	return null;
    }
    
    //TODO
    private Date getReturnTime() {
    	return null;
    }
    
}
