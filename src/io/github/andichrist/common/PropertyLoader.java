package io.github.andichrist.common;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * evaluate main property file and loads specific properties
 *
 * Created by andichrist on 27.11.16.
 */
public class PropertyLoader {
    private final static Logger LOGGER = Logger.getLogger(PropertyLoader.class.getName());

    private static final String FRAKTAL = "fraktal.";

    private static Properties properties;

    static {
        ClassLoader classLoader = PropertyLoader.class.getClassLoader();

        Properties basicProperties = new Properties();

            try {
                basicProperties.load(classLoader.getResourceAsStream("fraktal.properties"));
            } catch (IOException e) {
                e.printStackTrace();
            }


        String type = basicProperties.getProperty("type");
        LOGGER.log(Level.INFO, "Rendering stategy: {0}", type);

        if (properties == null) {
            properties = new Properties();

            try {
                properties.load(classLoader.getResourceAsStream(FRAKTAL + type + ".properties"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
