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
 * Created on 2004-12-14
 *
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGPubDir.java,v 1.1 2004-12-15 22:01:50 winnetou25 Exp $
 */
public class GGPubDir {

	private String m_type;
	
	private GGPubDir(String type) {
		m_type = type;
	}
	
	public final static GGPubDir UIN = new GGPubDir("FmNumber");
	public final static GGPubDir FIRST_NAME = new GGPubDir("firstname");
	public final static GGPubDir LAST_NAME = new GGPubDir("lastname");
	public final static GGPubDir BIRTH_YEAR = new GGPubDir("birthyear");
	public final static GGPubDir CITY = new GGPubDir("city");
	public final static GGPubDir GENDER = new GGPubDir("gender");
	public final static GGPubDir ACTIVE = new GGPubDir("ActiveOnly");
	public final static GGPubDir FAMILY_NAME = new GGPubDir("familyname");
	public final static GGPubDir FAMILYCITY = new GGPubDir("familycity");
	public final static GGPubDir START = new GGPubDir("fmstart");
	
}