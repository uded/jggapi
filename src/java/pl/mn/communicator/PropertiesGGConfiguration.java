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
package pl.mn.communicator;

import java.io.IOException;
import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

/**
 * Created on 2005-05-09
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: PropertiesGGConfiguration.java,v 1.2 2005-05-11 19:28:23 winnetou25 Exp $
 */
public class PropertiesGGConfiguration implements IGGConfiguration {
    
    private IGGConfiguration m_defaultGGConfiguration = new GGConfiguration();
    
    private Properties m_prop = null;
    
    public PropertiesGGConfiguration(String fileName, IGGConfiguration configuration) throws IOException, InvalidPropertiesFormatException {
        this(fileName);
        m_defaultGGConfiguration = configuration;
    }
    
    public PropertiesGGConfiguration(String fileName) throws IOException, InvalidPropertiesFormatException {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        InputStream is = getResourceAsStream(cl, fileName);
        
        if (is == null) throw new IOException("Unable to find file: "+fileName);
        m_prop = new Properties();
        m_prop.loadFromXML(is);
    }
    
    /**
     * @see pl.mn.communicator.IGGConfiguration#getConnectionThreadSleepTimeInMiliseconds()
     */
    public int getConnectionThreadSleepTimeInMiliseconds() {
        String connectionThreadSleepTime = String.valueOf(m_defaultGGConfiguration.getConnectionThreadSleepTimeInMiliseconds());
        return new Integer(m_prop.getProperty("connection.thread.sleep.time", connectionThreadSleepTime)).intValue();
    }
    
    /**
     * @see pl.mn.communicator.IGGConfiguration#getPingIntervalInMiliseconds()
     */
    public int getPingIntervalInMiliseconds() {
        String pingInterval = String.valueOf(m_defaultGGConfiguration.getPingIntervalInMiliseconds());
        return new Integer(m_prop.getProperty("ping.interval", pingInterval)).intValue();
    }
    
    /**
     * @see pl.mn.communicator.IGGConfiguration#getSocketTimeoutInMiliseconds()
     */
    public int getSocketTimeoutInMiliseconds() {
        String defaultSocketTimeout = String.valueOf(m_defaultGGConfiguration.getSocketTimeoutInMiliseconds());
        return new Integer(m_prop.getProperty("socket.timeout", defaultSocketTimeout)).intValue();
    }
    
    /**
     * @see pl.mn.communicator.IGGConfiguration#getRegistrationURL()
     */
    public String getRegistrationURL() {
        String registrationURL = m_defaultGGConfiguration.getRegistrationURL();
        return m_prop.getProperty("server.registration.url", registrationURL);
    }
    
    /**
     * @see pl.mn.communicator.IGGConfiguration#getSendPasswordURL()
     */
    public String getSendPasswordURL() {
        String sendPasswordURL = m_defaultGGConfiguration.getSendPasswordURL();
        return m_prop.getProperty("send.password.url", sendPasswordURL);
    }
    
    /**s
     * @see pl.mn.communicator.IGGConfiguration#getServerLookupURL()
     */
    public String getServerLookupURL() {
        String serverLookupURL = m_defaultGGConfiguration.getServerLookupURL();
        return m_prop.getProperty("server.lookup.url", serverLookupURL);
    }
    
    /**
     * @see pl.mn.communicator.IGGConfiguration#getTokenRequestURL()
     */
    public String getTokenRequestURL() {
        String tokenRequestURL = m_defaultGGConfiguration.getServerLookupURL();
        return m_prop.getProperty("token.request.url", tokenRequestURL);
    }
    
    public static PropertiesGGConfiguration createDefaultPropertiesGGConfiguration() throws IOException, InvalidPropertiesFormatException {
        return new PropertiesGGConfiguration("jggapi.properties");
    }
    
    private static InputStream getResourceAsStream(final ClassLoader loader, final String name) {
        return (InputStream)AccessController.doPrivileged(
                new PrivilegedAction() {
                    public Object run() {
                        if (loader != null) {
                            return loader.getResourceAsStream(name);
                        } else {
                            return ClassLoader.getSystemResourceAsStream(name);
                        }
                    }
                });
    }
    
}
