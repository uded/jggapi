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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Pakiet z list± u¿ytkowników jako odpowied¼ na <code>GGNotify</code>.
 * W obecnej implementacji interesuj± nas tylko pola:
 * numer, status, opis i czas
 * @version $Revision: 1.10 $
 * @author mnaglik
 */
class GGNotifyReply implements GGIncomingPackage {
    private static Logger logger = Logger.getLogger(GGNotifyReply.class);
    private byte[] dane;
    private Map statusy = new HashMap();

    /**
     * Tworz pakiet odpowiedzi na listê u¿ytkonwików dostêpnych.
     * @param dane dane do utworzenia pakietu
     */
    GGNotifyReply(byte[] dane) {
        logger.debug("Pakiet zmiany stanu u¿ytkownika");
        this.dane = dane;
        analize();
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
        // usun flagi z najstarszego bajtu
        int przesuniecie = 0;

        while (przesuniecie < dane.length) {
            dane[przesuniecie + 3] = GGConversion.intToByte(0)[0];
            int nr = GGConversion.byteToInt(dane,przesuniecie);
            logger.debug("Nr usera zmieniajacego status: "+nr);
    
            int status = GGConversion.unsignedByteToInt(dane[przesuniecie + 4]);
            logger.debug("Status u¿ytkownika to: "+status);
            // 2 to dostepny
            // 3 to zaraz wracam
            // dla statusow opisowych pobierz opis
            if (status == GGStatus.GG_STATUS_AVAIL_DESCR
    				|| status == GGStatus.GG_STATUS_BUSY_DESCR
    				|| status == GGStatus.GG_STATUS_INVISIBLE_DESCR
    				|| status == GGStatus.GG_STATUS_NOT_AVAIL_DESCR) {
            	logger.debug("U¿ytkownik ma status opisowy");
            	
                
                // TODO inkrementuj przesuniecie
                String description = null;
                Date returnTime = null;
            }else{
            	logger.debug("U¿ytkownik NIE ma statusu opisowego");
            	przesuniecie += 14; // pakiet bez opisu ma dlugosc 14 bajtow
            }
        }
    }
    /*
struct gg_notify_reply60 {
  4   int uin;        / numerek plus flagi w najstarszym bajcie /
  1   char status;        / status danej osoby /
  4   int remote_ip;      / adres IP bezpo¶rednich po³±czeñ /
  2   short remote_port;  / port bezpo¶rednich po³±czeñ /
  1   char version;       / wersja klienta /
  1   char image_size;    / maksymalny rozmiar obrazków w KB /
  1   char unknown1;      / 0x00 /
  1   char description_size;  / rozmiar opisu i czasu, nie musi wyst±piæ /
  1+  char description[]; / opis, nie musi wyst±piæ /
  4   int time;       / czas, nie musi wyst±piæ /
};

GG_UINFLAG_UNKNOWN1 0x10 Nieznane 
GG_UINFLAG_UNKNOWN2 0x20 Flaga spotykana, gdy u¿ytkownik staje siê niedostêpny 
GG_UINFLAG_VOICE 0x40 U¿ytkownik mo¿e prowadziæ rozmowy g³osowe 

    */
}
