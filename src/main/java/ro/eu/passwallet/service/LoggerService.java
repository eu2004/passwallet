package ro.eu.passwallet.service;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;


public final class LoggerService {
    private static final LoggerService instance = new LoggerService();

    private static final Logger LOGGER = Logger.getLogger("passwallet");
    private static final LogManager logManager = LogManager.getLogManager();
    static {
        try {
            logManager.readConfiguration(LoggerService.class.getResourceAsStream("/logging.properties"));
        } catch (IOException exception) {
            LOGGER.log(Level.SEVERE, "Exception occur", exception);
        }
    }

    private LoggerService(){
    }

    public static LoggerService getInstance() {
        return instance;
    }

    public Logger getLogger() {
        return LOGGER;
    }
}
