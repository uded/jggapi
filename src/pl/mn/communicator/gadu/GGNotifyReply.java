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

import java.util.HashMap;
import java.util.Map;


/**
 * Pakiet z list± u¿ytkowników jako odpowied¼ na <code>GGNotify</code>.
 * W obecnej implementacji interesuj± nas tylko pola:
 * numer, status, opis i czas
 * @version $Revision: 1.8 $
 * @author mnaglik
 */
class GGNotifyReply implements GGIncomingPackage {
    private static Logger logger = Logger.getLogger(GGNotifyReply.class);
    private byte[] dane;
    private Map statusy = new HashMap();
    
    /**
     * 
     * @param dane
     */
    GGNotifyReply(byte[] dane) {
        logger.debug("Pakiet zmiany stanu u¿ytkownika");
        this.dane = dane;
    }

    /**
     * Zwróc mapê statusów u¿ytkowników.
     * Kluczem jest <code>User</code> a warto¶ci± <code>Status</code>.
     * @return statusy u¿ytkowników
     */
    Map getUsersState() {
        return statusy;
    }

    /**
     * Analizuj dane pakietu
     */
    private void analize() {
        // sprawdz: 
        // - numer
        // - status
        // - description
        // - time
        
    }
    /*
    #define GG_NOTIFY_REPLY 0x000c   tak, to samo co GG_LOGIN

    struct gg_notify_reply {
      4  int uin;         numerek
      4  int status;     / status danej osoby
      4  int remote_ip;      / adres ip delikwenta
        short remote_port;  / port, na którym s³ucha klient
      4  int version;        / wersja klienta
        short unknown1;     / znowu port?
        char description[]; / opis, nie musi wyst±piæ
      4  int time;       / czas, nie musi wyst±piæ
    };

    */
}
