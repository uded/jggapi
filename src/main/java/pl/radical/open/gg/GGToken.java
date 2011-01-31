package pl.radical.open.gg;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @author <a href="mailto:lukasz.rzanek@radical.com.pl">Łukasz Rżanek</a>
 * @since 2005-01-26
 */
@Data
// FIXME Seriously?
@NoArgsConstructor
@RequiredArgsConstructor
public class GGToken {

	/** the width of a picture */
	@NonNull
	private int imageWidth;

	/** the height of a picture */
	@NonNull
	private int imageHeight;

	/** the length of token */
	@NonNull
	private int tokenLength;

	/** the id of token */
	@NonNull
	private String tokenID;

	/** the token URL */
	@NonNull
	private String tokenURL;

	public String getFullTokenURL() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append(getTokenURL());
		buffer.append("?tokenid=");
		buffer.append(getTokenID());

		return buffer.toString();
	}

}
