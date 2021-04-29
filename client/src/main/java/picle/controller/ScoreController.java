package picle.controller;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import picle.common.BasicAuthInterceptor;
import picle.common.Configuration;
import picle.common.ScoreErrorHandler;
import picle.model.Score;
import picle.model.ScoreList;
import picle.model.User;

import java.util.ArrayList;
import java.util.List;

public class ScoreController {

    private RestTemplate restTemplate;

    /**
     * Constructor sets up rest template to automatically authenticate for each request.
     */
    public ScoreController(User user) {
        BasicAuthInterceptor authInterceptor = new BasicAuthInterceptor(user);
        this.restTemplate = new RestTemplateBuilder()
                .additionalInterceptors(authInterceptor)
                .errorHandler(new ScoreErrorHandler())
                .build();
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Performs API Get call to /score and returns a list of scores.
     */
    public List<Score> getScores() {
        String reqUrl = Configuration.getInstance().url + "/score";
        ResponseEntity<ScoreList> res = restTemplate.getForEntity(
                    reqUrl, ScoreList.class);
        if (res.getStatusCode() == HttpStatus.OK) {
            if (res.getBody() != null) {
                return res.getBody().getScoreList();
            }
        }

        return new ArrayList<>();
    }

    /**
     * Gets the score for a single user.
     * @param username Username of the user.
     * @return Score object for the corresponding username.
     */
    public Score getScore(String username) {
        String reqUrl = String.format("%s/score/%s", Configuration.getInstance().url, username);
        ResponseEntity<Score> res = restTemplate.getForEntity(reqUrl, Score.class);
        Score score = null;
        if (res.getStatusCode() == HttpStatus.OK) {
            if (res.getBody() != null) {
                score = res.getBody();
            }
        }
        return score;
    }


}
