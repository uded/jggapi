/*
 * Created on 2004-11-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pl.mn.communicator;

import java.util.EventObject;

/**
 * @author mateusz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SessionStateEvent extends EventObject {

	private int m_oldState = -1;
	private int m_newState = -1;
	
	public SessionStateEvent(Object source, int oldState, int newState) {
		super(source);
		m_oldState = oldState;
		m_newState = newState;
	}
	
	public int getOldState() {
		return m_oldState;
	}
	
	public int getNewState() {
		return m_newState;
	}
	
}
