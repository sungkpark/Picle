package picle.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ScoreList {

    private List<Score> scoreList;

    @JsonCreator
    public ScoreList(@JsonProperty("scores") List<Score> list) {
        this.scoreList = list;
    }

    public List<Score> getScoreList() {
        return scoreList;
    }

}
