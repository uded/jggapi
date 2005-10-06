///*
// * Copyright (c) 2003-2005 JGGApi Development Team. All Rights Reserved.
// *
// * This program is free software; you can redistribute it and/or modify it
// * under the terms of the GNU General Public License as published by the Free
// * Software Foundation; either version 2 of the License, or (at your option)
// * any later version.
// *
// * This program is distributed in the hope that it will be useful, but WITHOUT
// * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
// * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
// * more details.
// *
// * You should have received a copy of the GNU General Public License along
// * with this program; if not, write to the Free Software Foundation, Inc.,
// * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
// */
//package pl.mn.communicator;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.InvalidPropertiesFormatException;
//import java.util.Properties;
//
///**
// * Created on 2005-05-09
// * 
// * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
// * @version $Id: XMLPropertiesGGConfiguration.java,v 1.2 2005-10-06 12:53:42 winnetou25 Exp $
// */
//public class XMLPropertiesGGConfiguration extends AbstractGGPropertiesConfiguration {
//    
//    public XMLPropertiesGGConfiguration(final String fileName, final IGGConfiguration configuration) throws IOException, InvalidPropertiesFormatException {
//    	super(fileName, configuration);
//    }
//    
//    public XMLPropertiesGGConfiguration(final String fileName) throws IOException, InvalidPropertiesFormatException {
//    	super(fileName);
//    }
//    
//    protected Properties createProperties() throws InvalidPropertiesFormatException, IOException {
//        final ClassLoader cl = Thread.currentThread().getContextClassLoader();
//        final InputStream is = getResourceAsStream(cl, m_fileName);
//        
//        final Properties props = new Properties();
//        props.loadFromXML(is);
//        
//        return props;
//    }
//    
//    public static XMLPropertiesGGConfiguration createSimpleXMLPropertiesGGConfiguration() throws InvalidPropertiesFormatException, IOException {
//    	XMLPropertiesGGConfiguration configuration = new XMLPropertiesGGConfiguration("jggapi.xml");
//    	
//    	return configuration;
//    }
//    
//}
