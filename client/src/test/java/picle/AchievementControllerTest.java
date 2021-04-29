package picle;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import picle.common.BasicAuthInterceptor;
import picle.common.Configuration;
import picle.controller.AchievementController;
import picle.model.Activity;
import picle.model.User;

import java.util.List;
import java.util.ArrayList;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

public class AchievementControllerTest {

    private final String username = "johndoe";
    private final String password = "password";

    private final Activity activity0 = new Activity(1, 0, 10, 10, 100);
    private final Activity activity1 = new Activity(1, 1, 10, 10, 100);
    private final Activity activity2 = new Activity(1, 2, 10, 10, 100);
    private final Activity activity3 = new Activity(1, 3, 10, 10, 100);
    private final Activity activity4 = new Activity(1, 4, 10, 10, 100);
    private final Activity activity5 = new Activity(1, 5, 10, 10, 100);
    private final Activity activity6 = new Activity(1, 6, 10, 10, 100);
    private final ArrayList<String> listOfActivities = new ArrayList<>();



    @Test
    public void restTemplateInterceptorTest() {
        User user = new User(username, password);
        AchievementController controller = new AchievementController(user);

        RestTemplate rt = controller.getRestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = rt.getInterceptors();
        ClientHttpRequestInterceptor interceptor = interceptors.get(interceptors.size() -1);

        Assert.assertTrue(interceptor instanceof BasicAuthInterceptor);
    }


    @Test
    public void getAchievementsNoAchievementTest(){
        User user = new User(username, password);
        listOfActivities.add(activity0.toJsonString());
        listOfActivities.add(activity1.toJsonString());
        listOfActivities.add(activity2.toJsonString());
        listOfActivities.add(activity3.toJsonString());
        listOfActivities.add(activity4.toJsonString());
        listOfActivities.add(activity5.toJsonString());
        listOfActivities.add(activity6.toJsonString());
        String activityListResponse = String.format("{\"activities\": %s}", listOfActivities);
        BasicAuthInterceptor interceptor = new BasicAuthInterceptor(user);
        RestTemplate restTemplate = new RestTemplateBuilder().additionalInterceptors(interceptor).build();

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        String reqUrl = String.format("%s/activity", Configuration.getInstance().url);
        mockServer.expect(requestTo(reqUrl))
                .andExpect(method(GET))
                .andExpect(header("Authorization", "Basic " + user.toBase64()))
                .andRespond(withStatus(OK).contentType(MediaType.APPLICATION_JSON).body(activityListResponse));

        AchievementController achievementController1 = new AchievementController(user);
        achievementController1.setRestTemplate(restTemplate);

        ArrayList<Integer> resultList = achievementController1.getAchievements(user);

        Assert.assertTrue(resultList.isEmpty());
    }


    @Test
    public void getAchievementsAchievementSuccesTest(){
        User user = new User(username, password);
        listOfActivities.add(activity1.toJsonString());
        listOfActivities.add(activity1.toJsonString());
        listOfActivities.add(activity1.toJsonString());
        listOfActivities.add(activity1.toJsonString());
        listOfActivities.add(activity1.toJsonString());
        listOfActivities.add(activity1.toJsonString());
        listOfActivities.add(activity1.toJsonString());
        listOfActivities.add(activity1.toJsonString());
        listOfActivities.add(activity1.toJsonString());
        listOfActivities.add(activity1.toJsonString());
        String activityListResponse = String.format("{\"activities\": %s}", listOfActivities);
        BasicAuthInterceptor interceptor = new BasicAuthInterceptor(user);
        RestTemplate restTemplate = new RestTemplateBuilder().additionalInterceptors(interceptor).build();

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        String reqUrl = String.format("%s/activity", Configuration.getInstance().url);
        mockServer.expect(requestTo(reqUrl))
                .andExpect(method(GET))
                .andExpect(header("Authorization", "Basic " + user.toBase64()))
                .andRespond(withStatus(OK).contentType(MediaType.APPLICATION_JSON).body(activityListResponse));

        AchievementController achievementController1 = new AchievementController(user);
        achievementController1.setRestTemplate(restTemplate);

        ArrayList<Integer> resultList = achievementController1.getAchievements(user);

        Assert.assertFalse(resultList.isEmpty());
    }





}
