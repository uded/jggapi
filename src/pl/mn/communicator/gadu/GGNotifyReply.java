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

import java.util.Map;
import org.apache.log4j.Logger;


/**
 * Pakiet z list± u¿ytkowników jako odpowied¼ na <code>GGNotify</code>.
 * W obecnej implementacji interesuj± nas tylko pola:
 * numer, status, opis i czas
 * @version $Revision: 1.7 $
 * @author mnaglik
 */
class GGNotifyReply implements GGIncomingPackage {
	private static Logger logger = Logger.getLogger(GGNotifyReply.class);
	
	GGNotifyReply(byte[] dane) {
    	logger.debug("Pakiet zmiany stanu u¿ytkownika");
	}
	
	/**
	 * Zwróc mapê statusów u¿ytkowników.
	 * Kluczem jest <code>User</code> a warto¶ci± <code>UserState</code>.
	 * @return
	 */
	Map getUsersState() {
		return null;
	}
	/*
        #define GG_NOTIFY_REPLY 0x000c   tak, to samo co GG_LOGIN

        struct gg_notify_reply {
            int uin;         numerek
            int status;     / status danej osoby
            int remote_ip;      / adres ip delikwenta
            short remote_port;  / port, na którym s³ucha klient
            int version;        / wersja klienta
            short unknown1;     / znowu port?
            char description[]; / opis, nie musi wyst±piæ
            int time;       / czas, nie musi wyst±piæ
        };

     */

}
