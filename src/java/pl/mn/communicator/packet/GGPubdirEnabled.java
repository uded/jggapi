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
 * Created on 2004-12-15
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGPubdirEnabled.java,v 1.5 2004-12-19 21:14:12 winnetou25 Exp $
 */
public interface GGPubdirEnabled {

	int GG_PUBDIR50_WRITE = 0x01;
	int GG_PUBDIR50_READ = 0x02;
	int GG_PUBDIR50_SEARCH = 0x03;
	int GG_PUBDIR50_SEARCH_REPLY = 0x05;
	
	String UIN = "FmNumber";
	String FIRST_NAME = "firstname";
	String LAST_NAME = "lastname";
	String NICK_NAME = "nickname";
	String BIRTH_YEAR = "birthyear";
	String CITY = "city";
	String GENDER = "gender";
	String ACTIVE = "ActiveOnly";
	String FAMILY_NAME = "familyname";
	String FAMILY_CITY = "familycity";
	String START = "fmstart";
	String STATUS = "FmStatus";
	String NEXT_START = "nextstart";

}
