package pl.mn.communicator.logger;

/**
 * Przykladowy most do loggera log4j.
 *
 * @author mnaglik
 */
public class Logger4J extends Logger {
    /**
     * @param clazz klasa która loguje
     */
    public Logger4J(Class clazz) {
        // TODO Auto-generated constructor stub
    }

    /**
     * @see pl.mn.communicator.logger.ILogger#debug(java.lang.Object,
     *      java.lang.Throwable)
     */
    public void debug(Object arg0, Throwable arg1) {
        // TODO Auto-generated method stub
    }

    /**
     * @see pl.mn.communicator.logger.ILogger#debug(java.lang.Object)
     */
    public void debug(Object arg0) {
        // TODO Auto-generated method stub
    }

    /**
     * @see pl.mn.communicator.logger.ILogger#error(java.lang.Object,
     *      java.lang.Throwable)
     */
    public void error(Object arg0, Throwable arg1) {
        // TODO Auto-generated method stub
    }

    /**
     * @see pl.mn.communicator.logger.ILogger#error(java.lang.Object)
     */
    public void error(Object arg0) {
        // TODO Auto-generated method stub
    }

    /**
     * @see pl.mn.communicator.logger.ILogger#fatal(java.lang.Object,
     *      java.lang.Throwable)
     */
    public void fatal(Object arg0, Throwable arg1) {
        // TODO Auto-generated method stub
    }

    /**
     * @see pl.mn.communicator.logger.ILogger#fatal(java.lang.Object)
     */
    public void fatal(Object arg0) {
        // TODO Auto-generated method stub
    }

    /**
     * @see pl.mn.communicator.logger.ILogger#info(java.lang.Object,
     *      java.lang.Throwable)
     */
    public void info(Object arg0, Throwable arg1) {
        // TODO Auto-generated method stub
    }

    /**
     * @see pl.mn.communicator.logger.ILogger#info(java.lang.Object)
     */
    public void info(Object arg0) {
        // TODO Auto-generated method stub
    }

    /**
     * @see pl.mn.communicator.logger.ILogger#warn(java.lang.Object,
     *      java.lang.Throwable)
     */
    public void warn(Object arg0, Throwable arg1) {
        // TODO Auto-generated method stub
    }

    /**
     * @see pl.mn.communicator.logger.ILogger#warn(java.lang.Object)
     */
    public void warn(Object arg0) {
        // TODO Auto-generated method stub
    }

    /**
     * Zwróæ instancje loggera.
     * @param clazz klasa która loguje
     * @return instancja loggera
     */
    public static synchronized Logger getLogger(Class clazz) {
        return new Logger4J(clazz);
    }
}
