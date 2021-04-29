package picle.common;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 *  Singleton class for loading config.properties file.
 *  Eg. Configuration.getInstance().url to access url variable.
 */
public class Configuration {
    private static String configFile = "config.properties";
    private static Configuration ourInstance = new Configuration();

    public String url;
    public String username;
    public String password;

    /**
     * Method retrieves all properties from config file.
     */
    private Configuration() {

        Properties prop = new Properties();

        try {
            //load a properties file from class path, inside static method
            InputStream stream = getClass().getClassLoader().getResourceAsStream(configFile);
            prop.load(stream);

            //get the property values
            url = prop.getProperty("url");
            username = prop.getProperty("username");
            password = prop.getProperty("password");

        } catch (IOException ex) {
            System.out.println("Could not load config file");
        }

    }

    /**
     * Stores username and password as properties.
     * @param username Username that must be stored.
     * @param password Password that must be stored.
     */
    public void storeCredentials(String username, String password) {

        Properties prop = new Properties();

        try {
            //load a properties file from class path
            InputStream streamIn = getClass().getClassLoader().getResourceAsStream(configFile);
            prop.load(streamIn);

            //Set new properties
            prop.setProperty("username", username);
            prop.setProperty("password", password);

            //Store properties
            FileOutputStream streamOut = new FileOutputStream(
                    "client\\src\\main\\resources\\" + configFile);
            prop.store(streamOut, null);
            streamOut.close();

            //Update current instance to track properties
            this.username = username;
            this.password = password;

        } catch (IOException ex) {
            System.out.println("Could not save to file");
        }
    }


    /**
     * Singleton accessor method.
     */
    public static Configuration getInstance() {
        return ourInstance;
    }
}
