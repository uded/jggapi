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
package pl.mn.communicator;

/**
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: IMessage.java,v 1.1 2004-12-14 21:53:51 winnetou25 Exp $
 */
public interface IMessage {
	
    /**
     * Gadu-Gadu number of user to whom this message is addressed.
     * @return gg number
     */
    int getUin();

    /**
     * @param user number of Gadu-Gadu user.<BR>
     */
    void setUin(int uin);

    /**
     * Gets message body.
     * @return String message body
     */
    String getText();

    /**
     * @param text message body.
     */
    void setText(String text);

    MessageClass getMessageClass();
    
}
