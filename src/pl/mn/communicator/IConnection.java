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

import java.io.IOException;

import java.util.Collection;


/**
 * Po³±czenie z serwerem gg.<BR>
 * S³u¿y do tworzenia po³±czenia.
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
 * @version $Revision: 1.12 $
 * @author mnaglik
 */
public interface IConnection {
    /**
     * Dodaj listenera u¿ytkowników.<BR>
     * Obs³uguje odpowiednie zdarzenia zwi¹zane z u¿ytkownikami
     * takie jak pryj¶cie i odej¶cie u¿ytkownika
     * @see UserListener
     * @param userListener obiekt listenera
     */
    void addUserListener(UserListener userListener);

    /**
     * Usuwa listenera u¿ytkowników.<BR>
     * Je¿eli nie ma aktywnego listenera nic siê nie dzieje.
     * @see UserListener
     */
    void removeUserListener();

    /**
     * Dodaj listenera zwi±zanego z po³±czeniem.<BR>
     * Obs³uguje on takie zdarzenia jak nawi¹zanie po³±czenia,
     * zerwanie po³±czenia itp.
     * @see ConnectionListener
     * @param connectionListener obiekt listenera
     */
    void addConnectionListener(ConnectionListener connectionListener);

    /**
     * Usuwa listenera zwi±zanego z po³±czeniem.<BR>
     * Jê¿eli nie ma aktywnego listenera nic siê nie dzieje.
     * @see ConnectionListener
     */
    void removeConnectionListener();

    /**
     * Dodaje listenera wiadomo¶ci.<BR>
     * Obs³uguje on takie zdarzenia jak nadej¶cie wiadomo¶ci.
     * @see MessageListener
     * @param messageListener obiekt listenera
     */
    void addMessageListener(MessageListener messageListener);

    /**
     * Usuwa listenera wiadomo¶ci.<BR>
     * Je¿eli nie ma aktywnego listenera nic siê nie dzieje.
     * @see MessageListener
     */
    void removeMessageListener();

    /**
     * Pod³±cz sie do serwera rozmów.<BR>
     * Próbuje ³±czyæ siê z serwerem rozmów, na podstawie danych<BR>
     * z konstruktora.<BR>
     * W wypadku niepowodzenie wyrzuca odpowiednie wyj±tki
     * @throws UnknownHostException nieznany serwer
     * @throws IOException nie powiodla siê próba po³±czenia
     * - nie ma po³±czenia sieciowego?
     */
    void connect() throws IOException;

    /**
     * Zamyka po³±czenie z serwerem rozmów.
     * @throws IOException b³±d przy zamykaniu po³±czenia
     */
    void disconnect() throws IOException;

    /**
     * Wy¶lij wiadomo¶æ do serwera rozmów.
     * @see AbstractMessage
     * @param message wiadomo¶æ do wys³ania.
     * @throws IOException b³±d wysy³ania wiadomo¶ci
     */
    void sendMessage(IMessage message) throws IOException;

    /**
     * Zmien aktualny status u¿ytkownika.<BR>
     * @param status - kolejny status
     * @throws IOException b³±d zmiany statusu
     */
    void changeStatus(IStatus status) throws IOException;

    /**
     * Wy¶lij listê monitorowanych u¿ytkowników.<br>
     * Lista zawiera u¿ytkowników dla których serwer
     * przesy³a informacje o zmianie statusu
     * @param userList lista u¿ytkowników
     * @throws IOException b³±d wysy³ania listy u¿ytkowników
     */
    void sendMonitoredUserList(Collection userList) throws IOException;

    /**
     * Dodaj u¿ytkownika do u¿ytkowników monitorowanych.<br>
     * Dla dodanego u¿ytkownika bêdzie przesy³ana informacja o zmianie
     * statusu.
     * @param user u¿ytkownik do monitorowania
     * @throws IOException b³±d dodawania u¿ytkownika
     */
    void addMonitoredUser(IUser user) throws IOException;

    /**
     * Usuñ u¿ytkownika z listy u¿ytkowników monitorowanych.<br>
     * Dla podanego u¿ytkownika nie bêdzie przesy³ana informacja
     * o zmianie statusu.
     * @param user u¿tykownik do niemonitorowania
     * @throws IOException b³ad usuwania u¿ytkownika
     */
    void removeMonitoredUser(IUser user) throws IOException;
}
