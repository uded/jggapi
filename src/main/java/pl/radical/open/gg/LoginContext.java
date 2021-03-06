package pl.radical.open.gg;

import pl.radical.open.gg.packet.dicts.StatusType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * This class represents information that will be used during login process.
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public final class LoginContext {

	/** the uin that will be used during login */
	private int m_uin = -1;

	/** password that will be used during login */
	private String m_password = null;

	/** Initial status */
	private ILocalStatus m_localStatus = new LocalStatus(StatusType.ONLINE);

	/** The list of users that we are intrested in */
	private final Collection<IUser> m_monitoredUsers = new ArrayList<IUser>();

	/** the max image size */
	private byte m_imageSize = 64;

	/** the local IP of the user */
	private byte[] m_localIP = null;

	/** the local port of the client */
	private int m_localPort = -1;

	/** The external IP of the client */
	private byte[] m_externalIP = null;

	/** the external port of the client */
	private int m_externalPort = -1;

	public LoginContext(final int uin, final String password) {
		if (uin < 0) {
			throw new IllegalArgumentException("uin cannot be less than 0");
		}
		if (password.isEmpty()) {
			throw new IllegalArgumentException("password cannot be null");
		}
		m_uin = uin;
		m_password = password;
	}

	public String getPassword() {
		return m_password;
	}

	public int getUin() {
		return m_uin;
	}

	public void setPassword(final String password) {
		if (password.isEmpty()) {
			throw new IllegalArgumentException("password cannot be null");
		}
		m_password = password;
	}

	public void setUin(final int uin) {
		if (uin < 0) {
			throw new IllegalArgumentException("uin must be a positive number");
		}
		m_uin = uin;
	}

	public void setStatus(final ILocalStatus localStatus) {
		if (localStatus == null) {
			throw new IllegalArgumentException("localStatus cannot be null");
		}
		m_localStatus = localStatus;
	}

	public ILocalStatus getStatus() {
		return m_localStatus;
	}

	public void setImageSize(final byte imageSize) {
		if (imageSize < 0) {
			throw new IllegalArgumentException("imageSize cannot be less than 0");
		}
		m_imageSize = imageSize;
	}

	public byte getImageSize() {
		return m_imageSize;
	}

	public byte[] getExternalIP() {
		return m_externalIP;
	}

	public void setExternalIP(final byte[] externalIP) {
		if (externalIP == null) {
			throw new IllegalArgumentException("externalIP cannot be null");
		}
		if (externalIP.length == 4) {
			throw new IllegalArgumentException("Incorrect address.");
		}
		m_externalIP = Arrays.copyOf(externalIP, externalIP.length);
	}

	public void setExternalPort(final int externalPort) {
		if (externalPort < 0) {
			throw new IllegalArgumentException("externalPort cannot be less than 0");
		}
		m_externalPort = externalPort;
	}

	public int getExternalPort() {
		return m_externalPort;
	}

	public byte[] getLocalIP() {
		return m_localIP;
	}

	public void setLocalIP(final byte[] localIP) {
		if (localIP == null) {
			throw new IllegalArgumentException("localIP cannot be null");
		}
		if (localIP.length == 4) {
			throw new IllegalArgumentException("Incorrect address.");
		}
		m_localIP = Arrays.copyOf(localIP, localIP.length);
	}

	public void setLocalPort(final int localPort) {
		if (localPort < 0) {
			throw new IllegalArgumentException("localPort cannot be less than 0");
		}
		m_localPort = localPort;
	}

	public int getLocalPort() {
		return m_localPort;
	}

	public Collection<IUser> getMonitoredUsers() {
		return m_monitoredUsers;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("LoginContext[%d,password=xxx(masking)]", m_uin);
	}

}
