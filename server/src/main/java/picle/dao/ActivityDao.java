package picle.dao;

import picle.entity.Activity;
import picle.entity.PostActivity;

import java.util.Collection;

/**
 * An interface to have one or more classes using these methods.
 * This is because of I am using two implementation of which
 * one is FakeActivityDaoImpl and the other is
 * MariaDbActivityDaoImpl.
 */
public interface ActivityDao {


    /**
     * Gets all activities recorded in the list of activities.
     * @return list of activities
     */
    Collection<Activity> getAllActivities(String username);

    /**
     * Gets a single activity identified by the activityId.
     * @param activityId the unique ID of the activity
     * @return a single activity with activity id 'activityId'
     */
    Activity getActivityById(int activityId, String username);

    /**
     * Deletes a single activity entry identified by activityId.
     * @param activityId the ID of the activity
     */
    boolean deleteActivityById(int activityId);

    /**
     * Adds an activity to the list of activities.
     * @param postActivity (activityTypeId, param1, param2)
     */
    boolean addActivity(PostActivity postActivity, String username, int score);

    /**
     * Updates a single activity identified with activityId with
     * the parameters postActivity.
     * @param activityId the ID of the activity
     * @param postActivity a PostActivity object describing the new values of the activity
     */
    boolean updateActivityByActivityId(
            int activityId, PostActivity postActivity, String username, int score);
}

