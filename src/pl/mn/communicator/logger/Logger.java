/*
 * Created on 2004-04-28
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package pl.mn.communicator.logger;

/**
 * @author mna
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Logger implements ILogger{
    private static Logger instance;

    /* (non-Javadoc)
	 * @see org.apache.log4j.Category#debug(java.lang.Object, java.lang.Throwable)
	 */
	public void debug(Object arg0, Throwable arg1) {
		instance.debug(arg0,arg1);
    }
	/* (non-Javadoc)
	 * @see org.apache.log4j.Category#debug(java.lang.Object)
	 */
	public void debug(Object arg0) {
        instance.debug(arg0);
	}
	/* (non-Javadoc)
	 * @see org.apache.log4j.Category#error(java.lang.Object, java.lang.Throwable)
	 */
	public void error(Object arg0, Throwable arg1) {
		instance.error(arg0,arg1);
    }
	/* (non-Javadoc)
	 * @see org.apache.log4j.Category#error(java.lang.Object)
	 */
	public void error(Object arg0) {
		instance.error(arg0);
    }
	/* (non-Javadoc)
	 * @see org.apache.log4j.Category#fatal(java.lang.Object, java.lang.Throwable)
	 */
	public void fatal(Object arg0, Throwable arg1) {
		instance.fatal(arg0, arg1);
    }
	/* (non-Javadoc)
	 * @see org.apache.log4j.Category#fatal(java.lang.Object)
	 */
	public void fatal(Object arg0) {
        instance.fatal(arg0);
    }
	/* (non-Javadoc)
	 * @see org.apache.log4j.Category#info(java.lang.Object, java.lang.Throwable)
	 */
	public void info(Object arg0, Throwable arg1) {
		instance.info(arg0,arg1);
    }
	/* (non-Javadoc)
	 * @see org.apache.log4j.Category#info(java.lang.Object)
	 */
	public void info(Object arg0) {
        instance.info(arg0);
	}
	/* (non-Javadoc)
	 * @see org.apache.log4j.Category#warn(java.lang.Object, java.lang.Throwable)
	 */
	public void warn(Object arg0, Throwable arg1) {
		instance.warn(arg0,arg1);
    }
	/* (non-Javadoc)
	 * @see org.apache.log4j.Category#warn(java.lang.Object)
	 */
	public void warn(Object arg0) {
        instance.warn(arg0);
	}
    
    public static synchronized Logger getLogger(Class clazz){
    	if (instance == null)
            instance = new Logger4J();
        return instance;
    }
}
