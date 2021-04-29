package picle.controller;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import picle.common.AuthErrorHandler;
import picle.common.BasicAuthInterceptor;
import picle.common.Configuration;
import picle.model.User;


/**
 * Controller for the /auth api.
 */
public class AuthController {

    private RestTemplate restTemplate;

    /**
     * Constructor sets up rest template to automatically authenticate for each request.
     */
    public AuthController(User user) {
        BasicAuthInterceptor authInterceptor = new BasicAuthInterceptor(user);
        this.restTemplate = new RestTemplateBuilder()
                .additionalInterceptors(authInterceptor)
                .errorHandler(new AuthErrorHandler())
                .build();
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Performs API Get call to /auth with username and password in authorization header.
     * Note that the BasicAuthInterceptor used in the constructor sets the username and password.
     * Spring will throw an exception if the response is other than OK which will be handled by the
     * AuthErrorHandler in the common package. Therefore running this method without exception
     * implies that the user authenticated successfully.
     */
    public void login() {
        String reqUrl = Configuration.getInstance().url + "/auth";
        restTemplate.getForEntity(reqUrl, String.class);
    }

    /**
     * Takes in a user and uses API POST call to send their username and password to be registered.
     */
    public boolean register(User user) {
        String reqUrl = Configuration.getInstance().url + "/register";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(user.toJsonString() ,headers);

        ResponseEntity<String> res = restTemplate.postForEntity(reqUrl,
                entity, String.class);

        return res.getStatusCode() == HttpStatus.CREATED;
    }


}
