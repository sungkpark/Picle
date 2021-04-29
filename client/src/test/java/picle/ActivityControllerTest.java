package picle;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import picle.common.ActivityErrorHandler;
import picle.common.BasicAuthInterceptor;
import picle.common.Configuration;
import picle.common.CustomIoException;
import picle.controller.ActivityController;
import picle.model.Activity;
import picle.model.User;

import java.util.List;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

public class ActivityControllerTest {

    private final String username = "johndoe";
    private final String password = "password";

    private final Activity activity = new Activity(1, 1, 10, 10, 100);
    private final String activityListResponse = String.format("{\"activities\":[%s, %s]}", activity.toJsonString(), activity.toJsonString());

    @Test
    public void restTemplateInterceptorTest() {
        User user = new User(username, password);
        ActivityController controller = new ActivityController(user);

        RestTemplate rt = controller.getRestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = rt.getInterceptors();
        ClientHttpRequestInterceptor interceptor = interceptors.get(interceptors.size() -1);

        Assert.assertTrue(interceptor instanceof BasicAuthInterceptor);
    }

    @Test
    public void getActivitiesSuccessTest(){
        User user = new User(username, password);
        BasicAuthInterceptor interceptor = new BasicAuthInterceptor(user);
        RestTemplate restTemplate = new RestTemplateBuilder().additionalInterceptors(interceptor).build();

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        String reqUrl = String.format("%s/activity", Configuration.getInstance().url);
        mockServer.expect(requestTo(reqUrl))
                .andExpect(method(GET))
                .andExpect(header("Authorization", "Basic " + user.toBase64()))
                .andRespond(withStatus(OK).contentType(MediaType.APPLICATION_JSON).body(activityListResponse));

        ActivityController ac = new ActivityController(user);
        ac.setRestTemplate(restTemplate);
        List<Activity> activities = ac.getActivities();

        mockServer.verify();
        Assert.assertEquals(activity, activities.get(1));
    }

    @Test
    public void getActivitiesFailingTest(){
        User user = new User(username, password);
        BasicAuthInterceptor interceptor = new BasicAuthInterceptor(user);
        RestTemplate restTemplate = new RestTemplateBuilder().additionalInterceptors(interceptor).build();

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        String reqUrl = String.format("%s/activity", Configuration.getInstance().url);
        mockServer.expect(requestTo(reqUrl))
                .andExpect(method(GET))
                .andExpect(header("Authorization", "Basic " + user.toBase64()))
                .andRespond(withStatus(ACCEPTED));

        ActivityController ac = new ActivityController(user);
        ac.setRestTemplate(restTemplate);
        List<Activity> activities = ac.getActivities();

        mockServer.verify();
        Assert.assertTrue(activities.isEmpty());
    }

    @Test
    public void getActivitiesEmptyActivitiesTest(){
        String emptyActivities = "";
        User user = new User(username, password);
        BasicAuthInterceptor interceptor = new BasicAuthInterceptor(user);
        RestTemplate restTemplate = new RestTemplateBuilder().additionalInterceptors(interceptor).build();

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        String reqUrl = String.format("%s/activity", Configuration.getInstance().url);
        mockServer.expect(requestTo(reqUrl))
                .andExpect(method(GET))
                .andExpect(header("Authorization", "Basic " + user.toBase64()))
                .andRespond(withStatus(OK).contentType(MediaType.APPLICATION_JSON).body(emptyActivities));

        ActivityController ac = new ActivityController(user);
        ac.setRestTemplate(restTemplate);
        List<Activity> activities = ac.getActivities();

        mockServer.verify();
        Assert.assertTrue(activities.isEmpty());
    }

    @Test(expected = HttpClientErrorException.class)
    public void getActivitiesFailureTest(){
        User user = new User(username, password);
        BasicAuthInterceptor interceptor = new BasicAuthInterceptor(user);
        RestTemplate restTemplate = new RestTemplateBuilder().additionalInterceptors(interceptor).build();

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        String reqUrl = String.format("%s/activity", Configuration.getInstance().url);
        mockServer.expect(requestTo(reqUrl))
                .andExpect(method(GET))
                .andExpect(header("Authorization", "Basic " + user.toBase64()))
                .andRespond(withStatus(NOT_FOUND));

        ActivityController ac = new ActivityController(user);
        ac.setRestTemplate(restTemplate);
        List<Activity> activities = ac.getActivities();

        mockServer.verify();
        Assert.assertTrue(activities.isEmpty());
    }

    @Test
    public void getActivitySuccessTest(){
        User user = new User(username, password);
        BasicAuthInterceptor interceptor = new BasicAuthInterceptor(user);
        RestTemplate restTemplate = new RestTemplateBuilder()
                .errorHandler(new ActivityErrorHandler())
                .additionalInterceptors(interceptor).build();

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        String reqUrl = String.format("%s/activity/%d", Configuration.getInstance().url, 1);
        mockServer.expect(requestTo(reqUrl))
                .andExpect(method(GET))
                .andExpect(header("Authorization", "Basic " + user.toBase64()))
                .andRespond(withStatus(OK).contentType(MediaType.APPLICATION_JSON).body(activity.toJsonString()));

        ActivityController ac = new ActivityController(user);
        ac.setRestTemplate(restTemplate);
        Activity result = ac.getActivity(1);

        mockServer.verify();
        Assert.assertEquals(activity, result);
    }

    @Test
    public void getActivityFailingTest(){
        Activity emptyActivity = new Activity();
        User user = new User(username, password);
        BasicAuthInterceptor interceptor = new BasicAuthInterceptor(user);
        RestTemplate restTemplate = new RestTemplateBuilder()
                .errorHandler(new ActivityErrorHandler())
                .additionalInterceptors(interceptor).build();

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        String reqUrl = String.format("%s/activity/%d", Configuration.getInstance().url, 1);
        mockServer.expect(requestTo(reqUrl))
                .andExpect(method(GET))
                .andExpect(header("Authorization", "Basic " + user.toBase64()))
                .andRespond(withStatus(ACCEPTED));

        ActivityController ac = new ActivityController(user);
        ac.setRestTemplate(restTemplate);
        Activity result = ac.getActivity(1);

        mockServer.verify();
        Assert.assertNull(result);
    }

    @Test
    public void getActivityEmptyTest(){
        String emptyActivityString = "";
        Activity emptyActivity = new Activity();
        User user = new User(username, password);
        BasicAuthInterceptor interceptor = new BasicAuthInterceptor(user);
        RestTemplate restTemplate = new RestTemplateBuilder()
                .errorHandler(new ActivityErrorHandler())
                .additionalInterceptors(interceptor).build();

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        String reqUrl = String.format("%s/activity/%d", Configuration.getInstance().url, 1);
        mockServer.expect(requestTo(reqUrl))
                .andExpect(method(GET))
                .andExpect(header("Authorization", "Basic " + user.toBase64()))
                .andRespond(withStatus(OK).contentType(MediaType.APPLICATION_JSON).body(emptyActivityString));

        ActivityController ac = new ActivityController(user);
        ac.setRestTemplate(restTemplate);
        Activity result = ac.getActivity(1);

        mockServer.verify();
        Assert.assertNull(result);
    }

    @Test(expected = HttpClientErrorException.class)
    public void getActivityFailureTest(){
        User user = new User(username, password);
        BasicAuthInterceptor interceptor = new BasicAuthInterceptor(user);
        RestTemplate restTemplate = new RestTemplateBuilder().additionalInterceptors(interceptor).build();

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        String reqUrl = String.format("%s/activity/%d", Configuration.getInstance().url, 1);
        mockServer.expect(requestTo(reqUrl))
                .andExpect(method(GET))
                .andExpect(header("Authorization", "Basic " + user.toBase64()))
                .andRespond(withStatus(NOT_FOUND));

        ActivityController ac = new ActivityController(user);
        ac.setRestTemplate(restTemplate);
        Activity result = ac.getActivity(1);

        mockServer.verify();
        Assert.assertEquals(null, result);
    }

    @Test(expected = CustomIoException.class)
    public void getActivityErrorHandlerSuccessTest(){
        User user = new User(username, password);
        BasicAuthInterceptor interceptor = new BasicAuthInterceptor(user);
        RestTemplate restTemplate = new RestTemplateBuilder()
                .errorHandler(new ActivityErrorHandler())
                .additionalInterceptors(interceptor).build();

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        String reqUrl = String.format("%s/activity/%d", Configuration.getInstance().url, 1);
        mockServer.expect(requestTo(reqUrl))
                .andExpect(method(GET))
                .andExpect(header("Authorization", "Basic " + user.toBase64()))
                .andRespond(withStatus(NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("{\"type\": \"404\", \"message\": \"activity not found\"}"));

        ActivityController ac = new ActivityController(user);
        ac.setRestTemplate(restTemplate);
        Activity result = ac.getActivity(1);

        mockServer.verify();
        Assert.assertEquals(null, result);
    }

    @Test(expected = HttpClientErrorException.class)
    public void getActivityErrorHandlerFailureTest(){
        User user = new User(username, password);
        BasicAuthInterceptor interceptor = new BasicAuthInterceptor(user);
        RestTemplate restTemplate = new RestTemplateBuilder()
                .errorHandler(new ActivityErrorHandler())
                .additionalInterceptors(interceptor).build();

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        String reqUrl = String.format("%s/activity/%d", Configuration.getInstance().url, 1);
        mockServer.expect(requestTo(reqUrl))
                .andExpect(method(GET))
                .andExpect(header("Authorization", "Basic " + user.toBase64()))
                .andRespond(withStatus(BAD_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON));

        ActivityController ac = new ActivityController(user);
        ac.setRestTemplate(restTemplate);
        Activity result = ac.getActivity(1);

        mockServer.verify();
        Assert.assertEquals(null, result);
    }

    @Test
    public void addActivitySuccessTest(){
        User user = new User(username, password);
        BasicAuthInterceptor interceptor = new BasicAuthInterceptor(user);
        RestTemplate restTemplate = new RestTemplateBuilder().additionalInterceptors(interceptor).build();

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        String reqUrl = String.format("%s/activity", Configuration.getInstance().url);
        mockServer.expect(requestTo(reqUrl))
                .andExpect(method(POST))
                .andExpect(header("Authorization", "Basic " + user.toBase64()))
                .andExpect(content().string(activity.toJsonString()))
                .andRespond(withStatus(OK));

        ActivityController ac = new ActivityController(user);
        ac.setRestTemplate(restTemplate);
        ac.addActivity(activity);

        mockServer.verify();
    }

    @Test
    public void addActivityFailingTest(){
        User user = new User(username, password);
        BasicAuthInterceptor interceptor = new BasicAuthInterceptor(user);
        RestTemplate restTemplate = new RestTemplateBuilder().additionalInterceptors(interceptor).build();

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        String reqUrl = String.format("%s/activity", Configuration.getInstance().url);
        mockServer.expect(requestTo(reqUrl))
                .andExpect(method(POST))
                .andExpect(header("Authorization", "Basic " + user.toBase64()))
                .andExpect(content().string(activity.toJsonString()))
                .andRespond(withStatus(ACCEPTED));

        ActivityController ac = new ActivityController(user);
        ac.setRestTemplate(restTemplate);
        boolean TestResult = ac.addActivity(activity);

        mockServer.verify();
        Assert.assertFalse(TestResult);
    }

    @Test
    public void updateActivityTest(){
        User user = new User(username, password);
        BasicAuthInterceptor interceptor = new BasicAuthInterceptor(user);
        RestTemplate restTemplate = new RestTemplateBuilder().additionalInterceptors(interceptor).build();

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        String reqUrl = String.format("%s/activity/%d", Configuration.getInstance().url, activity.getId());
        mockServer.expect(requestTo(reqUrl))
                .andExpect(method(PUT))
                .andExpect(header("Authorization", "Basic " + user.toBase64()))
                .andExpect(content().string(activity.toJsonString()))
                .andRespond(withStatus(OK));

        ActivityController ac = new ActivityController(user);
        ac.setRestTemplate(restTemplate);
        ac.updateActivity(activity);

        mockServer.verify();
    }

    @Test
    public void deleteActivityTest(){
        User user = new User(username, password);
        BasicAuthInterceptor interceptor = new BasicAuthInterceptor(user);
        RestTemplate restTemplate = new RestTemplateBuilder().additionalInterceptors(interceptor).build();

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        String reqUrl = String.format("%s/activity/%d", Configuration.getInstance().url, 1);
        mockServer.expect(requestTo(reqUrl))
                .andExpect(method(DELETE))
                .andExpect(header("Authorization", "Basic " + user.toBase64()))
                .andRespond(withStatus(OK));

        ActivityController ac = new ActivityController(user);
        ac.setRestTemplate(restTemplate);
        ac.deleteActivity(1);

        mockServer.verify();
    }



}
