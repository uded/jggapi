package pl.radical.open.gg;

/**
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public final class Server implements IServer {

	/**
	 * The server's address
	 */
	private String address = null;

	/**
	 * The server's port
	 */
	private int port = -1;

	public Server() {
	}

	/**
	 * @param address
	 *            the server's address.
	 * @param port
	 *            the server's port.
	 * @throws NullPointerException
	 *             if the address is null.
	 * @throws IllegalArgumentException
	 *             if the port is not value between 0 and 65535.
	 */
	public Server(final String address, final int port) {
		if (address.isEmpty()) {
			throw new IllegalArgumentException("address cannot be null");
		}
		if (!address.matches("(?:\\d{1,3}\\.?+){4}")) {
			throw new IllegalArgumentException("adrres format not correct");
		}
		if (port < 0 || port > 65535) {
			throw new IllegalArgumentException("port cannot be less than 0 and grather than 65535");
		}
		this.address = address;
		this.port = port;
	}

	public String getAddress() {
		return address;
	}

	public int getPort() {
		return port;
	}

	@Override
	public String toString() {
		return "[" + address + ":" + port + "]";
	}

}
