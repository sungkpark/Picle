package picle;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import picle.controller.ScoreController;
import picle.entity.Score;
import picle.entity.ScoreList;
import picle.service.ScoreService;

import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ScoreControllerTest {

    private Score one;
    private Score two;
    private Collection<Score> scores;
    private ScoreList allScores;

    @Mock
    private ScoreService mockScoreService;

    @InjectMocks
    private ScoreController scoreController;

    @Autowired
    private ObjectMapper mapper;

    private MockMvc mvc;

    String username = "username";

    @Before
    public void setup() {
        one = new Score(username, 100, 1);
        two = new Score(username, 200, 2);
        scores = new ArrayList<>();
        scores.add(one);
        allScores = new ScoreList(scores);
        MockitoAnnotations.initMocks(this);
        this.mvc = MockMvcBuilders.standaloneSetup(scoreController).build();
    }

    @Test
    public void getAllScoresOk() throws Exception {
        scores.add(two);
          allScores.setScores(scores);
          when(mockScoreService.getAllScores()).thenReturn(scores);
          this.mvc.perform(get("/score").header("X-Picle-Username", username)).andDo(MockMvcResultHandlers.print())
                  .andExpect(content().json("{\"scores\":[{\"username\":\"username\",\"score\":100,\"level\":1},{\"username\":\"username\",\"score\":200,\"level\":2}]}"))
                  .andExpect(status().isOk());
    }

    @Test
    public void getAllScoresNotFound() throws Exception {
        when(mockScoreService.getAllScores()).thenReturn(new ArrayList<>());
        this.mvc.perform(get("/score").header("X-Picle-Username", username))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"scores\":[]}"));
    }

    @Test
    public void getScoreByUsername() throws Exception {
        Score toReturn = new Score("user", 1000, 10);
        when(mockScoreService.getScoreByUsername("user")).thenReturn(toReturn);
        this.mvc.perform(get("/score/user").requestAttr("X-Picle-Username", username))
                .andExpect(content().json("{\"username\":\"user\",\"score\":1000,\"level\":10}"))
                .andExpect(status().isOk());
    }

    @Test
    public void getScoreByUsernameNullReturn() throws Exception {
        when(mockScoreService.getScoreByUsername("user")).thenReturn(null);
        this.mvc.perform(get("/score/user").requestAttr("X-Picle-Username", username))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateScoreByUsername() throws Exception {
        Score score = new Score("user", 1330, 13);
        when(mockScoreService.updateScoreByUsername("user", 100)).thenReturn(true);
        this.mvc.perform(put("/score/user/100").requestAttr("X-Picle-Username", username)).andExpect(status().isOk()).andExpect(content().string(score.getUsername() + "'s score has been updated"));
    }

    @Test
    public void updateScoreByNonExistingUsername() throws Exception {
        when(mockScoreService.updateScoreByUsername("noone", 100)).thenReturn(false);
        this.mvc.perform(put("/score/noone/100").requestAttr("X-Picle-Username", username)).andExpect(status().is4xxClientError()).andExpect(content().string("This username doesn't exist"));
    }
}
