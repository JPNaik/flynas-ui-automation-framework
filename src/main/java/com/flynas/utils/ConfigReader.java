package com.flynas.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties prop = new Properties();
    private static final Logger log = LogManager.getLogger(ConfigReader.class);

    static {
        try {
            String environment = System.getProperty("env", "prelive").toLowerCase().trim();
            String globalConfigPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
                    + File.separator + "resources" + File.separator + "config" + File.separator + "config.properties";
            try {
                FileInputStream globalFile = new FileInputStream(globalConfigPath);
                prop.load(globalFile);
                log.info("Successfully loaded global configuration properties from: {}", globalConfigPath);
            } catch (FileNotFoundException e) {
                log.error("CRITICAL: Global config file not found at location: {}", globalConfigPath, e);
                throw new RuntimeException("Missing global base configuration file", e);
            } catch (IOException e) {
                log.error("ERROR: Failed to parse and load global configuration stream data", e);
                throw new RuntimeException("Corrupted global configuration parsing", e);
            }
            String envConfigPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
                    + File.separator + "resources" + File.separator + "config" + File.separator + environment + "-config.properties";

            try {
                FileInputStream envConfigFile = new FileInputStream(envConfigPath);
                prop.load(envConfigFile);
                log.info("Successfully layered target environment profiles from: {}", envConfigPath);
            } catch (FileNotFoundException ex) {
                log.error("CRITICAL: Environment specific file profile not found at location: {}", envConfigPath, ex);
                throw new RuntimeException("Missing targeted environment file definition", ex);
            } catch (IOException ex) {
                log.error("ERROR: Failed to parse and load environment configuration stream data", ex);
                throw new RuntimeException("Corrupted environment configuration parsing", ex);
            }
        } catch (Exception exception) {
            log.error("Failed to initialize configuration files properties mappings",exception);
        }
        }

        public static String getProperty (String key)
        {
            String systemOverride = System.getProperty(key);
            if (systemOverride != null) {
                return systemOverride;
            }
            return prop.getProperty(key);
        }
}
