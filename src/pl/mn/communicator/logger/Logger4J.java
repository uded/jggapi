package pl.mn.communicator.logger;

import java.util.HashMap;
import java.util.Map;


/**
 * Przykladowy most do loggera log4j.
 *
 * @author mnaglik
 */
public class Logger4J extends Logger {
    private static Map loggers = new HashMap();
    private org.apache.log4j.Logger rootLogger;

    /**
     * Twórz instancje obiektu.
     * @param logger apachowy logger
     */
    private Logger4J(org.apache.log4j.Logger logger) {
        this.rootLogger = logger;
    }

    /**
     * @see pl.mn.communicator.logger.ILogger#debug(java.lang.Object,
     *      java.lang.Throwable)
     */
    public void debug(Object arg0, Throwable arg1) {
        rootLogger.debug(arg0, arg1);
    }

    /**
     * @see pl.mn.communicator.logger.ILogger#debug(java.lang.Object)
     */
    public void debug(Object arg0) {
        rootLogger.debug(arg0);
    }

    /**
     * @see pl.mn.communicator.logger.ILogger#error(java.lang.Object,
     *      java.lang.Throwable)
     */
    public void error(Object arg0, Throwable arg1) {
        rootLogger.error(arg0, arg1);
    }

    /**
     * @see pl.mn.communicator.logger.ILogger#error(java.lang.Object)
     */
    public void error(Object arg0) {
        rootLogger.error(arg0);
    }

    /**
     * @see pl.mn.communicator.logger.ILogger#fatal(java.lang.Object,
     *      java.lang.Throwable)
     */
    public void fatal(Object arg0, Throwable arg1) {
        rootLogger.fatal(arg0, arg1);
    }

    /**
     * @see pl.mn.communicator.logger.ILogger#fatal(java.lang.Object)
     */
    public void fatal(Object arg0) {
        rootLogger.fatal(arg0);
    }

    /**
     * @see pl.mn.communicator.logger.ILogger#info(java.lang.Object,
     *      java.lang.Throwable)
     */
    public void info(Object arg0, Throwable arg1) {
        rootLogger.info(arg0, arg1);
    }

    /**
     * @see pl.mn.communicator.logger.ILogger#info(java.lang.Object)
     */
    public void info(Object arg0) {
        rootLogger.info(arg0);
    }

    /**
     * @see pl.mn.communicator.logger.ILogger#warn(java.lang.Object,
     *      java.lang.Throwable)
     */
    public void warn(Object arg0, Throwable arg1) {
        rootLogger.warn(arg0, arg1);
    }

    /**
     * @see pl.mn.communicator.logger.ILogger#warn(java.lang.Object)
     */
    public void warn(Object arg0) {
        rootLogger.warn(arg0);
    }

    /**
     * Zwróæ instancje loggera.
     * @param clazz klasa która loguje
     * @return instancja loggera
     */
    public static synchronized Logger getLogger(Class clazz) {
        Logger4J l = (Logger4J) loggers.get(clazz);

        if (l == null) {
            l = new Logger4J(org.apache.log4j.Logger.getLogger(clazz));
            loggers.put(clazz, l);
        }

        return l;
    }
}
