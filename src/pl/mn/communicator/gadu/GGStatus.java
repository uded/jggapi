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

import org.apache.log4j.Logger;

import pl.mn.communicator.IStatus;
import pl.mn.communicator.IUser;

/**
 * Pakiet powiadomienia u¿ytkownika o zmianie statusu u¿ytkownika z listy.
 * @version $Revision: 1.11 $
 * @author mnaglik
 */
class GGStatus implements GGIncomingPackage {
	private static Logger logger = Logger.getLogger(GGStatus.class);
    private IUser user;
    private IStatus status;
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
		int userNo = GGConversion.byteToInt(dane);
        logger.debug("Nr uzytkownika zmieniajacego status: "+userNo);
        user = new User(userNo);
        
        int status = GGConversion.unsignedByteToInt(dane[4]);
        logger.debug("Status u¿ytkownika to: " + status);

        if ((status == GGNewStatus.GG_STATUS_AVAIL_DESCR) ||
                (status == GGNewStatus.GG_STATUS_BUSY_DESCR) ||
                (status == GGNewStatus.GG_STATUS_INVISIBLE_DESCR) ||
                (status == GGNewStatus.GG_STATUS_NOT_AVAIL_DESCR)) {
        	logger.debug("U¿ytkownik ma status opisowy");
        }
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
    	return status;
    }
    
    
    /*
    struct gg_status60 {
       4 int uin;            // numer plus flagi w najstarszym bajcie //
       1 char status;            // nowy stan //
       4 int remote_ip;      // adres IP bezpo¶rednich po³±czeñ //
       2 short remote_port;  // port bezpo¶rednich po³±czeñ //
       1 char version;       // wersja klienta //
       1 char image_size;    // maksymalny rozmiar grafiki //
       1 char unknown1;      // 0x00 /
       1+char description[]; // opis, nie musi wyst±piæ //
       4 int time;       // czas, nie musi wyst±piæ //
    };
    */
}
