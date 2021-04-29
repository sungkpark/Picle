package picle.entity;

public class Score {

    /**
     * Username corresponding to score.
     */
    private String username;

    /**
     * Score corresponding to the username.
     */
    private int score;

    /**
     * Level corresponding to the score.
     */
    private int level;

    /**
     * Constructor for the score entity.
     * @param username Username of the user.
     * @param score Score of the user.
     */
    public Score(String username, int score, int level) {
        this.username = username;
        this.score = score;
        this.level = level;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
