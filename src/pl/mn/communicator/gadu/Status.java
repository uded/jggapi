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

import org.apache.log4j.Logger;

import pl.mn.communicator.AbstractStatus;

import java.util.Map;


/**
 * Status u¿ytkownika gg.
 * @version $Revision: 1.7 $
 * @author mnaglik
 */
public class Status extends AbstractStatus {
    private static Logger logger = Logger.getLogger(Status.class);

    /**
     * Status niewidoczny.
     */
    public static final int NOT_VISIBLE = 3;

    /**
     * Status zajety.
     */
    public static final int BUSY = 6;

    /**
     * @param status status u¿ytkownika
     */
    public Status(int status) {
        super(status);
    }

    /**
     * Pobierz dostêpne statusy.
     * @return Map mapa dostêpnych statusów
     * @see pl.mn.communicator.AbstractStatus#getAvaiableStatuses()
     */
    public Map getAvaiableStatuses() {
        Map map = super.getAvaiableStatuses();
        map.put(new Integer(Status.NOT_VISIBLE), "NOT VISIBLE");

        return map;
    }
}
