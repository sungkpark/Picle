package picle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import picle.entity.Score;
import picle.entity.ScoreList;
import picle.service.ScoreService;

import java.util.Collection;

/**
 * Handles HTTP methods.
 */
@RestController
@RequestMapping("/score")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    /**
     * GET method to get all scores.
     * http://localhost:8080/score
     * @return a list of all scores.
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ScoreList> getAllScores() {
        Collection<Score> allScores = scoreService.getAllScores();
        if (allScores == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ScoreList list = new ScoreList(allScores);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * GET method to get a singular score of a user.
     * http://locahost:8080/score/{user}
     * @param user whose Score to get
     * @param username filter user
     * @return score of the requested user, if it exists
     */
    @RequestMapping(value = "/{user}", method = RequestMethod.GET)
    public ResponseEntity<Score> getScoreByUsername(
            @PathVariable("user") String user,
            @RequestAttribute("X-Picle-Username") String username) {
        Score score = scoreService.getScoreByUsername(user);
        if (score == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(score, HttpStatus.OK);
    }

    /**
     * PUT method to update a user's total_score and level (achievement).
     * http://locahost:8080/score/{user}/{score}
     * @param username username of score to be updated
     * @param score new score from a new activity
     *              that will be added to total_score
     * @param xpicleUsername filter user
     * @return boolean of whether score was updated or not
     */
    @RequestMapping(value = "/{username}/{score}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateScoreByUsername(
            @PathVariable("username") String username,
            @PathVariable("score") int score,
            @RequestAttribute("X-Picle-Username") String xpicleUsername) {
        boolean flag = scoreService.updateScoreByUsername(username, score);
        if (!flag) {
            return new ResponseEntity<>("This username doesn't exist",
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(username + "'s score has been updated",
                HttpStatus.OK);
    }
}

