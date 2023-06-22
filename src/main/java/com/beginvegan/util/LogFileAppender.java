package com.beginvegan.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;

@Component
public class LogFileAppender {

    private static final Logger logger = LoggerFactory.getLogger(LogFileAppender.class);

    private static final String LOG_FILE_PATH = System.getProperty("user.dir") + "/LogFile.txt";

    public static void appendLog(String logMessage) {
        try (FileWriter writer = new FileWriter(LOG_FILE_PATH, true)) {
            writer.write(logMessage);
            writer.write(System.lineSeparator());
        } catch (IOException e) {
            logger.error("Failed to append log to file: " + e.getMessage());
        }
    }
}