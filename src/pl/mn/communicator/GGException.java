/*
 * Created on 2004-11-30
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pl.mn.communicator;

/**
 * @author mateusz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GGException extends Exception {

	public GGException(String message) {
		super(message);
	}
	
	public GGException(String message, Exception ex) {
		super(message, ex);
	}
	
}
