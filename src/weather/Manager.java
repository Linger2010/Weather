package weather;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.FileHandler;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Manager {

    /**
     *  Finds a named logger. A new logger is created if not exists.
     *
     * @param name name of the Logger
     *
     * @return matching logger or a new one if not found.
     */
    public static Logger getLogger(String name) {
        Objects.requireNonNull(name);
        var logManager = LogManager.getLogManager();
        var log = logManager.getLogger(name);
        if(log == null) {
            log = Logger.getLogger(name);

            try {
                log.setUseParentHandlers(false);
                var builder = new StringBuilder(name);
                builder.append("-log.%u.%g.txt");
                var handler = new FileHandler(builder.toString(), true);
                handler.setFormatter(new SimpleFormatter());
                log.addHandler(handler);
                logManager.addLogger(log);
            } catch (IOException iOEx) {
                System.err.println(iOEx.getMessage());
            }

        } // else, the log with the name exists, doNothing();

        return logManager.getLogger(name);
    }
}
