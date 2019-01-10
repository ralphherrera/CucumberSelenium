package com.cucumberselenium.rpdch.utilities;

import com.cucumberselenium.rpdch.constants.CommonConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class FileMgmtUtil {

    private static final Logger logger = LogManager.getLogger(FileMgmtUtil.class);

    private static Properties readPropertyValue() {
        Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(CommonConstants.CONFIG_PROP_PATH);
            if (new File(CommonConstants.CONFIG_PROP_PATH).isFile()) {
                properties.load(fileInputStream);
            }
        } catch (IOException ioe) {
            logger.error("config.properties not found!");
//            throw new IOException("config.properties not found!");
        } catch (Exception e) {
            logger.error("Something went wrong reading the config.properties");
        }
        return properties;
    }

    public static String getPropertyValue(String key) {
        logger.info("Getting value from config.properties with key {}", key);

        return readPropertyValue().getProperty(key);
    }

    public static Integer getNumberValue(String key) {
        logger.info("Getting value from config.properties with key {}", key);
        Integer num = null;
        try {
            num = Integer.parseInt(getPropertyValue(key));
        } catch (NumberFormatException nfe) {
            logger.error("Invalid number format for key {} in config.properties", key);
        }
        return num;
    }
}
