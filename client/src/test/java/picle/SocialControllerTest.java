package picle;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import picle.common.*;
import picle.controller.SocialController;
import picle.model.Friend;
import picle.model.User;

import java.util.List;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

public class SocialControllerTest {

    private final String username = "johndoe";
    private final String password = "password";

    private final Friend friend = new Friend("harry");
    private final String friendListResponse = String.format("{\"friends\":[%s, %s]}", friend.toJsonString(), friend.toJsonString());

    @Test
    public void restTemplateInterceptorTest() {
        User user = new User(username, password);
        SocialController controller = new SocialController(user);

        RestTemplate rt = controller.getRestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = rt.getInterceptors();
        ClientHttpRequestInterceptor interceptor = interceptors.get(interceptors.size() -1);

        Assert.assertTrue(interceptor instanceof BasicAuthInterceptor);
    }

    @Test
    public void getFriendsSuccessTest(){
        User user = new User(username, password);
        BasicAuthInterceptor interceptor = new BasicAuthInterceptor(user);
        RestTemplate restTemplate = new RestTemplateBuilder().additionalInterceptors(interceptor).build();

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        String reqUrl = String.format("%s/social", Configuration.getInstance().url);
        mockServer.expect(requestTo(reqUrl))
                .andExpect(method(GET))
                .andExpect(header("Authorization", "Basic " + user.toBase64()))
                .andRespond(withStatus(OK).contentType(MediaType.APPLICATION_JSON).body(friendListResponse));

        SocialController controller = new SocialController(user);
        controller.setRestTemplate(restTemplate);
        List<Friend> friends = controller.getFriends();

        mockServer.verify();
        Assert.assertEquals(friend, friends.get(1));
    }

    @Test
    public void getFriendsFailingTest(){
        User user = new User(username, password);
        BasicAuthInterceptor interceptor = new BasicAuthInterceptor(user);
        RestTemplate restTemplate = new RestTemplateBuilder().additionalInterceptors(interceptor).build();

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        String reqUrl = String.format("%s/social", Configuration.getInstance().url);
        mockServer.expect(requestTo(reqUrl))
                .andExpect(method(GET))
                .andExpect(header("Authorization", "Basic " + user.toBase64()))
                .andRespond(withStatus(ACCEPTED));

        SocialController controller = new SocialController(user);
        controller.setRestTemplate(restTemplate);
        List<Friend> friends = controller.getFriends();

        mockServer.verify();
        Assert.assertTrue(friends.isEmpty());
    }

    @Test
    public void getFriendsEmptyListTest(){
        String friendListResponse1 = "";
        User user = new User(username, password);
        BasicAuthInterceptor interceptor = new BasicAuthInterceptor(user);
        RestTemplate restTemplate = new RestTemplateBuilder().additionalInterceptors(interceptor).build();

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        String reqUrl = String.format("%s/social", Configuration.getInstance().url);
        mockServer.expect(requestTo(reqUrl))
                .andExpect(method(GET))
                .andExpect(header("Authorization", "Basic " + user.toBase64()))
                .andRespond(withStatus(OK).contentType(MediaType.APPLICATION_JSON).body(friendListResponse1));

        SocialController controller = new SocialController(user);
        controller.setRestTemplate(restTemplate);
        List<Friend> friends = controller.getFriends();

        mockServer.verify();
        Assert.assertTrue(friends.isEmpty());
    }

    @Test(expected = HttpClientErrorException.class)
    public void getFriendsFailureTest(){
        User user = new User(username, password);
        BasicAuthInterceptor interceptor = new BasicAuthInterceptor(user);
        RestTemplate restTemplate = new RestTemplateBuilder().additionalInterceptors(interceptor).build();

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        String reqUrl = String.format("%s/social", Configuration.getInstance().url);
        mockServer.expect(requestTo(reqUrl))
                .andExpect(method(GET))
                .andExpect(header("Authorization", "Basic " + user.toBase64()))
                .andRespond(withStatus(NOT_FOUND));

        SocialController controller = new SocialController(user);
        controller.setRestTemplate(restTemplate);
        List<Friend> friends = controller.getFriends();

        mockServer.verify();
        Assert.assertTrue(friends.isEmpty());
    }

    @Test
    public void getFriendSuccessTest(){
        User user = new User(username, password);
        BasicAuthInterceptor interceptor = new BasicAuthInterceptor(user);
        RestTemplate restTemplate = new RestTemplateBuilder()
                .errorHandler(new SocialErrorHandler())
                .additionalInterceptors(interceptor).build();

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        String reqUrl = String.format("%s/social/%s", Configuration.getInstance().url, friend.getUsername());
        mockServer.expect(requestTo(reqUrl))
                .andExpect(method(GET))
                .andExpect(header("Authorization", "Basic " + user.toBase64()))
                .andRespond(withStatus(OK).contentType(MediaType.APPLICATION_JSON).body(friend.toJsonString()));

        SocialController controller = new SocialController(user);
        controller.setRestTemplate(restTemplate);
        Friend result = controller.getFriend(friend.getUsername());

        mockServer.verify();
        Assert.assertEquals(friend, result);
    }

    @Test
    public void getFriend1FailingTest(){
        User user = new User(username, password);
        BasicAuthInterceptor interceptor = new BasicAuthInterceptor(user);
        RestTemplate restTemplate = new RestTemplateBuilder()
                .errorHandler(new SocialErrorHandler())
                .additionalInterceptors(interceptor).build();

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        String reqUrl = String.format("%s/social/%s", Configuration.getInstance().url, friend.getUsername());
        mockServer.expect(requestTo(reqUrl))
                .andExpect(method(GET))
                .andExpect(header("Authorization", "Basic " + user.toBase64()))
                .andRespond(withStatus(ACCEPTED));

        SocialController controller = new SocialController(user);
        controller.setRestTemplate(restTemplate);
        Friend result = controller.getFriend(friend.getUsername());

        mockServer.verify();
        Assert.assertEquals(result , null);
    }

    @Test
    public void getFriendEmptyTest(){
        String friend1 = "";
        User user = new User(username, password);
        BasicAuthInterceptor interceptor = new BasicAuthInterceptor(user);
        RestTemplate restTemplate = new RestTemplateBuilder()
                .errorHandler(new SocialErrorHandler())
                .additionalInterceptors(interceptor).build();

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        String reqUrl = String.format("%s/social/%s", Configuration.getInstance().url, friend.getUsername());
        mockServer.expect(requestTo(reqUrl))
                .andExpect(method(GET))
                .andExpect(header("Authorization", "Basic " + user.toBase64()))
                .andRespond(withStatus(OK).contentType(MediaType.APPLICATION_JSON).body(friend1));

        SocialController controller = new SocialController(user);
        controller.setRestTemplate(restTemplate);
        Friend result = controller.getFriend(friend.getUsername());

        mockServer.verify();
        Assert.assertEquals(result, null);
    }

    @Test(expected = HttpClientErrorException.class)
    public void getFriendFailureTest(){
        User user = new User(username, password);
        BasicAuthInterceptor interceptor = new BasicAuthInterceptor(user);
        RestTemplate restTemplate = new RestTemplateBuilder().additionalInterceptors(interceptor).build();

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        String reqUrl = String.format("%s/social/%s", Configuration.getInstance().url, friend.getUsername());
        mockServer.expect(requestTo(reqUrl))
                .andExpect(method(GET))
                .andExpect(header("Authorization", "Basic " + user.toBase64()))
                .andRespond(withStatus(NOT_FOUND));

        SocialController controller = new SocialController(user);
        controller.setRestTemplate(restTemplate);
        Friend result = controller.getFriend(friend.getUsername());

        mockServer.verify();
        Assert.assertEquals(null, result);
    }

    @Test(expected = CustomIoException.class)
    public void getFriendErrorHandlerSuccessTest(){
        User user = new User(username, password);
        BasicAuthInterceptor interceptor = new BasicAuthInterceptor(user);
        RestTemplate restTemplate = new RestTemplateBuilder()
                .errorHandler(new SocialErrorHandler())
                .additionalInterceptors(interceptor).build();

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        String reqUrl = String.format("%s/social/%s", Configuration.getInstance().url, friend.getUsername());
        mockServer.expect(requestTo(reqUrl))
                .andExpect(method(GET))
                .andExpect(header("Authorization", "Basic " + user.toBase64()))
                .andRespond(withStatus(NOT_FOUND));

        SocialController controller = new SocialController(user);
        controller.setRestTemplate(restTemplate);
        Friend result = controller.getFriend(friend.getUsername());

        mockServer.verify();
        Assert.assertEquals(null, result);
    }

    @Test(expected = HttpClientErrorException.class)
    public void getFriendErrorHandlerFailureTest(){
        User user = new User(username, password);
        BasicAuthInterceptor interceptor = new BasicAuthInterceptor(user);
        RestTemplate restTemplate = new RestTemplateBuilder()
                .errorHandler(new SocialErrorHandler())
                .additionalInterceptors(interceptor).build();

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        String reqUrl = String.format("%s/social/%s", Configuration.getInstance().url, friend.getUsername());
        mockServer.expect(requestTo(reqUrl))
                .andExpect(method(GET))
                .andExpect(header("Authorization", "Basic " + user.toBase64()))
                .andRespond(withStatus(FORBIDDEN));

        SocialController controller = new SocialController(user);
        controller.setRestTemplate(restTemplate);
        Friend result = controller.getFriend(friend.getUsername());

        mockServer.verify();
        Assert.assertEquals(null, result);
    }


    @Test
    public void addFriendTest(){
        User user = new User(username, password);
        BasicAuthInterceptor interceptor = new BasicAuthInterceptor(user);
        RestTemplate restTemplate = new RestTemplateBuilder().additionalInterceptors(interceptor).build();

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        String reqUrl = String.format("%s/social", Configuration.getInstance().url);
        mockServer.expect(requestTo(reqUrl))
                .andExpect(method(POST))
                .andExpect(header("Authorization", "Basic " + user.toBase64()))
                .andExpect(content().string(friend.toJsonString()))
                .andRespond(withStatus(CREATED));

        SocialController controller = new SocialController(user);
        controller.setRestTemplate(restTemplate);
        controller.addFriend(friend);

        mockServer.verify();
    }

    @Test
    public void addFriendFalingTest(){
        User user = new User(username, password);
        BasicAuthInterceptor interceptor = new BasicAuthInterceptor(user);
        RestTemplate restTemplate = new RestTemplateBuilder().additionalInterceptors(interceptor).build();

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        String reqUrl = String.format("%s/social", Configuration.getInstance().url);
        mockServer.expect(requestTo(reqUrl))
                .andExpect(method(POST))
                .andExpect(header("Authorization", "Basic " + user.toBase64()))
                .andExpect(content().string(friend.toJsonString()))
                .andRespond(withStatus(ACCEPTED));

        SocialController controller = new SocialController(user);
        controller.setRestTemplate(restTemplate);
        boolean TestResult = controller.addFriend(friend);

        mockServer.verify();
        Assert.assertFalse(TestResult);
    }

    @Test
    public void deleteFriendTest(){
        User user = new User(username, password);
        BasicAuthInterceptor interceptor = new BasicAuthInterceptor(user);
        RestTemplate restTemplate = new RestTemplateBuilder().additionalInterceptors(interceptor).build();

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        String reqUrl = String.format("%s/social/%s", Configuration.getInstance().url, friend.getUsername());
        mockServer.expect(requestTo(reqUrl))
                .andExpect(method(DELETE))
                .andExpect(header("Authorization", "Basic " + user.toBase64()))
                .andRespond(withStatus(OK));

        SocialController controller = new SocialController(user);
        controller.setRestTemplate(restTemplate);
        controller.deleteFriend(friend.getUsername());

        mockServer.verify();
    }

}
