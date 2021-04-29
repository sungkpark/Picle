package picle.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ActivityList {

    private List<Activity> activityList;

    @JsonCreator
    public ActivityList(@JsonProperty("activities") List<Activity> list) {
        this.activityList = list;
    }

    public List<Activity> getActivityList() {
        return activityList;
    }

}
