package pl.radical.open.gg.event;

import java.util.EventObject;

/**
 * Created on 2005-10-25
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class LoginFailedEvent extends EventObject {
	private static final long serialVersionUID = 8954960680213823334L;

	public final static int NEED_EMAIL_REASON = 0;
	public final static int WRONG_PASSWORD = 1;

	private int reason = WRONG_PASSWORD;

	public LoginFailedEvent(final Object source) {
		super(source);
	}

	public int getReason() {
		return reason;
	}

	public void setReason(final int reason) {
		if (reason == NEED_EMAIL_REASON || reason == WRONG_PASSWORD) {
			this.reason = reason;
		} else {
			throw new IllegalArgumentException("Incorrect reason");
		}

	}

}
