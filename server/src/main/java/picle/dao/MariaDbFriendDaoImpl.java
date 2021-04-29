package picle.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import picle.entity.Friend;
import picle.sql.DatabaseController;
import picle.sql.DatabaseControllerBuilder;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;

@Repository
@Primary
@Qualifier("MariaDbFriendDaoImpl")
public class MariaDbFriendDaoImpl implements FriendDao {

    private DatabaseControllerBuilder dcBuilder = new DatabaseControllerBuilder();

    @Override
    public Collection<Friend> getAllFriends(String username) {
        DatabaseController dbController = dcBuilder.buildDatabaseController();
        Collection<Friend> friends = new HashSet<>();

        try {
            Connection conn = dbController.getConn();
            Statement stmt = conn.createStatement();

            String friendQuery = "SELECT u2.username AS friendname FROM friends\n"
                               + "JOIN users as u1 ON friends.user_id=u1.user_id\n"
                               + "JOIN users as u2 ON friends.friend_id=u2.user_id\n"
                               + "WHERE u1.username='" + username + "';";
            ResultSet rs = stmt.executeQuery(friendQuery);

            while (rs.next()) {
                String name = rs.getString(1);
                Friend friend = new Friend(name);
                friends.add(friend);
            }

        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        dbController.close();
        return friends;
    }

    @Override
    public Friend getFriendByUsername(String username, String friendUsername) {
        DatabaseController dbController = dcBuilder.buildDatabaseController();
        Friend friend = null;

        try {
            Connection conn = dbController.getConn();
            Statement stmt = conn.createStatement();

            String friendQuery = "SELECT u2.username AS friendname FROM friends\n"
                               + "JOIN users as u1 ON friends.user_id=u1.user_id\n"
                               + "JOIN users as u2 ON friends.friend_id=u2.user_id\n"
                               + "WHERE u1.username='" + username
                               + "' AND u2.username='" + friendUsername + "';";
            ResultSet rs = stmt.executeQuery(friendQuery);

            if (rs.first()) {
                String name = rs.getString(1);
                friend = new Friend(name);
            }

        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        dbController.close();
        return friend;
    }

    @Override
    public boolean deleteFriendByUsername(String username, String friendUsername) {
        DatabaseController dbController = dcBuilder.buildDatabaseController();
        boolean status = false;

        try {
            Connection conn = dbController.getConn();
            Statement stmt = conn.createStatement();

            String deleteQuery = "DELETE FROM friends "
                    + "WHERE (SELECT user_id FROM users WHERE username='"
                    + username + "')=user_id AND "
                    + "(SELECT user_id FROM users WHERE username='"
                    + friendUsername + "')=friend_id";
            int linesDeleted = stmt.executeUpdate(deleteQuery);
            status = linesDeleted > 0;

        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        dbController.close();
        return status;
    }

    @Override
    public boolean addFriend(String username, Friend friend) {
        DatabaseController dbController = dcBuilder.buildDatabaseController();
        boolean status = false;

        try {
            Connection conn = dbController.getConn();
            Statement stmt = conn.createStatement();

            String insertQuery = "INSERT INTO friends (user_id, friend_id) \n"
                               + "VALUES ((SELECT user_id FROM users \n"
                               + "WHERE username='" + username + "'),\n"
                               + "(SELECT user_id FROM users \n"
                               + "WHERE username='" + friend.getUsername() + "'));";
            stmt.executeUpdate(insertQuery);
            status = true;
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        dbController.close();
        return status;
    }
}


