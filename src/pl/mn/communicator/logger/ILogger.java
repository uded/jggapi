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
 * Interfejs dla implementacji loggerów.
 * 
 * @author mnaglik
 */
public interface ILogger {
    /**
     * @see org.apache.log4j.Category#debug(java.lang.Object,
     *      java.lang.Throwable)
     */
    void debug(Object arg0, Throwable arg1);

    /**
     * @see org.apache.log4j.Category#debug(java.lang.Object)
     */
    void debug(Object arg0);

    /**
     * @see org.apache.log4j.Category#error(java.lang.Object,
     *      java.lang.Throwable)
     */
    void error(Object arg0, Throwable arg1);

    /**
     * @see org.apache.log4j.Category#error(java.lang.Object)
     */
    void error(Object arg0);

    /**
     * @see org.apache.log4j.Category#fatal(java.lang.Object,
     *      java.lang.Throwable)
     */
    void fatal(Object arg0, Throwable arg1);

    /**
     * @see org.apache.log4j.Category#fatal(java.lang.Object)
     */
    void fatal(Object arg0);

    /**
     * @see org.apache.log4j.Category#info(java.lang.Object,
     *      java.lang.Throwable)
     */
    void info(Object arg0, Throwable arg1);

    /**
     * @see org.apache.log4j.Category#info(java.lang.Object)
     */
    void info(Object arg0);

    /**
     * @see org.apache.log4j.Category#warn(java.lang.Object,
     *      java.lang.Throwable)
     */
    void warn(Object arg0, Throwable arg1);

    /**
     * @see org.apache.log4j.Category#warn(java.lang.Object)
     */
    void warn(Object arg0);
}
