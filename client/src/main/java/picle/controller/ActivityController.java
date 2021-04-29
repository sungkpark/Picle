package picle.controller;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import picle.common.ActivityErrorHandler;
import picle.common.BasicAuthInterceptor;
import picle.common.Configuration;
import picle.model.Activity;
import picle.model.ActivityList;
import picle.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the /auth api.
 */
public class ActivityController {

    private RestTemplate restTemplate;

    /**
     * Constructor sets up rest template to automatically authenticate for each request.
     */
    public ActivityController(User user) {
        BasicAuthInterceptor authInterceptor = new BasicAuthInterceptor(user);
        this.restTemplate = new RestTemplateBuilder()
                .additionalInterceptors(authInterceptor)
                .errorHandler(new ActivityErrorHandler())
                .build();
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Performs API GET call to /activity to get list of user activities.
     * Note that the BasicAuthInterceptor used in the constructor sets the username and password.
     */
    public List<Activity> getActivities() {
        String reqUrl = Configuration.getInstance().url + "/activity";
        ResponseEntity<ActivityList> res = restTemplate.getForEntity(
                reqUrl, ActivityList.class);

        List<Activity> activities = new ArrayList<>();
        if (res.getStatusCode() == HttpStatus.OK) {
            if (res.getBody() != null) {
                activities = res.getBody().getActivityList();
            }
        }

        return activities;
    }

    /**
     * Performs API GET call to /activity/{id} to get a single activity.
     * Note that the BasicAuthInterceptor used in the constructor sets the username and password.
     */
    public Activity getActivity(int id) {
        String reqUrl = String.format("%s/activity/%d", Configuration.getInstance().url, id);
        ResponseEntity<Activity> res = restTemplate.getForEntity(reqUrl, Activity.class);
        Activity activity = null;
        if (res.getStatusCode() == HttpStatus.OK) {
            if (res.getBody() != null) {
                activity = res.getBody();
            }
        }
        return activity;
    }


    /**
     * Performs API POST call to /activity to create a new activity
     * using type id, param0 and param1.
     * Note that the BasicAuthInterceptor used in the constructor
     * sets the username and password.
     */
    public boolean addActivity(Activity newActivity) {
        String reqUrl = String.format("%s/activity", Configuration.getInstance().url);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(newActivity.toJsonString() ,headers);

        ResponseEntity<String> res = restTemplate.postForEntity(reqUrl,
                entity, String.class);

        return res.getStatusCode() == HttpStatus.OK;
    }

    /**
     * Performs API PUT call to /activity/{id} to update an activity using id.
     * Note that the BasicAuthInterceptor used in the constructor sets the username and password.
     */
    public void updateActivity(Activity updated) {
        String reqUrl = String.format("%s/activity/%d",
                Configuration.getInstance().url, updated.getId());
        restTemplate.put(reqUrl, new HttpEntity<>(updated.toJsonString()));
    }

    /**
     * Performs API DELETE call to /activity/{id} to delete an activity using id.
     * Note that the BasicAuthInterceptor used in the constructor sets the username and password.
     */
    public void deleteActivity(int id) {
        String reqUrl = String.format("%s/activity/%d", Configuration.getInstance().url, id);
        restTemplate.delete(reqUrl);
    }
}
