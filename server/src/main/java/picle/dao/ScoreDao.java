package picle.dao;

import picle.entity.Score;

import java.util.Collection;

public interface ScoreDao {

    /**
     * Gets all scores from the database.
     * @return All scores.
     */
    Collection<Score> getAllScores();

    /**
     * Get the score attribute for a given user.
     * @param username username of Score retrieved
     * @return Score attribute of the given user
     */
    Score getScoreByUsername(String username);

    /**
     * Updates the total_score and level for a given user.
     * @param username username of the user to be updated
     * @param score score that will be added to total_score
     *              (this is not the total score)
     * @param level level from total_score
     * @return boolean of success or fail
     */
    boolean updateScoreByUsername(String username, int score, int level);
}
