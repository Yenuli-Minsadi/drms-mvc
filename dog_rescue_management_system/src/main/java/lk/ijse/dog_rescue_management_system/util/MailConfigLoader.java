package lk.ijse.dog_rescue_management_system.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MailConfigLoader {
    private static final Properties props = new Properties();

    static {
        try (InputStream input = MailConfigLoader.class.getResourceAsStream("/config.properties")) {
            if (input == null) {
                throw new RuntimeException("Configuration file not found.");
            }
            props.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String getEmail() {
        return props.getProperty("email");
    }

    public static String getPassword() {
        return props.getProperty("password");
    }
}