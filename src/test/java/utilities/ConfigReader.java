package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.io.InputStream;
public class ConfigReader {
	private static final Properties properties = new Properties();
    private static final String CONFIG_FILE_PATH = "src/test/resources/config.properties";
    static {
        loadConfigProperties();
    }
    public static void loadConfigProperties() {
        try (InputStream inputStream = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(inputStream);
            System.out.println("Config File Loaded: " + CONFIG_FILE_PATH);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
    public static String getUserId() {
        return properties.getProperty("userId");
    }
    public static String getUserFirstName() {
        return properties.getProperty("userFirstName");
    }
    // updating user id and username dynamically
    public static void updateUserDetailsInConfig(int userId, String userFirstName) {
        try {
            properties.setProperty("userId", String.valueOf(userId));
            properties.setProperty("userFirstName", userFirstName);
            try (FileOutputStream outputStream = new FileOutputStream(CONFIG_FILE_PATH)) {
                properties.store(outputStream, "Updated user details dynamically");
            }
            System.out.println("Updated userId and userFirstName in config.properties");
        } catch (IOException e) {
            throw new RuntimeException("Failed to update config.properties", e);
        }
    }
    public static String getBaseUrl() {
        return properties.getProperty("baseUrl");
    }
	}