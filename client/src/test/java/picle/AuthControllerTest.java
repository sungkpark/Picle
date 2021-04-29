package picle;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import picle.common.*;
import picle.controller.AuthController;
import picle.model.User;

import java.util.List;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

public class AuthControllerTest {

    private final String username = "johndoe";
    private final String password = "password";

    @Test
    public void restTemplateInterceptorTest(){
        User user = new User(username, password);
        AuthController ac = new AuthController(user);

        RestTemplate rt = ac.getRestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = rt.getInterceptors();
        ClientHttpRequestInterceptor interceptor = interceptors.get(interceptors.size() -1);

        Assert.assertTrue(interceptor instanceof BasicAuthInterceptor);
    }

    @Test
    public void loginSuccessfulTest(){
        User user = new User(username, password);
        BasicAuthInterceptor interceptor = new BasicAuthInterceptor(user);
        RestTemplate restTemplate = new RestTemplateBuilder().additionalInterceptors(interceptor).build();

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        String reqUrl = String.format("%s/auth", Configuration.getInstance().url);
        mockServer.expect(requestTo(reqUrl))
                .andExpect(method(GET))
                .andExpect(header("Authorization", "Basic " + user.toBase64()))
                .andRespond(withStatus(OK));

        AuthController ac = new AuthController(user);
        ac.setRestTemplate(restTemplate);
        ac.login();

        mockServer.verify();
    }

    @Test
    public void registerSuccessfulTest(){
        User user = new User(username, password);
        BasicAuthInterceptor interceptor = new BasicAuthInterceptor(user);
        RestTemplate restTemplate = new RestTemplateBuilder()
                .errorHandler(new AuthErrorHandler())
                .additionalInterceptors(interceptor).build();

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        String reqUrl = String.format("%s/register", Configuration.getInstance().url);
        mockServer.expect(requestTo(reqUrl))
                .andExpect(method(POST))
                .andExpect(header("Authorization", "Basic " + user.toBase64()))
                .andExpect(content().string(user.toJsonString()))
                .andRespond(withStatus(CREATED));

        AuthController ac = new AuthController(user);
        ac.setRestTemplate(restTemplate);
        ac.register(user);
        mockServer.verify();
    }

    @Test(expected = CustomIoException.class)
    public void registerAuthErrorHandlerSuccessTest(){
        User user = new User(username, password);
        BasicAuthInterceptor interceptor = new BasicAuthInterceptor(user);
        RestTemplate restTemplate = new RestTemplateBuilder()
                .errorHandler(new AuthErrorHandler())
                .additionalInterceptors(interceptor).build();

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        String reqUrl = String.format("%s/register", Configuration.getInstance().url);
        mockServer.expect(requestTo(reqUrl))
                .andExpect(method(POST))
                .andExpect(header("Authorization", "Basic " + user.toBase64()))
                .andExpect(content().string(user.toJsonString()))
                .andRespond(withStatus(UNAUTHORIZED));

        AuthController ac = new AuthController(user);
        ac.setRestTemplate(restTemplate);
        ac.register(user);
        mockServer.verify();
    }

    @Test(expected = HttpClientErrorException.class)
    public void registerAuthErrorHandlerFailureTest(){
        User user = new User(username, password);
        BasicAuthInterceptor interceptor = new BasicAuthInterceptor(user);
        RestTemplate restTemplate = new RestTemplateBuilder()
                .errorHandler(new AuthErrorHandler())
                .additionalInterceptors(interceptor).build();

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        String reqUrl = String.format("%s/register", Configuration.getInstance().url);
        mockServer.expect(requestTo(reqUrl))
                .andExpect(method(POST))
                .andExpect(header("Authorization", "Basic " + user.toBase64()))
                .andExpect(content().string(user.toJsonString()))
                .andRespond(withStatus(FORBIDDEN));

        AuthController ac = new AuthController(user);
        ac.setRestTemplate(restTemplate);
        ac.register(user);
        mockServer.verify();
    }

    @Test
    public void registerFailingTest(){
        User user = new User(username, password);
        BasicAuthInterceptor interceptor = new BasicAuthInterceptor(user);
        RestTemplate restTemplate = new RestTemplateBuilder()
                .errorHandler(new AuthErrorHandler())
                .additionalInterceptors(interceptor).build();

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        String reqUrl = String.format("%s/register", Configuration.getInstance().url);
        mockServer.expect(requestTo(reqUrl))
                .andExpect(method(POST))
                .andExpect(header("Authorization", "Basic " + user.toBase64()))
                .andExpect(content().string(user.toJsonString()))
                .andRespond(withStatus(OK));

        AuthController ac = new AuthController(user);
        ac.setRestTemplate(restTemplate);
        boolean TestResult = ac.register(user);
        mockServer.verify();
        Assert.assertFalse(TestResult);
    }
}
