package picle;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import picle.dao.ScoreDao;
import picle.entity.Score;
import picle.service.ScoreService;

import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.*;

public class ScoreServiceTest {

    @Mock
    private ScoreDao scoreDao;

    private MockMvc mvc;

    private ScoreService scoreService;
    private Collection<Score> expectedScores= new ArrayList<>();
    private Score one;
    private String username = "username";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        scoreService = new ScoreService(scoreDao);
        one = new Score(username, 100, 1);
        expectedScores.add(one);
    }

    @Test
    public void getAllScoresTest() {

        given(scoreDao.getAllScores()).willReturn(expectedScores);

        Collection<Score> allScores = scoreService.getAllScores();

        Assert.assertEquals(1, allScores.size());
        Assert.assertEquals(expectedScores, allScores);
    }

    @Test
    public void getScoreByUsernameTest() {
        given(scoreDao.getScoreByUsername("user")).willReturn(one);
        Score returned = scoreService.getScoreByUsername("user");
        Assert.assertEquals(one.getScore(), returned.getScore());
        Assert.assertEquals(one.getUsername(), returned.getUsername());
        Assert.assertEquals(one.getLevel(), returned.getLevel());
    }

    @Test
    public void updateScoreByUsernameTest() throws Exception {
        Score expectedScore = new Score("username", 1100, 1);

        given(scoreDao.getScoreByUsername(username)).willReturn(expectedScore);
        given(scoreDao.updateScoreByUsername(anyString(), anyInt(), anyInt())).willReturn(true);

        boolean actual = scoreService.updateScoreByUsername(username, 1000);
        Score result = scoreService.getScoreByUsername(username);

        Assert.assertEquals(true, actual);
        Assert.assertEquals(expectedScore, result);
    }

    @Test
    public void updateScoreByNotExistingUsernameTest() throws Exception {
        given(scoreDao.getScoreByUsername(username)).willReturn(one);
        given(scoreDao.updateScoreByUsername(anyString(), anyInt(), anyInt())).willReturn(false);

        boolean actual = scoreService.updateScoreByUsername(username, 1000);

        Assert.assertEquals(false, actual);
    }


}
