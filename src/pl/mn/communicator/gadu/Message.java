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

import pl.mn.communicator.AbstractMessage;
import pl.mn.communicator.logger.Logger;

/**
 * Wiadomosc gg.
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @version $Id: Message.java,v 1.10 2004-10-26 23:56:40 winnetou25 Exp $
 */
public final class Message extends AbstractMessage {
    
	private static Logger logger = Logger.getLogger(Message.class);

    /**
     * Tworz wiadomo¶æ na podstawie adresata i tre¶ci.
     * @param toUser adresat wiadomo¶ci
     * @param text tre¶c wiadomo¶ci
     */
    public Message(int toUser, String text) {
        super(toUser, text);
    }
    
}
