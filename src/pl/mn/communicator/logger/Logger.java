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

import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;


/**
 * Klasa fabrykujaca logger.
 *
 * @author mnaglik
 */
public abstract class Logger implements ILogger {
    private static NullLogger nullLogger = new NullLogger();
    private static boolean log;

    static {
        Properties prop = new Properties();

        try {
            InputStream in = Logger.class.getResourceAsStream("log.properties");
            prop.load(in);
            in.close();
        } catch (IOException e) {
            System.err.println("Blad czytania parametrow logowania.");
        }

        log = Boolean.valueOf(prop.getProperty("log", "true")).booleanValue();
    }

    /**
     * Zwróc instancje loggera.
     * @param clazz klasa która loguje
     * @return instancja loggera
     */
    public static synchronized Logger getLogger(Class clazz) {
        if (!log) {
            return nullLogger;
        } else {
            return Logger4J.getLogger(clazz);
        }
    }
}
