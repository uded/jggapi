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
package pl.mn.communicator.gadu.in;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pl.mn.communicator.GGUser;
import pl.mn.communicator.GGUserMode;
import pl.mn.communicator.IStatus60;
import pl.mn.communicator.IUser;
import pl.mn.communicator.Status60;
import pl.mn.communicator.StatusType;
import pl.mn.communicator.gadu.GGStatusEnabled;
import pl.mn.communicator.gadu.GGUtils;

/**
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGStatus60.java,v 1.1 2004-12-12 16:21:54 winnetou25 Exp $
 */
public class GGStatus60 implements GGStatusEnabled {

	public static final int GG_STATUS60 = 0x0F;
	
	private static Log logger = LogFactory.getLog(GGStatus60.class);

	private IUser m_user = null;
	private IStatus60 m_status60 = null;
	
	public GGStatus60(byte[] data) {
		handleUser(data);
		handleStatus60(data);
	}
	
	public IUser getUser() {
		return m_user;
	}
	
	public IStatus60 getStatus60() {
		return m_status60;
	}
	
    private void handleUser(byte[] data) {
        //usun flage
        data[3] = GGUtils.intToByte(0)[0];
    	int uin = GGUtils.byteToInt(data);
//      byte protocolStatus = data[4];
    	//int protocolStatus = GGUtils.unsignedByteToInt(data[4]);
        //GGUserMode userMode = GGUtils.getUserMode(protocolStatus);
        m_user =  new GGUser(uin, GGUserMode.BUDDY);
    }
    
    private void handleStatus60(byte[] data) {
        byte protocolStatus = data[4];
    	byte[] remoteIP = GGUtils.byteToString(data, 5).getBytes();
    	int remotePort = GGUtils.byteToShort(data, 9);
    	byte version = data[10];
    	byte imageSize = data[11];
    	m_status60 = new Status60(StatusType.ONLINE);
    }
	
//	#define GG_STATUS60 0x000F
	
//struct gg_status60 {
//	int uin;	        /* numer plus flagi w najstarszym bajcie */ 4
//	char status;	    /* nowy stan */ 1
//	int remote_ip;		/* adres IP bezpo�rednich po��cze� */ 4
//	short remote_port;	/* port bezpo�rednich po��cze� */ 2
//	char version;		/* wersja klienta */ 1
//	char image_size;	/* maksymalny rozmiar grafiki */ 1
//	char unknown1;		/* 0x00 * 1
//	char description[];	/* opis, nie musi wyst�pi� */ n
//	int time;		/* czas, nie musi wyst�pi� */ 1
//};
	
}
