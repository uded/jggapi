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

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pl.mn.communicator.IStatus;
import pl.mn.communicator.IUser;
import pl.mn.communicator.User;

/**
 * 
 * TO REFACTOR
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGStatus.java,v 1.19 2004-12-11 16:25:58 winnetou25 Exp $
 */
public class GGStatus implements GGIncomingPackage, GGStatusEnabled {

	private static Log logger = LogFactory.getLog(GGStatus.class);

	public static final int GG_STATUS = 0x02;

    private IUser m_user = null;
    private IStatus m_status = null;
    private byte[] m_data;

    public GGStatus(byte[] dane) {
        logger.debug("Odebralem pakiet zmiany statusu uzytkownika");
        m_data = dane;
        analize();
    }
    
	public int getPacketType() {
		return GG_STATUS;
	}

    /**
     * Analizuj pakiet przychodz�cy.
     */
    private void analize() {
        String description = null;
        Date returnTime = null;

        // usun flage
        m_data[3] = GGUtils.intToByte(0)[0];

        int userNo = GGUtils.byteToInt(m_data);
        logger.debug("Nr uzytkownika zmieniajacego status: " + userNo);
        m_user = new User(userNo);

        int status = GGUtils.unsignedByteToInt(m_data[4]);
        logger.debug("Status u�ytkownika to: " + status);

        if ((status == GGStatus.GG_STATUS_AVAIL_DESCR) ||
                (status == GGStatus.GG_STATUS_BUSY_DESCR) ||
                (status == GGStatus.GG_STATUS_INVISIBLE_DESCR) ||
                (status == GGStatus.GG_STATUS_NOT_AVAIL_DESCR)) {
            logger.debug("U�ytkownik ma status opisowy");

            description = GGUtils.byteToString(m_data, 14);
            logger.debug("Opis:" + description);

            if (m_data.length > (14 + description.length())) {
                logger.debug("U�ytkownik ma ustawiona date powrotu");
                long czas = GGUtils.byteToInt(m_data, m_data.length - 4);
                czas *= 1000;
                returnTime = new Date();
                returnTime.setTime(czas);
                logger.debug("Czas: " + czas + ":" + returnTime);
            }
        }

        m_status = GGUtils.getClientStatus(status, description, returnTime);
    }

    /**
    * Pobierz u�ytkonwika kt�ry zmieni� status.
    * @return u�ytkownik kt�ry zmieni� status
    */
    public IUser getUser() {
        return m_user;
    }

    /**
     * Pobierz nowy status u�ytkownika.
     * @return nowy status u�ytkownika
     */
    public IStatus getStatus() {
        return m_status;
    }
    
}
