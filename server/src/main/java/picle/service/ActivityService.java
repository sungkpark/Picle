package picle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import picle.dao.ActivityDao;
import picle.dao.ScoreDao;
import picle.entity.Activity;
import picle.entity.PostActivity;

import java.util.Collection;

/**
 * Business logic.
 */
@Service
public class ActivityService {

    /**
     * Uses the interface ActivityDao of which the used class is
     * defined in the @Qualifier.
     */
    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private ScoreDao scoreDao;

    public ActivityService(ActivityDao activityDao) {
        this.activityDao = activityDao;
    }

    public Collection<Activity> getAllActivities(String username) {
        return this.activityDao.getAllActivities(username);
    }

    public Activity getActivityById(int activityId, String username) {
        return this.activityDao.getActivityById(activityId, username);
    }

    public boolean deleteActivityById(int activityId) {
        return this.activityDao.deleteActivityById(activityId);
    }

    /**
     * Register an activity with the given user.
     * @param postActivity the PostActivity object with the information on the activity.
     * @param username the user to register the activity with.
     * @return true if success.
     */
    public boolean addActivity(PostActivity postActivity, String username) {
        int score = ScoreCalculator.calculateScore(
                postActivity.getActivityTypeId(),
                postActivity.getParam1(),
                postActivity.getParam2()
        );

        ScoreService service = new ScoreService(this.scoreDao);
        service.updateScoreByUsername(username, score);

        return this.activityDao.addActivity(
                postActivity,
                username,
                score);
    }

    /**
     * Update an already registered activity.
     * @param activityId the Id of the to be changed activity
     * @param postActivity new information of the activity
     * @param username the associated user
     * @return true if successful
     */
    public boolean updateActivityByActivityId(
            int activityId, PostActivity postActivity, String username) {
        return this.activityDao.updateActivityByActivityId(
                activityId,
                postActivity,
                username,
                ScoreCalculator.calculateScore(
                        postActivity.getActivityTypeId(),
                        postActivity.getParam1(),
                        postActivity.getParam2()));
    }
}

