package picle.controller;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;
import picle.common.ActivityErrorHandler;
import picle.common.BasicAuthInterceptor;
import picle.model.Activity;
import picle.model.User;

import java.util.ArrayList;
import java.util.List;

public class AchievementController {

    private RestTemplate restTemplate;

    /**
     * Constructor sets up rest template to automatically authenticate for each request.
     */
    public AchievementController(User user) {
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
     * This method calls activityController.getActivities to get an array of all activities
     * from one user and returns an array of with the activity type ids of the activity types
     * that the user has completed 10 or more times.
     */
    public ArrayList<Integer> getAchievements(User user) {
        ActivityController controller = new ActivityController(user);
        controller.setRestTemplate(this.restTemplate);
        ArrayList<Integer> achievementList = new ArrayList<>();
        int[] activityCounter = new int[7];
        List<Activity> listOfActivities = controller.getActivities();

        for (Activity item : listOfActivities) {
            switch (item.getType_id()) {
                case 1:
                    int counter1 = activityCounter[0] + 1;
                    activityCounter[0] = counter1;
                    break;
                case 2:
                    int counter2 = activityCounter[1] + 1;
                    activityCounter[1] = counter2;
                    break;
                case 3:
                    int counter3 = activityCounter[2] + 1;
                    activityCounter[2] = counter3;
                    break;
                case 4:
                    int counter4 = activityCounter[3] + 1;
                    activityCounter[3] = counter4;
                    break;
                case 5:
                    int counter5 = activityCounter[4] + 1;
                    activityCounter[4] = counter5;
                    break;
                case 6:
                    int counter6 = activityCounter[5] + 1;
                    activityCounter[5] = counter6;
                    break;
                default:
                    activityCounter[6] = activityCounter[6]++;
                    break;
            }
        }
        for (int i = 0; i < activityCounter.length; i++) {
            if (activityCounter[i] >= 10) {
                achievementList.add(i + 1);
            }
        }
        return achievementList;
    }
}
