package picle.entity;

/**
 * This class is used just to POST and PUT methods to add
 * and update activity entries.
 */
public class PostActivity {

    private int activityTypeId;
    private int param1;
    private int param2;

    /**
     * The PostActivity class is a separate class for when the server receives a
     * new activity from the client. We're not using the Activity class for this,
     * because the client may not supply a score or ID.
     * @param activityTypeId The type id
     * @param param1 the first parameter
     * @param param2 the second parameter
     */
    public PostActivity(int activityTypeId, int param1, int param2) {
        this.activityTypeId = activityTypeId;
        this.param1 = param1;
        this.param2 = param2;
    }

    public PostActivity() { }

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
}

