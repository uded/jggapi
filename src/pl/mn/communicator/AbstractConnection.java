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

import org.apache.log4j.Logger;

/**
 * Po³¹czenie z serwerem gg.<BR>
 * S³u¿y do tworzenia po³¹czenia.
 * <BR><BR>
 * <i>Przyk³ad u¿ycia:</i><BR><BR>
 * <code>
 * AbstractLocalUser user = new XXXLocalUser(1234,"password");<BR>
 * AbstractServer server = XXXServerAddress.getHost(user);<BR>
 * AbstractServer s = new XXXServer(server.user);<BR>
 * AcstractConnection c = new XXXConnection();<BR><BR>
 * try{<BR>
 * &nbsp; &nbsp; c.connect();<BR>
 * }catch(Exception e){<BR>
 * &nbsp; &nbsp; ...<BR>
 * }
 * </code>
 * 
 * @version $Revision: 1.5 $
 * @author mnaglik
 */
public abstract class AbstractConnection implements IConnection {
	private static Logger logger = Logger.getLogger(AbstractConnection.class);
	/**
	 * Listener u¿ytkowników
	 */
	protected UserListener userListener = null;

	/**
	 * Listener po³¹czenia
	 */
	protected ConnectionListener connectionListener = null;

	/**
	 * Listener wiadomoœci
	 */
	protected MessageListener messageListener = null;

	/**
	 * Dodaj listenera u¿ytkowników.<BR>
	 * Obs³uguje odpowiednie zdarzenia zwi¹zane z u¿ytkownikami
	 * takie jak pryjœcie i odejœcie u¿ytkownika
	 * 
	 * @see UserListener
	 * @param userListener obiekt listenera
	 */
	public void addUserListener(UserListener userListener) {
		this.userListener = userListener;
	}
	/**
	 * Usuwa listenera u¿ytkowników.<BR>
	 * Je¿eli nie ma aktywnego listenera nic siê nie dzieje.
	 * 
	 * @see UserListener
	 */
	public void removeUserListener() {
		this.userListener = null;
	}

	/**
	 * Dodaj listenera zwi¹zanego z po³¹czeniem.<BR>
	 * Obs³uguje on takie zdarzenia jak nawi¹zanie po³¹czenia,
	 * zerwanie po³¹czenia itp.
	 * 
	 * @see ConnectionListener
	 * @param connectionListener obiekt listenera
	 */
	public void addConnectionListener(ConnectionListener connectionListener) {
		this.connectionListener = connectionListener;
	}
	/**
	 * Usuwa listenera zwi¹zanego z po³¹czeniem.<BR>
	 * Jê¿eli nie ma aktywnego listenera nic siê nie dzieje.
	 * 
	 * @see ConnectionListener
	 */
	public void removeConnectionListener() {
		this.connectionListener = null;
	}
	/**
	 * Dodaje listenera wiadomoœci.<BR>
	 * Obs³uguje on takie zdarzenia jak nadejœcie wiadomoœci.
	 * 
	 * @see MessageListener 
	 * @param messageListener obiekt listenera
	 */
	public void addMessageListener(MessageListener messageListener) {
		this.messageListener = messageListener;
	}

	/**
	 * Usuwa listenera wiadomoœci.<BR>
	 * Je¿eli nie ma aktywnego listenera nic siê nie dzieje.
	 * 
	 * @see MessageListener
	 */
	public void removeMessageListener() {
		this.messageListener = null;
	}
}