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

import pl.mn.communicator.Status;
import pl.mn.communicator.User;
import pl.mn.communicator.logger.Logger;

/**
 * Pakiet z list± u¿ytkowników jako odpowied¼ na <code>GGNotify</code>.
 * W obecnej implementacji interesuj± nas tylko pola:
 * numer, status, opis i czas.
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGNotifyReply.java,v 1.19 2004-10-27 00:52:15 winnetou25 Exp $
 */
public class GGNotifyReply implements GGIncomingPackage {
    
	public static final int GG_NOTIFY_REPLY = 0x000C;

	private static Logger logger = Logger.getLogger(GGNotifyReply.class);

	private byte[] dane;
    private Map statusy = new HashMap();

    /**
     * Tworz pakiet odpowiedzi na listê u¿ytkonwików dostêpnych.
     * @param dane dane do utworzenia pakietu
     */
    public GGNotifyReply(byte[] dane) {
        logger.debug("Pakiet zmiany stanu u¿ytkownika");
        this.dane = dane;
        analize();
    }

    public int getPacketType() {
    	return -1;
    }
    
    /**
     * Zwróc mapê statusów u¿ytkowników.
     * Kluczem jest <code>User</code> a warto¶ci± <code>Status</code>.
     * @return statusy u¿ytkowników
     */
    public Map getUsersState() {
        return statusy;
    }

    /**
     * Analizuj dane pakietu
     */
    private void analize() {
        // usun flagi z najstarszego bajtu
        int przesuniecie = 0;

        while (przesuniecie < dane.length) {
            dane[przesuniecie + 3] = GGConversion.intToByte(0)[0];

            int nr = GGConversion.byteToInt(dane, przesuniecie);
            logger.debug("Nr usera zmieniajacego status: " + nr);

            int status = GGConversion.unsignedByteToInt(dane[przesuniecie + 4]);
            logger.debug("Status u¿ytkownika to: " + status);

            // dla statusow opisowych pobierz opis
            String description = null;
            Date returnTime = null;

            if ((status == GGNewStatus.GG_STATUS_AVAIL_DESCR) ||
                    (status == GGNewStatus.GG_STATUS_BUSY_DESCR) ||
                    (status == GGNewStatus.GG_STATUS_INVISIBLE_DESCR) ||
                    (status == GGNewStatus.GG_STATUS_NOT_AVAIL_DESCR)) {
                int descriptionSize = GGConversion.unsignedByteToInt(dane[przesuniecie +
                        14]);

                logger.debug("U¿ytkownik ma status opisowy o dlugosci " +
                    descriptionSize);

                boolean jestCzas = dane[(przesuniecie + 15 + descriptionSize) -
                    5] == 0;

                if (jestCzas) {
                    logger.debug("Ustawiony czas powrotu " +
                        dane[(przesuniecie + 15 + descriptionSize) - 4]);

                    long czas = GGConversion.byteToInt(dane,
                            (przesuniecie + 15 + descriptionSize) - 4);
                    czas *= 1000;
                    returnTime = new Date();
                    returnTime.setTime(czas);
                    descriptionSize -= 5;
                    logger.debug("Czas: " + czas + ":" + returnTime);
                }

                
                byte[] opis = new byte[descriptionSize];
                System.arraycopy(dane, przesuniecie + 15, opis, 0,
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

            Status statusUzytkownika = new Status(GGConversion.dajStatusBiz(status));
            statusUzytkownika.setDescription(description);
            statusUzytkownika.setReturnTime(returnTime);

            User uzytkownik = new User(nr);
            statusy.put(uzytkownik, statusUzytkownika);
        }
    }
}
