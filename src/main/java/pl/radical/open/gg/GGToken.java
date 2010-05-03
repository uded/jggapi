package pl.radical.open.gg;

/**
 * Created on 2005-01-26
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class GGToken {

	/** the width of a picture */
	private int imageWidth;

	/** the height of a picture */
	private int imageHeight;

	/** the length of token */
	private int tokenLength;

	/** the id of token */
	private String tokenID;

	/** the token URL */
	private String tokenURL;

	/**
	 * @return Returns the imageWidth.
	 */
	public int getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(final int imageWidth) {
		if (imageWidth < 0) {
			throw new IllegalArgumentException("imageWidth cannot be null");
		}
		this.imageWidth = imageWidth;
	}

	public int getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(final int imageHeight) {
		if (imageHeight < 0) {
			throw new IllegalArgumentException("imageHeight cannot be null");
		}
		this.imageHeight = imageHeight;
	}

	public String getTokenURL() {
		return tokenURL;
	}

	public void setTokenURL(final String tokenURL) {
		this.tokenURL = tokenURL;
	}

	public String getTokenID() {
		return tokenID;
	}

	public void setTokenID(final String tokenID) {
		this.tokenID = tokenID;
	}

	public int getTokenLength() {
		return tokenLength;
	}

	public void setTokenLength(final int tokenLength) {
		this.tokenLength = tokenLength;
	}

	public String getFullTokenURL() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append(getTokenURL());
		buffer.append("?tokenid=");
		buffer.append(getTokenID());

		return buffer.toString();
	}

}
