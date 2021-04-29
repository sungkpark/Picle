package picle.entity;

import java.util.Collection;

public class ActivityList {

    private Collection<Activity> activities;

    public ActivityList(Collection<Activity> activities) {
        this.activities = activities;
    }

    public Collection<Activity> getActivities() {
        return activities;
    }

    public void setActivities(Collection<Activity> activities) {
        this.activities = activities;
    }

}
