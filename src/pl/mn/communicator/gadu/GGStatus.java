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

import pl.mn.communicator.IStatus;
import pl.mn.communicator.IUser;
import pl.mn.communicator.logger.Logger;

/**
 * Pakiet powiadomienia u¿ytkownika o zmianie statusu u¿ytkownika z listy.
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @version $Id: GGStatus.java,v 1.16 2004-10-26 23:56:40 winnetou25 Exp $
 */
class GGStatus implements GGIncomingPackage {
	
    private static Logger logger = Logger.getLogger(GGStatus.class);
    private IUser user;
    private Status statusBiz;
    private byte[] dane;

    public GGStatus(byte[] dane) {
        logger.debug("Odebralem pakiet zmiany statusu uzytkownika");
        this.dane = dane;
        analize();
    }

    /**
     * Analizuj pakiet przychodz±cy.
     */
    private void analize() {
        Date returnTime = null;
        String description = null;

        // usun flage
        dane[3] = GGConversion.intToByte(0)[0];

        int userNo = GGConversion.byteToInt(dane);
        logger.debug("Nr uzytkownika zmieniajacego status: " + userNo);
        user = new User(userNo);

        int status = GGConversion.unsignedByteToInt(dane[4]);
        logger.debug("Status u¿ytkownika to: " + status);

        if ((status == GGNewStatus.GG_STATUS_AVAIL_DESCR) ||
                (status == GGNewStatus.GG_STATUS_BUSY_DESCR) ||
                (status == GGNewStatus.GG_STATUS_INVISIBLE_DESCR) ||
                (status == GGNewStatus.GG_STATUS_NOT_AVAIL_DESCR)) {
            logger.debug("U¿ytkownik ma status opisowy");

            description = GGConversion.byteToString(dane, 14);
            logger.debug("Opis:" + description);

            if (dane.length > (14 + description.length())) {
                logger.debug("U¿ytkownik ma ustawiona date powrotu");

                long czas = GGConversion.byteToInt(dane, dane.length - 4);
                czas *= 1000;
                returnTime = new Date();
                returnTime.setTime(czas);
                logger.debug("Czas: " + czas + ":" + returnTime);
            }
        }

        statusBiz = new Status(GGConversion.dajStatusBiz(status));
        statusBiz.setDescription(description);
        statusBiz.setReturnTime(returnTime);
    }

    /**
    * Pobierz u¿ytkonwika który zmieni³ status.
    * @return u¿ytkownik który zmieni³ status
    */
    public IUser getUser() {
        return user;
    }

    /**
     * Pobierz nowy status u¿ytkownika.
     * @return nowy status u¿ytkownika
     */
    public IStatus getStatus() {
        return statusBiz;
    }
}
