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

/**
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGStatusEnabled.java,v 1.1 2004-12-11 16:25:58 winnetou25 Exp $
 */
public interface GGStatusEnabled {

    /** Status dostepny */
    int GG_STATUS_AVAIL = 0x00000002;

    /** Status dostepny z opisem */
    int GG_STATUS_AVAIL_DESCR = 0x00000004;

    /** Status niedostepny */
    int GG_STATUS_NOT_AVAIL = 0x00000001;

    /** Status niedostepny z opisem */
    int GG_STATUS_NOT_AVAIL_DESCR = 0x00000015;

    /** Status zajety */
    int GG_STATUS_BUSY = 0x00000003;

    /** Status zajety z opisem */
    int GG_STATUS_BUSY_DESCR = 0x00000005;

    /** Status niewidoczny */
    int GG_STATUS_INVISIBLE = 0x00000014;

    /** Status niewidoczny z opisem */
    int GG_STATUS_INVISIBLE_DESCR = 0x00000016;

    /** Status zablokowany */
    int GG_STATUS_BLOCKED = 0x00000006;

    /** Maska bitowa oznaczajaca tryb tylko dla przyjaciol */
    int GG_STATUS_FRIENDS_MASK = 0x00008000; //1|000|000|000|000|000 = 32768 = (2^16)/2

}
