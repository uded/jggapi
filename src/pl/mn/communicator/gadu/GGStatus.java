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
 * @version $Revision: 1.10 $
 * @author mnaglik
 */
class GGStatus implements GGIncomingPackage {
	private static Logger logger = Logger.getLogger(GGStatus.class);
    private IUser user;
    private IStatus status;
    
    public GGStatus(byte[] dane) {
    	logger.debug("Odebralem pakiet zmiany statusu uzytkownika");
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
        int uin;            // numer plus flagi w najstarszym bajcie //
        char status;            // nowy stan //
        int remote_ip;      // adres IP bezpo¶rednich po³±czeñ //
        short remote_port;  // port bezpo¶rednich po³±czeñ //
        char version;       // wersja klienta //
        char image_size;    // maksymalny rozmiar grafiki //
        char unknown1;      // 0x00 /
        char description[]; // opis, nie musi wyst±piæ //
        int time;       // czas, nie musi wyst±piæ //
    };
    */
}
