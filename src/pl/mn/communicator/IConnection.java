/*
 * Created on 2003-10-01
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package pl.mn.communicator;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * @author mna
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public interface IConnection {
	/**
	 * Dodaj listenera u¿ytkowników.<BR>
	 * Obs³uguje odpowiednie zdarzenia zwi¹zane z u¿ytkownikami
	 * takie jak pryjœcie i odejœcie u¿ytkownika
	 * 
	 * @see UserListener
	 * @param userListener obiekt listenera
	 */
	public void addUserListener(UserListener userListener);
	/**
	 * Usuwa listenera u¿ytkowników.<BR>
	 * Je¿eli nie ma aktywnego listenera nic siê nie dzieje.
	 * 
	 * @see UserListener
	 */
	public void removeUserListener();
	/**
	 * Dodaj listenera zwi¹zanego z po³¹czeniem.<BR>
	 * Obs³uguje on takie zdarzenia jak nawi¹zanie po³¹czenia,
	 * zerwanie po³¹czenia itp.
	 * 
	 * @see ConnectionListener
	 * @param connectionListener obiekt listenera
	 */
	public void addConnectionListener(ConnectionListener connectionListener);
	/**
	 * Usuwa listenera zwi¹zanego z po³¹czeniem.<BR>
	 * Jê¿eli nie ma aktywnego listenera nic siê nie dzieje.
	 * 
	 * @see ConnectionListener
	 */
	public void removeConnectionListener();
	/**
	 * Dodaje listenera wiadomoœci.<BR>
	 * Obs³uguje on takie zdarzenia jak nadejœcie wiadomoœci.
	 * 
	 * @see MessageListener 
	 * @param messageListener obiekt listenera
	 */
	public void addMessageListener(MessageListener messageListener);
	/**
	 * Usuwa listenera wiadomoœci.<BR>
	 * Je¿eli nie ma aktywnego listenera nic siê nie dzieje.
	 * 
	 * @see MessageListener
	 */
	public void removeMessageListener();
	/**
	 * Pod³¹cz sie do serwera rozmów.<BR>
	 * Próbuje ³¹czyæ siê z serwerem rozmów, na podstawie danych<BR>
	 * z konstruktora.<BR>
	 * W wypadku niepowodzenie wyrzuca odpowiednie wyj¹tki
	 * 
	 * @throws UnknownHostException nieznany serwer 
	 * @throws IOException nie powiodla siê próba po³¹czenia - nie ma po³¹czenia sieciowego?
	 */
	public void connect() throws UnknownHostException, IOException;
	/**
	 * Zamyka po³¹czenie z serwerem rozmów.
	 * 
	 * @throws IOException b³¹d przy zamykaniu po³¹czenia
	 */
	public void disconnect() throws IOException;
	/**
	 * Wyœlij wiadomoœæ do serwera rozmów.
	 * 
	 * @see AbstractMessage
	 * @param message wiadomoœæ do wys³ania.
	 */
	public void sendMessage(AbstractMessage message)
		throws IOException;
	/**
	 * Zmien aktualny status u¿ytkownika.<BR>
	 * 
	 * @param status - kolejny status
	 */
	public void changeStatus(AbstractStatus status)
		throws IOException;
}