package picle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import picle.dao.ScoreDao;
import picle.entity.Score;

import java.util.Collection;

/**
 * Business logic.
 */
@Service
public class ScoreService {

    /**
     * Uses the interface ScoreDao of which the used class is
     * defined in the @Qualifier.
     */
    @Autowired
    private ScoreDao scoreDao;

    public ScoreService(ScoreDao scoreDao) {
        this.scoreDao = scoreDao;
    }

    public Collection<Score> getAllScores() {
        return this.scoreDao.getAllScores();
    }

    public Score getScoreByUsername(String user) {
        return this.scoreDao.getScoreByUsername(user);
    }

    /**
     * Updates total_score and level of a user.
     * @param username username of the score to be updated
     * @param score the new score from a new activity to be added to total_score
     * @return boolean of whether the method is done in the database or not
     */
    public boolean updateScoreByUsername(String username, int score) {
        Score currentUserScore = getScoreByUsername(username);
        return this.scoreDao.updateScoreByUsername(
                username,
                score,
                LevelCalculator.calculateLevel(currentUserScore.getScore() + score));
    }
}
