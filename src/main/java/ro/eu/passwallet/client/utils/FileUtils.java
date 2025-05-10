package ro.eu.passwallet.client.utils;

import ro.eu.passwallet.service.LoggerService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Logger;

public class FileUtils {
    private static final Logger logger = LoggerService.getInstance().getLogger();

    private FileUtils(){}

    public static void backup(String walletXMLFilePath) {
        try {
            String backup = walletXMLFilePath + "_" + System.currentTimeMillis();
            Files.copy(new File(walletXMLFilePath).toPath(), new FileOutputStream(backup));
            logger.info(backup);
        } catch (IOException e) {
            logger.severe(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
