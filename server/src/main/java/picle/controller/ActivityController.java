package picle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import picle.entity.Activity;
import picle.entity.ActivityList;
import picle.entity.PostActivity;
import picle.service.ActivityService;

import java.util.Collection;

/**
 * Handles HTTP methods.
 */
@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    /**
     * GET method to get all activities.
     * http://localhost:8080/activity/
     * @return a list of all activities
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ActivityList> getAllActivities(
            @RequestAttribute("X-Picle-Username") String username
    ) {
        Collection<Activity> allActivities = activityService.getAllActivities(username);
        if (allActivities == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ActivityList list = new ActivityList(allActivities);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * GET method to get a single activity entry.
     * http://localhost:8080/activity/{activityId}/
     * @param activityId identifies which activity entry to get
     * @return a single activity entry with activity id activityId
     */
    @RequestMapping(value = "/{activityId}", method = RequestMethod.GET)
    public ResponseEntity<Activity> getActivityById(
            @PathVariable("activityId") int activityId,
            @RequestAttribute("X-Picle-Username") String username) {

        Activity activity = activityService.getActivityById(activityId, username);
        if (activity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(activity, HttpStatus.OK);
    }

    /**
     * DELETE method to delete a single activity.
     * http://localhost:8080/activity/{activityId}
     * @param activityId identifies which activity entry to delete
     */
    @RequestMapping(value = "/{activityId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteActivityById(@PathVariable("activityId") int activityId) {
        boolean flag = activityService.deleteActivityById(activityId);
        if (!flag) {
            return new ResponseEntity<String>(
                    "This activity id does not exist",
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(
                "Activity ID " + activityId + " has been deleted",
                HttpStatus.OK);
    }

    /**
     * POST method to add a new activity entry.
     * http://localhost:8080/activity/
     * This method needs a JSON value passed on in the form of PostActivity.
     * Client passes in the params required which are the fields of PostActivity.
     * @param postActivity (activityTypeId, param1, param2)
     */
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addActivity(
            @RequestBody PostActivity postActivity,
            @RequestAttribute("X-Picle-Username") String username) {
        //boolean flag = activityService.addActivity(postActivity);
        if (!activityService.addActivity(postActivity, username)) {
            return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("This activity has been added", HttpStatus.CREATED);
    }

    /**
     * PUT method to update an activity entry.
     * http://localhost:8080/activity/{activityId}
     * This method needs a JSON value passed on in the form of PostActivity.
     * @param activityId identifies which activity entry to update
     * @param postActivity from client with updated values of the to be
     *                     updated activity entry
     */
    @RequestMapping(value = "/{activityId}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateActivityByActivityId(
            @PathVariable("activityId") int activityId,
            @RequestBody PostActivity postActivity,
            @RequestAttribute("X-Picle-Username") String username) {
        boolean flag = activityService.updateActivityByActivityId(
                activityId,
                postActivity,
                username);
        if (!flag) {
            return new ResponseEntity<>("This activity id doesn't exists",
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(activityId + " has been updated",
                HttpStatus.OK);
    }

}

