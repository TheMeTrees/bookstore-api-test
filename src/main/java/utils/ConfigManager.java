package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * ConfigManager is a singleton utility class for loading and providing
 * access to environment specific configuration properties.
 *
 * <p>It dynamically selects a configuration file based on the environment
 * specified via the system property {@code -Denv=ENV_NAME}. If no environment
 * is specified, it defaults to {@code config-local.properties}<p/>
 * */
public class ConfigManager {

    private static ConfigManager configManager;
    private static final Properties properties = new Properties();

    /**
     * Private constructor that loads the appropriate configuration
     * file based on the system property {@code env}, or defaults to {@code local}.
     */
    private ConfigManager() {
        String env = System.getProperty("env", "local").toLowerCase();
        String configFile = "src/test/resources/config-" + env + ".properties";

        try (FileInputStream fileInputStream = new FileInputStream(configFile)) {
            properties.load(fileInputStream);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load config file: " + configFile, e);
        }
    }

    /**
     * Returns the singleton instance of ConfigManager.
     *
     * @return the ConfigManager instance
     */
    public static ConfigManager getInstance() {
        // Double check locking to  ensure only one thread can create the instance
        if (configManager == null) {
            synchronized (ConfigManager.class) {
                if (configManager == null) {
                    configManager = new ConfigManager();
                }
            }
        }

        return configManager;
    }

    /**
     * Retrieves the value of a given property key as a String.
     *
     * @param key the name of the property
     * @return the property value as a String
     * @throws IllegalArgumentException if the property is not found
     */
    public String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new IllegalArgumentException("Property '" + key + "' not found in config.properties");
        }

        return value;
    }

    /**
     * Retrieves the value of a given property key as an integer.
     *
     * @param key the name of the property
     * @return the property value as an integer
     */
    public int getIntProperty(String key) {

        return Integer.parseInt(getProperty(key));
    }

    /**
     * Returns the resolved value of a property, preferring a system property override
     * and falling back to the config file.
     *
     * @param key the name of the property
     * @return the resolved value as a String
     */
    public String getResolvedProperty(String key) {
        String systemValue = System.getProperty(key);
        if (systemValue != null && !systemValue.isBlank()) {
            return systemValue;
        }

        return getProperty(key);
    }

}