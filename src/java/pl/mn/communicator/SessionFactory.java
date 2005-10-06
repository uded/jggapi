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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created on 2004-12-12
 * 
 * This is the factory class that helps developers
 * to create a new instance of a session class.
 * <p>
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: SessionFactory.java,v 1.14 2005-10-06 12:53:03 winnetou25 Exp $
 */
public class SessionFactory {
	
    private final static Log LOGGER = LogFactory.getLog(SessionFactory.class);
    
    public static ISession createSession() {
        try {
            final IGGConfiguration configuration = SimplePropertiesGGConfiguration.createSimplePropertiesGGConfiguration();
            return new Session(configuration);
        } catch (Exception ex) {
            LOGGER.warn("Unable to load jggapi.properties!", ex);
            LOGGER.warn("Falling back to default properties.");
            return new Session();
        }
    }
    
}
