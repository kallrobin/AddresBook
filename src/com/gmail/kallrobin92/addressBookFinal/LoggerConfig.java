package com.gmail.kallrobin92.addressBookFinal;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Created by Robin Gk on 2016-11-25 as a school project.
 * email kallrobin92@gmail.com
 */
class LoggerConfig {
    private final static Logger log = Logger.getLogger(LoggerConfig.class.getName());

    public LoggerConfig() {
        setupLogging();
        log.info("Logging started");
    }


    private void setupLogging() {
        String loggingFilePath = "logging.properties";
        try (FileInputStream fileInputStream = new FileInputStream(loggingFilePath)) {
            LogManager.getLogManager().readConfiguration(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException("Could not load log properties", e);

        }
    }
}

