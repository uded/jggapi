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
public interface ILogger {
	/* (non-Javadoc)
	 * @see org.apache.log4j.Category#debug(java.lang.Object, java.lang.Throwable)
	 */public abstract void debug(Object arg0, Throwable arg1);
	/* (non-Javadoc)
	 * @see org.apache.log4j.Category#debug(java.lang.Object)
	 */public abstract void debug(Object arg0);
	/* (non-Javadoc)
	 * @see org.apache.log4j.Category#error(java.lang.Object, java.lang.Throwable)
	 */public abstract void error(Object arg0, Throwable arg1);
	/* (non-Javadoc)
	 * @see org.apache.log4j.Category#error(java.lang.Object)
	 */public abstract void error(Object arg0);
	/* (non-Javadoc)
	 * @see org.apache.log4j.Category#fatal(java.lang.Object, java.lang.Throwable)
	 */public abstract void fatal(Object arg0, Throwable arg1);
	/* (non-Javadoc)
	 * @see org.apache.log4j.Category#fatal(java.lang.Object)
	 */public abstract void fatal(Object arg0);
	/* (non-Javadoc)
	 * @see org.apache.log4j.Category#info(java.lang.Object, java.lang.Throwable)
	 */public abstract void info(Object arg0, Throwable arg1);
	/* (non-Javadoc)
	 * @see org.apache.log4j.Category#info(java.lang.Object)
	 */public abstract void info(Object arg0);
	/* (non-Javadoc)
	 * @see org.apache.log4j.Category#warn(java.lang.Object, java.lang.Throwable)
	 */public abstract void warn(Object arg0, Throwable arg1);
	/* (non-Javadoc)
	 * @see org.apache.log4j.Category#warn(java.lang.Object)
	 */public abstract void warn(Object arg0);
     
}