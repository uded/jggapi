/*
 * Copyright (c) 2003-2005 JGGApi Development Team. All Rights Reserved. This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version. This program is distributed in
 * the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a
 * copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package pl.mn.communicator.packet;

/**
 * Constants specific for Gadu-Gadu messages.
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGMessageClass.java,v 1.1 2005/11/05 23:34:52 winnetou25 Exp $
 */
public interface GGMessageClass {

	int GG_CLASS_QUEUED = 0x0001;

	int GG_CLASS_MSG = 0x0004;

	int GG_CLASS_CHAT = 0x0008;

	int GG_CLASS_CTCP = 0x0010;

	int GG_CLASS_ACK = 0x0020;

	int GG_CLASS_QUEUED2 = 0x0009;

	int GG_CLASS_UNKNOWN = -0x0001;

}
