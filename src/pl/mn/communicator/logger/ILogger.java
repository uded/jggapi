package pl.mn.communicator.logger;

/**
 * @author mnaglik
 */
public interface ILogger {
    /**
     * @see org.apache.log4j.Category#debug(java.lang.Object,
     *      java.lang.Throwable)
     */
    void debug(Object arg0, Throwable arg1);

    /**
     * @see org.apache.log4j.Category#debug(java.lang.Object)
     */
    void debug(Object arg0);

    /**
     * @see org.apache.log4j.Category#error(java.lang.Object,
     *      java.lang.Throwable)
     */
    void error(Object arg0, Throwable arg1);

    /**
     * @see org.apache.log4j.Category#error(java.lang.Object)
     */
    void error(Object arg0);

    /**
     * @see org.apache.log4j.Category#fatal(java.lang.Object,
     *      java.lang.Throwable)
     */
    void fatal(Object arg0, Throwable arg1);

    /**
     * @see org.apache.log4j.Category#fatal(java.lang.Object)
     */
    void fatal(Object arg0);

    /**
     * @see org.apache.log4j.Category#info(java.lang.Object,
     *      java.lang.Throwable)
     */
    void info(Object arg0, Throwable arg1);

    /**
     * @see org.apache.log4j.Category#info(java.lang.Object)
     */
    void info(Object arg0);

    /**
     * @see org.apache.log4j.Category#warn(java.lang.Object,
     *      java.lang.Throwable)
     */
    void warn(Object arg0, Throwable arg1);

    /**
     * @see org.apache.log4j.Category#warn(java.lang.Object)
     */
    void warn(Object arg0);
}
