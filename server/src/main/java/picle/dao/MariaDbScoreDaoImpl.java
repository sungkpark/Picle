package picle.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import picle.entity.Score;
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
@Qualifier("MariaDbScoreDaoImpl")
public class MariaDbScoreDaoImpl implements ScoreDao {

    private DatabaseControllerBuilder dcBuilder = new DatabaseControllerBuilder();

    /**
     * Queries database for all user names and scores.
     * Then it maps the result rows to Score objects.
     * @return Collection of Score objects.
     */
    @Override
    public Collection<Score> getAllScores() {
        DatabaseController dbController = dcBuilder.buildDatabaseController();
        Collection<Score> scores = new HashSet<>();
        try {
            Connection conn = dbController.getConn();
            Statement stmt = conn.createStatement();

            String scoreQuery = "SELECT username, total_score,"
                    + "level FROM users;";
            ResultSet rs = stmt.executeQuery(scoreQuery);
            while (rs.next()) {
                String username = rs.getString(1);
                int totalScore = rs.getInt(2);
                int level = rs.getInt(3);
                Score score = new Score(username, totalScore, level);
                scores.add(score);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        dbController.close();
        return scores;
    }

    @Override
    public Score getScoreByUsername(String username) {
        DatabaseController dbController = dcBuilder.buildDatabaseController();
        Score score = null;
        try {
            Connection conn = dbController.getConn();
            Statement stmt = conn.createStatement();

            String scoreQuery = "SELECT username, total_score,"
                    + "level FROM users "
                    + "WHERE username='" + username + "';";
            ResultSet rs = stmt.executeQuery(scoreQuery);

            if (rs.next()) {
                username = rs.getString(1);
                int totalScore = rs.getInt(2);
                int level = rs.getInt(3);
                score = new Score(username, totalScore, level);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        dbController.close();
        return score;
    }

    /**
     * Query for updating database with new score and level.
     * @param username username of the user to be updated
     * @param score score that will be added to total_score
     *              (this is not the total score,
     *              so it needs to be calculated here)
     * @param level level from total_score (this is already calculated)
     * @return true if successful
     */
    @Override
    public boolean updateScoreByUsername(String username, int score, int level) {
        DatabaseController dbController = dcBuilder.buildDatabaseController();

        Score newScore = getScoreByUsername(username);
        newScore.setScore(getScoreByUsername(username).getScore() + score);
        try {
            Connection conn = dbController.getConn();
            Statement stmt = conn.createStatement();

            String updateScoreQuery = "UPDATE users "
                    + "SET total_score=" + newScore.getScore()
                    + ", level=" + level
                    + " WHERE username='" + username + "'";
            stmt.executeUpdate(updateScoreQuery);
            dbController.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            dbController.close();
            return false;
        }
    }
}
