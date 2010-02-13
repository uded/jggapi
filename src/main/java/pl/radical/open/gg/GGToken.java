package pl.radical.open.gg;

/**
 * Created on 2005-01-26
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class GGToken {

	/** the width of a picture */
	private int m_imageWidth;

	/** the height of a picture */
	private int m_imageHeight;

	/** the length of token */
	private int m_tokenLength;

	/** the id of token */
	private String m_tokenID;

	/** the token URL */
	private String m_tokenURL;

	/**
	 * @return Returns the imageWidth.
	 */
	public int getImageWidth() {
		return m_imageWidth;
	}

	public void setImageWidth(final int imageWidth) {
		if (imageWidth < 0) {
			throw new IllegalArgumentException("imageWidth cannot be null");
		}
		m_imageWidth = imageWidth;
	}

	public int getImageHeight() {
		return m_imageHeight;
	}

	public void setImageHeight(final int imageHeight) {
		if (imageHeight < 0) {
			throw new IllegalArgumentException("imageHeight cannot be null");
		}
		m_imageHeight = imageHeight;
	}

	public String getTokenURL() {
		return m_tokenURL;
	}

	public void setTokenURL(final String tokenURL) {
		m_tokenURL = tokenURL;
	}

	public String getTokenID() {
		return m_tokenID;
	}

	public void setTokenID(final String tokenID) {
		m_tokenID = tokenID;
	}

	public int getTokenLength() {
		return m_tokenLength;
	}

	public void setTokenLength(final int tokenLength) {
		m_tokenLength = tokenLength;
	}

	public String getFullTokenURL() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append(getTokenURL());
		buffer.append("?tokenid=");
		buffer.append(getTokenID());

		return buffer.toString();
	}

}
