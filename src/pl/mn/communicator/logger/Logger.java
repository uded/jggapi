package pl.mn.communicator.logger;

import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;


/**
 * Klasa fabrykujaca logger.
 *
 * @author mnaglik
 */
public abstract class Logger implements ILogger {
    private static NullLogger nullLogger = new NullLogger();
    private static boolean log;

    static {
        Properties prop = new Properties();

        try {
            InputStream in = Logger.class.getResourceAsStream("log.properties");
            prop.load(in);
            in.close();
        } catch (IOException e) {
            System.err.println("Blad czytania parametrow logowania.");
        }

        log = Boolean.valueOf(prop.getProperty("log", "true")).booleanValue();
        System.out.println("LOG: " + log);
    }

    protected Logger() {
    }

    /**
     * Zwróc instancje loggera.
     * @param clazz klasa która loguje
     * @return instancja loggera
     */
    public static synchronized Logger getLogger(Class clazz) {
        if (!log) {
            return nullLogger;
        } else {
            return Logger4J.getLogger(clazz);
        }
    }
}
