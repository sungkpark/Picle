package picle.common;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import picle.model.User;

import java.io.IOException;


/**
 * This interceptor can be added to a rest template to automatically customize
 * the Authorization headers with the username and password of the user.
 * Eg. rt = new RestTemplateBuilder.getInterceptors().add(new BasicAuthInterceptor(userX)).build();.
 */
public class BasicAuthInterceptor implements ClientHttpRequestInterceptor {

    private User user;

    public BasicAuthInterceptor(User user) {
        this.user = user;
    }

    @Override
    public ClientHttpResponse intercept(
            HttpRequest request,
            byte[] body,
            ClientHttpRequestExecution execution
    ) throws IOException {
        String authorization = user.toBase64();
        request.getHeaders().add("Authorization", "Basic " + authorization);

        return execution.execute(request, body);
    }
}
