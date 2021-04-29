package picle.sql;

//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.Properties;

public class DatabaseController {

    private Connection conn;
    private String dbHost;
    private String dbUser;
    private String dbPass;
    private String databaseName;
    private String propertiesFileName = "database.properties";

    /**
     * Constructor loads config properties from file.
     * Then it opens a database connection.
     */
    public DatabaseController() {
        /*Properties prop = new Properties();
        try {
            InputStream input = new FileInputStream(propertiesFileName);
            prop.load(input);
        } catch (IOException e) {
            System.out.println(e.toString());
            return;
        }
*/
        this.dbHost = "localhost";
        this.dbUser = "root";
        this.dbPass = "password";
        this.databaseName = "picle";
        this.open();
    }

    /**
     * Getter for the database connection.
     * @return Current connection.
     */
    public Connection getConn() {
        return this.conn;
    }

    public String getDbHost() {
        return this.dbHost;
    }

    public String getDbUser() {
        return this.dbUser;
    }

    /**
     * Closes the connection with the database.
     * @return true if successful (no SQLException has occurred)
     */
    public boolean close() {
        try {
            this.conn.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }

        return true;
    }

    /**
     * Opens a connection with the database.
     * @return true if successful (no SQLException has occured)
     */
    public boolean open() {
        String hostUrl = "jdbc:mariadb://" + dbHost + ":3306/" + databaseName;
        System.out.println("Using database hostUrl: " + hostUrl);
        try {
            this.conn = DriverManager.getConnection(hostUrl, dbUser, dbPass);
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }

        return true;
    }
}
