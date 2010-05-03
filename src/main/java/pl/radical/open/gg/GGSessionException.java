package pl.radical.open.gg;

/**
 * Created on 2004-11-30 Exception that is thrown when user tries to move from certain state to state that is not
 * allowed at that moment.
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class GGSessionException extends GGException {

	/**
     * 
     */
	private static final long serialVersionUID = 4199162248270553752L;
	private final SessionState sessionState;

	public GGSessionException(final SessionState actualSessionState) {
		super("Incorrect session state: " + actualSessionState);
		sessionState = actualSessionState;
	}

	public SessionState getSessionState() {
		return sessionState;
	}

}
