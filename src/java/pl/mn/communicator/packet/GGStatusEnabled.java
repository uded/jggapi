/*
 * Copyright (c) 2003-2005 JGGApi Development Team. All Rights Reserved.
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
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGStatusEnabled.java,v 1.3 2004-12-19 17:14:43 winnetou25 Exp $
 */
public interface GGStatusEnabled {

    /** Status available */
    int GG_STATUS_AVAIL = 0x00000002;

    /** Status available with description */
    int GG_STATUS_AVAIL_DESCR = 0x00000004;

    /** Status not available */
    int GG_STATUS_NOT_AVAIL = 0x00000001;

    /** Status not available with description */
    int GG_STATUS_NOT_AVAIL_DESCR = 0x00000015;

    /** Status busy */
    int GG_STATUS_BUSY = 0x00000003;

    /** Status busy with description */
    int GG_STATUS_BUSY_DESCR = 0x00000005;

    /** Status invisible */
    int GG_STATUS_INVISIBLE = 0x00000014;

    /** Status invisible with description */
    int GG_STATUS_INVISIBLE_DESCR = 0x00000016;

    /** Bitmask for status blocked */
    int GG_STATUS_BLOCKED = 0x00000006;

    /** Bitmask for status for friends only */
    int GG_STATUS_FRIENDS_MASK = 0x00008000; //1|000|000|000|000|000 = 32768 = (2^16)/2

}
