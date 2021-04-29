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
import picle.controller.ScoreController;
import picle.model.Score;
import picle.model.User;

import java.util.List;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

public class ScoreControllerTest {


    private final Score score1 = new Score("john", 10, 1);

    private final Score score2 = new Score("sam", 20, 2);

    private final String scoreListResponse = String.format("{\"scores\":[%s, %s]}", score1.toJsonString(), score2.toJsonString());

    @Test
    public void restTemplateInterceptorTest() {
        User user = new User("john", "password1");
        ScoreController controller = new ScoreController(user);

        RestTemplate rt = controller.getRestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = rt.getInterceptors();
        ClientHttpRequestInterceptor interceptor = interceptors.get(interceptors.size() -1);

        Assert.assertTrue(interceptor instanceof BasicAuthInterceptor);
    }

    @Test
    public void getScoresSuccessTest(){
        User user = new User("john", "password1");
        BasicAuthInterceptor interceptor = new BasicAuthInterceptor(user);
        RestTemplate restTemplate = new RestTemplateBuilder()
                .errorHandler(new ScoreErrorHandler())
                .additionalInterceptors(interceptor).build();

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        String reqUrl = String.format("%s/score", Configuration.getInstance().url);
        mockServer.expect(requestTo(reqUrl))
                .andExpect(method(GET))
                .andExpect(header("Authorization", "Basic " + user.toBase64()))
                .andRespond(withStatus(OK).contentType(MediaType.APPLICATION_JSON).body(scoreListResponse));

        ScoreController ac = new ScoreController(user);
        ac.setRestTemplate(restTemplate);
        List<Score> scores = ac.getScores();
        mockServer.verify();
        Assert.assertEquals(score1, scores.get(0));
    }

    @Test
    public void getScoresFailureTest(){
        User user = new User("john", "password1");
        BasicAuthInterceptor interceptor = new BasicAuthInterceptor(user);
        RestTemplate restTemplate = new RestTemplateBuilder().additionalInterceptors(interceptor).build();

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        String reqUrl = String.format("%s/score", Configuration.getInstance().url);
        mockServer.expect(requestTo(reqUrl))
                .andExpect(method(GET))
                .andExpect(header("Authorization", "Basic " + user.toBase64()))
                .andRespond(withStatus(OK));

        ScoreController ac = new ScoreController(user);
        ac.setRestTemplate(restTemplate);
        List<Score> scores = ac.getScores();

        mockServer.verify();
        Assert.assertTrue(scores.isEmpty());
    }

    @Test(expected = CustomIoException.class)
    public void getScoresErrorHandlerSuccessTest(){
        User user = new User("john", "password1");
        BasicAuthInterceptor interceptor = new BasicAuthInterceptor(user);
        RestTemplate restTemplate = new RestTemplateBuilder()
                .errorHandler(new ScoreErrorHandler())
                .additionalInterceptors(interceptor).build();

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        String reqUrl = String.format("%s/score", Configuration.getInstance().url);
        mockServer.expect(requestTo(reqUrl))
                .andExpect(method(GET))
                .andExpect(header("Authorization", "Basic " + user.toBase64()))
                .andRespond(withStatus(NOT_FOUND));

        ScoreController ac = new ScoreController(user);
        ac.setRestTemplate(restTemplate);
        List<Score> scores = ac.getScores();

        mockServer.verify();
        Assert.assertTrue(scores.isEmpty());
    }

    @Test(expected = HttpClientErrorException.class)
    public void getScoresErrorHandlerFailureTest(){
        User user = new User("john", "password1");
        BasicAuthInterceptor interceptor = new BasicAuthInterceptor(user);
        RestTemplate restTemplate = new RestTemplateBuilder()
                .errorHandler(new ScoreErrorHandler())
                .additionalInterceptors(interceptor).build();

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        String reqUrl = String.format("%s/score", Configuration.getInstance().url);
        mockServer.expect(requestTo(reqUrl))
                .andExpect(method(GET))
                .andExpect(header("Authorization", "Basic " + user.toBase64()))
                .andRespond(withStatus(FORBIDDEN));

        ScoreController ac = new ScoreController(user);
        ac.setRestTemplate(restTemplate);
        List<Score> scores = ac.getScores();

        mockServer.verify();
        Assert.assertTrue(scores.isEmpty());
    }

    @Test
    public void getScoresWrongStatusCodeTest(){
        User user = new User("john", "password1");
        BasicAuthInterceptor interceptor = new BasicAuthInterceptor(user);
        RestTemplate restTemplate = new RestTemplateBuilder().additionalInterceptors(interceptor).build();

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        String reqUrl = String.format("%s/score", Configuration.getInstance().url);
        mockServer.expect(requestTo(reqUrl))
                .andExpect(method(GET))
                .andExpect(header("Authorization", "Basic " + user.toBase64()))
                .andRespond(withStatus(ACCEPTED));

        ScoreController ac = new ScoreController(user);
        ac.setRestTemplate(restTemplate);
        List<Score> scores = ac.getScores();

        mockServer.verify();
        Assert.assertTrue(scores.isEmpty());
    }

    @Test
    public void getScoreSuccessTest(){
        User user = new User("john", "password1");
        BasicAuthInterceptor interceptor = new BasicAuthInterceptor(user);
        RestTemplate restTemplate = new RestTemplateBuilder()
                .errorHandler(new ScoreErrorHandler())
                .additionalInterceptors(interceptor).build();

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        String reqUrl = String.format("%s/score/%s", Configuration.getInstance().url, "john");
        mockServer.expect(requestTo(reqUrl))
                .andExpect(method(GET))
                .andExpect(header("Authorization", "Basic " + user.toBase64()))
                .andRespond(withStatus(OK).contentType(MediaType.APPLICATION_JSON).body(score1.toJsonString()));

        ScoreController ac = new ScoreController(user);
        ac.setRestTemplate(restTemplate);
        Score result = ac.getScore("john");
        mockServer.verify();
        Assert.assertEquals(score1, result);
    }

    @Test
    public void getScoreWrongStatusTest(){
        User user = new User("john", "password1");
        BasicAuthInterceptor interceptor = new BasicAuthInterceptor(user);
        RestTemplate restTemplate = new RestTemplateBuilder()
                .errorHandler(new ScoreErrorHandler())
                .additionalInterceptors(interceptor).build();

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        String reqUrl = String.format("%s/score/%s", Configuration.getInstance().url, "john");
        mockServer.expect(requestTo(reqUrl))
                .andExpect(method(GET))
                .andExpect(header("Authorization", "Basic " + user.toBase64()))
                .andRespond(withStatus(ACCEPTED));

        ScoreController ac = new ScoreController(user);
        ac.setRestTemplate(restTemplate);
        Score result = ac.getScore("john");
        mockServer.verify();
        Assert.assertNull(result);
    }

    @Test
    public void getScoreNullTest(){
        String score = "";
        User user = new User("john", "password1");
        BasicAuthInterceptor interceptor = new BasicAuthInterceptor(user);
        RestTemplate restTemplate = new RestTemplateBuilder()
                .errorHandler(new ScoreErrorHandler())
                .additionalInterceptors(interceptor).build();

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        String reqUrl = String.format("%s/score/%s", Configuration.getInstance().url, "john");
        mockServer.expect(requestTo(reqUrl))
                .andExpect(method(GET))
                .andExpect(header("Authorization", "Basic " + user.toBase64()))
                .andRespond(withStatus(OK).contentType(MediaType.APPLICATION_JSON).body(score));

        ScoreController ac = new ScoreController(user);
        ac.setRestTemplate(restTemplate);
        Score result = ac.getScore("john");
        mockServer.verify();
        Assert.assertNull(result);
    }
}
