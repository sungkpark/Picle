package picle.entity;

public class Activity {

    /**
     * activityId is a unique activity identifier which
     * is used to map one activity.
     */
    private int activityId;

    /**
     * the username of the user who owns this activity.
     */
    private String username;
    /**
     * is an integer value that identifies the description
     * of the activity.
     */
    private int activityTypeId;
    /**
     * parameter 1 for score calculation.
     */
    private int param1;
    /**
     * parameter 2 if necessary for score calculation.
     */
    private int param2;
    /**
     * score of the activity corresponding to the
     * activityTypeId, param1 and param2.
     */
    private int score;

    /**
     * Constructor.
     * @param activityId the ID of the activity
     * @param username the username of the user associated with the activity
     * @param activityTypeId the type of the activity
     * @param param1 the first parameter
     * @param param2 the second parameter
     * @param score the score
     */
    public Activity(
            int activityId,
            String username,
            int activityTypeId,
            int param1,
            int param2,
            int score
    ) {
        this.activityId = activityId;
        this.username = username;
        this.activityTypeId = activityTypeId;
        this.param1 = param1;
        this.param2 = param2;
        this.score = score;
    }

    /**
     * Constructor.
     * @param activityId the ID of the activity
     * @param activityTypeId the type of the activity
     * @param param1 the first parameter
     * @param param2 the second parameter
     * @param score the score
     */
    public Activity(int activityId, int activityTypeId, int param1, int param2, int score) {
        this.activityId = activityId;
        this.username = null;
        this.activityTypeId = activityTypeId;
        this.param1 = param1;
        this.param2 = param2;
        this.score = score;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getUsername() {
        return username;
    }

    public int getActivityTypeId() {
        return activityTypeId;
    }

    public void setActivityTypeId(int activityTypeId) {
        this.activityTypeId = activityTypeId;
    }

    public int getParam1() {
        return param1;
    }

    public void setParam1(int param1) {
        this.param1 = param1;
    }

    public int getParam2() {
        return param2;
    }

    public void setParam2(int param2) {
        this.param2 = param2;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
