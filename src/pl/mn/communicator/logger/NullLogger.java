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
package pl.mn.communicator.logger;

/**
 * Pusty logger.
 * Dane przekazane do loggera nie s± nigdzie logowane.
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @version $Id: NullLogger.java,v 1.3 2004-10-26 23:56:40 winnetou25 Exp $
 */
public class NullLogger extends Logger {
    /**
     * @see pl.mn.communicator.logger.ILogger#debug(java.lang.Object,
     *      java.lang.Throwable)
     */
    public void debug(Object arg0, Throwable arg1) {
    }

    /**
     * @see pl.mn.communicator.logger.ILogger#debug(java.lang.Object)
     */
    public void debug(Object arg0) {
    }

    /**
     * @see pl.mn.communicator.logger.ILogger#error(java.lang.Object,
     *      java.lang.Throwable)
     */
    public void error(Object arg0, Throwable arg1) {
    }

    /**
     * @see pl.mn.communicator.logger.ILogger#error(java.lang.Object)
     */
    public void error(Object arg0) {
    }

    /**
     * @see pl.mn.communicator.logger.ILogger#fatal(java.lang.Object,
     *      java.lang.Throwable)
     */
    public void fatal(Object arg0, Throwable arg1) {
    }

    /**
     * @see pl.mn.communicator.logger.ILogger#fatal(java.lang.Object)
     */
    public void fatal(Object arg0) {
    }

    /**
     * @see pl.mn.communicator.logger.ILogger#info(java.lang.Object,
     *      java.lang.Throwable)
     */
    public void info(Object arg0, Throwable arg1) {
    }

    /**
     * @see pl.mn.communicator.logger.ILogger#info(java.lang.Object)
     */
    public void info(Object arg0) {
    }

    /**
     * @see pl.mn.communicator.logger.ILogger#warn(java.lang.Object,
     *      java.lang.Throwable)
     */
    public void warn(Object arg0, Throwable arg1) {
    }

    /**
     * @see pl.mn.communicator.logger.ILogger#warn(java.lang.Object)
     */
    public void warn(Object arg0) {
    }
}
