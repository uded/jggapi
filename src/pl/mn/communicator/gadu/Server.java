package pl.mn.communicator.gadu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import pl.mn.communicator.AbstractServer;
import pl.mn.communicator.ILocalUser;

/**
 * Klasa z danymi dotycz¹cymi serwera gg.
 * 
 * @version $Revision: 1.6 $
 * @author mnaglik
 */
public final class Server extends AbstractServer {
	private static Logger logger = Logger.getLogger(Server.class);
	public Server(String address, int port) {
		super(address, port);
	}

	/**
	 * Pobierz serwera gg ze strony www udostêpnionej na serwerze www gg.
	 * 
	 * @param localUser u¿ytkownik (potrzebny do stworzenia adresu)
	 * @return Server serwer
	 * @throws IOException
	 */
	public static Server getDefaultServer(ILocalUser user) throws IOException {

		URL url =
			new URL(
				"http://appmsg.gadu-gadu.pl/appsvc/appmsg.asp?fmnumber="
					+ user.getUserNo());

		InputStream is = url.openStream();
		BufferedReader in = new BufferedReader(new InputStreamReader(is));

		String line = in.readLine();
		is.close();
		in.close();
		return parseAddress(line);
	}

	private static Server parseAddress(String line) {
		StringTokenizer token = new StringTokenizer(line);
		for (int i = 0; i < 3; i++)
			token.nextToken();

		StringTokenizer token1 = new StringTokenizer(token.nextToken(), ":");
		return new Server(
			token1.nextToken(),
			Integer.parseInt(token1.nextToken()));
	}
}
