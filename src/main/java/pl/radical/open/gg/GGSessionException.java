package pl.radical.open.gg;

/**
 * Created on 2004-11-30 Exception that is thrown when user tries to move from certain state to state that is not
 * allowed at that moment.
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class GGSessionException extends GGException {
	private static final long serialVersionUID = 4199162248270553752L;
	private final SessionState sessionState; // NOPMD by LRzanek on 04.05.10 00:55

	public GGSessionException(final SessionState sessionState) {
		super("Incorrect session state: " + sessionState);
		this.sessionState = sessionState;
	}

	public SessionState getSessionState() {
		return sessionState;
	}

}
