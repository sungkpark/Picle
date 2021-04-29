package picle.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import picle.entity.RegisterUser;
import picle.entity.User;
import picle.sql.DatabaseController;
import picle.sql.DatabaseControllerBuilder;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
@Primary
@Qualifier("MariaDbUserDaoImpl")
public class MariaDbUserDaoImpl implements UserDao {

    private DatabaseControllerBuilder dcBuilder = new DatabaseControllerBuilder();

    @Override
    public User getUser(int userId) {
        try {
            DatabaseController dbController = dcBuilder.buildDatabaseController();
            Connection conn = dbController.getConn();
            Statement stmt = conn.createStatement();

            String userQuery = "SELECT * FROM users WHERE user_id = " + userId;
            ResultSet rs = stmt.executeQuery(userQuery);

            if (rs.first()) {
                String username = rs.getString(2);
                String pass = rs.getString(3);
                return new User(userId, username, pass);
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
            return null;
        }
    }

    @Override
    public User getUser(String username) {
        DatabaseController dbController = dcBuilder.buildDatabaseController();

        try {
            Connection conn = dbController.getConn();
            Statement stmt = conn.createStatement();

            String userQuery = "SELECT * FROM users "
                    + "WHERE username = '" + username + "'";
            ResultSet rs = stmt.executeQuery(userQuery);

            User result;
            if (rs.first()) {
                String pass = rs.getString(3);
                int userId = rs.getInt(1);

                result = new User(userId, username, pass);
            } else {
                return null;
            }
            dbController.close();
            return result;
        } catch (SQLException e) {
            System.out.println(e.toString());
            dbController.close();
            return null;
        }
    }

    @Override
    public boolean userExists(int userId) {
        return getUser(userId) != null;
    }

    @Override
    public boolean userExists(String username) {
        return getUser(username) != null;
    }

    @Override
    public boolean deleteUser(int userId) {
        return false;
    }

    @Override
    public boolean deleteUser(String username) {
        return false;
    }

    @Override
    public boolean changeUsername(int userId, String newUsername) {
        return false;
    }

    @Override
    public boolean register(RegisterUser registerUser) {
        DatabaseController dbController = dcBuilder.buildDatabaseController();

        try {
            Connection conn = dbController.getConn();
            Statement stmt = conn.createStatement();

            if (userExists(registerUser.getUsername())) {
                dbController.close();
                return false;
            } else {
                String registerQuery = "INSERT INTO users "
                        + "( username, hashed_password, total_score, level) "
                        + "VALUES ('"
                        + registerUser.getUsername() + "', '"
                        + registerUser.getPassword() + "', 0, 0);";
                stmt.execute(registerQuery);
                dbController.close();
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
            dbController.close();
            return false;
        }
    }
}
