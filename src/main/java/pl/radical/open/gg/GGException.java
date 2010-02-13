package pl.radical.open.gg;

/**
 * Created on 2004-11-30
 * <P>
 * An exception that provides information on access to Gadu-Gadu server. It contains the exception chain.
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class GGException extends Exception {

	/**
     * 
     */
	private static final long serialVersionUID = -2956708463640990150L;

	public GGException(final String message) {
		super(message);
	}

	public GGException(final String message, final Exception ex) {
		super(message, ex);
	}

}
