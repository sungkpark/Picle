package picle.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


/**
 * Model of the user which keeps track of the username and password.
 */
public class Activity {

    @JsonProperty("activityId") private int id;
    @JsonProperty("activityTypeId") private int typeId;
    @JsonProperty("param1") private int param0;
    @JsonProperty("param2") private int param1;
    @JsonProperty("score") private int score;

    /**
     * An entity class of one activity.
     * @param id the unique ID of the activity
     * @param typeId the type ID of the activity
     * @param param0 the first parameter
     * @param param1 the second parameter
     * @param score the score
     */
    public Activity(int id, int typeId, int param0, int param1, int score) {
        this.id = id;
        this.typeId = typeId;
        this.param0 = param0;
        this.param1 = param1;
        this.score = score;
    }

    public Activity(){}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType_id() {
        return typeId;
    }

    public void setType_id(int typeId) {
        this.typeId = typeId;
    }

    public int getParam0() {
        return param0;
    }

    public void setParam0(int param0) {
        this.param0 = param0;
    }

    public int getParam1() {
        return param1;
    }

    public void setParam1(int param1) {
        this.param1 = param1;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Maps the object to a JSON String.
     * @return JSON
     */
    public String toJsonString() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("activityId", this.id);
        node.put("activityTypeId", this.typeId);
        node.put("param1", this.param0);
        node.put("param2", this.param1);
        node.put("score", this.score);

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
        Activity activity = (Activity) obj;
        return getId() == activity.getId()
                && typeId == activity.typeId
                && getParam0() == activity.getParam0()
                && getParam1() == activity.getParam1()
                && getScore() == activity.getScore();
    }

}
