package picle.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Component
public class ActivityErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse httpResponse)
            throws IOException {

        return httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR;
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse)
            throws IOException {

        String responseString = new String(httpResponse.getBody().readAllBytes());
        if (httpResponse.getStatusCode()
                .value() == HttpStatus.NOT_FOUND.value()) {

            // Transform the response string to CustomIoException object
            ObjectMapper mapper = new ObjectMapper();

            throw mapper.readValue(responseString,
                    CustomIoException.class);
        }
        throw new HttpClientErrorException(httpResponse.getStatusCode());
    }
}