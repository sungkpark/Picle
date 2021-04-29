package picle.entity;

import java.util.Collection;

/**
 * Wrapper class for a list of scores for Json parsing purposes.
 */
public class ScoreList {

    /**
     * List of scores for all users.
     */
    Collection<Score> scores;

    /**
     * Constructor.
     * @param scores List of scores.
     */
    public ScoreList(Collection<Score> scores) {
        this.scores = scores;
    }

    public Collection<Score> getScores() {
        return scores;
    }

    public void setScores(Collection<Score> scores) {
        this.scores = scores;
    }
}
