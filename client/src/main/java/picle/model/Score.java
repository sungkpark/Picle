package picle.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


/**
 * Model of the score which keeps track of score and corresponding user.
 */
public class Score {

    @JsonProperty ("username") private String username;
    @JsonProperty("score") private double score;
    @JsonProperty("level") private int level;

    /**
     * Constructor.
     * @param username Username of the user.
     * @param score Score corresponding to that user.
     * @param level Level corresponding to that user.
     */
    public Score(String username, double score, int level) {
        this.username = username;
        this.score = score;
        this.level = level;
    }

    public Score(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getScore() {
        return this.score / 1000;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Maps the object to a JSON String.
     * @return JSON
     */
    public String toJsonString() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("username", this.username);
        node.put("score", this.score);
        node.put("level", this.level);

        return node.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Score score1 = (Score) obj;
        return getScore() == score1.getScore()
                && getLevel() == score1.getLevel()
                && getUsername().equals(score1.getUsername());
    }

}
