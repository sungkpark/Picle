package picle.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import picle.entity.Activity;
import picle.entity.PostActivity;
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
@Qualifier("MariaDbActivityDaoImpl")
public class MariaDbActivityDaoImpl implements ActivityDao {

    private DatabaseControllerBuilder dcBuilder = new DatabaseControllerBuilder();

    @Override
    public Collection<Activity> getAllActivities(String username) {
        DatabaseController dbController = dcBuilder.buildDatabaseController();
        Collection<Activity> activities = new HashSet<>();

        try {
            Connection conn = dbController.getConn();
            Statement stmt = conn.createStatement();

            String activitiesQuery = "SELECT * FROM activities WHERE user_id ="
                    + "(SELECT user_id FROM users WHERE username = '" + username + "')";
            ResultSet rs = stmt.executeQuery(activitiesQuery);

            while (rs.next()) {
                int activityId = rs.getInt(1);
                int activityTypeId = rs.getInt(3);
                int param1 = rs.getInt(4);
                int param2 = rs.getInt(5);
                int score = rs.getInt(6);

                activities.add(
                        new Activity(activityId, username, activityTypeId, param1, param2, score)
                );
            }
            dbController.close();
            return activities;

        } catch (SQLException e) {
            System.out.println(e.toString());
            dbController.close();
            return null;
        }
    }

    @Override
    public Activity getActivityById(int activityId, String username) {
        DatabaseController dbController = dcBuilder.buildDatabaseController();

        try {
            Connection conn = dbController.getConn();
            Statement stmt = conn.createStatement();
            String activityQuery = "SELECT * FROM activities WHERE activity_id = " + activityId
                    + " AND user_id = (SELECT user_id FROM users WHERE username = '"
                    + username + "')";
            System.out.println(activityQuery);
            ResultSet rsActivity = stmt.executeQuery(activityQuery);

            Activity result;
            if (rsActivity.first()) {
                int activityTypeId = rsActivity.getInt(3);
                int param1 = rsActivity.getInt(4);
                int param2 = rsActivity.getInt(5);
                int score = rsActivity.getInt(6);
                result = new Activity(activityId, username,
                            activityTypeId, param1, param2, score);
            } else {
                result =  null;
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
    public boolean deleteActivityById(int activityId) {
        DatabaseController dbController = dcBuilder.buildDatabaseController();

        try {
            Connection conn = dbController.getConn();
            Statement stmt = conn.createStatement();
            String deleteQuery = "DELETE FROM activities WHERE activity_id = " + activityId;
            int res = stmt.executeUpdate(deleteQuery);

            dbController.close();
            return res > 0;

        } catch (SQLException e) {
            System.out.println(e.toString());
            dbController.close();
            return false;
        }
    }

    @Override
    public boolean addActivity(PostActivity postActivity, String username, int score) {
        DatabaseController dbController = dcBuilder.buildDatabaseController();

        try {
            Connection conn = dbController.getConn();
            Statement stmt = conn.createStatement();
            String insertQuery =
                    "INSERT INTO activities (user_id,activity_type_id,param0,param1,score) VALUES ("
                    + "(SELECT user_id FROM users WHERE username = '" + username + "'),"
                    + postActivity.getActivityTypeId() + ","
                    + postActivity.getParam1() + ","
                    + postActivity.getParam2() + ","
                    + score + ")";
            stmt.executeUpdate(insertQuery);
            dbController.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            dbController.close();
            return false;
        }
    }

    // TODO when implementing, do not forget to cLoSe CoNnEcTiOn
    @Override
    public boolean updateActivityByActivityId(
            int activityId, PostActivity postActivity, String username, int score) {
        return false;
    }
}


