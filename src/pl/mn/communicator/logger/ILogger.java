package pl.mn.communicator.logger;

/**
 * @author mnaglik
 */
public interface ILogger {
    /**
     * @see org.apache.log4j.Category#debug(java.lang.Object,
     *      java.lang.Throwable)
     */
    public abstract void debug(Object arg0, Throwable arg1);

    /**
     * @see org.apache.log4j.Category#debug(java.lang.Object)
     */
    public abstract void debug(Object arg0);

    /**
     * @see org.apache.log4j.Category#error(java.lang.Object,
     *      java.lang.Throwable)
     */
    public abstract void error(Object arg0, Throwable arg1);

    /**
     * @see org.apache.log4j.Category#error(java.lang.Object)
     */
    public abstract void error(Object arg0);

    /**
     * @see org.apache.log4j.Category#fatal(java.lang.Object,
     *      java.lang.Throwable)
     */
    public abstract void fatal(Object arg0, Throwable arg1);

    /**
     * @see org.apache.log4j.Category#fatal(java.lang.Object)
     */
    public abstract void fatal(Object arg0);

    /**
     * @see org.apache.log4j.Category#info(java.lang.Object,
     *      java.lang.Throwable)
     */
    public abstract void info(Object arg0, Throwable arg1);

    /**
     * @see org.apache.log4j.Category#info(java.lang.Object)
     */
    public abstract void info(Object arg0);

    /**
     * @see org.apache.log4j.Category#warn(java.lang.Object,
     *      java.lang.Throwable)
     */
    public abstract void warn(Object arg0, Throwable arg1);

    /**
     * @see org.apache.log4j.Category#warn(java.lang.Object)
     */
    public abstract void warn(Object arg0);
}
