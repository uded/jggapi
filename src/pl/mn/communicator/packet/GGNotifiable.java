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
package pl.mn.communicator.packet;

/**
 * Created on 2004-12-12
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGNotifiable.java,v 1.1 2004-12-14 19:29:57 winnetou25 Exp $
 */
public interface GGNotifiable {

	/** Every user that is added to contact list */
    public static final int GG_USER_BUDDY = 0x01;

    /** User to whom we are seen in friends only mode */
    public static final int GG_USER_FRIEND = 0x02;

//  /** Ordinary user that is added to contact list */
//  public static final int GG_USER_NORMAL = 0x03;

    /** The user from whom we do not want to receive messages */
    public static final int GG_USER_BLOCKED = 0x04;

}
