package picle;

import org.junit.Assert;
import org.junit.Test;
import picle.entity.Score;

public class ScoreTest {

    private Score score = new Score("Alpyboi", 1000, 10);

    @Test
    public void getUsernameTest() {
        Assert.assertEquals("Alpyboi", score.getUsername());
    }

    @Test
    public void setUsernameTest() {
        score.setUsername("Sungyboi");
        Assert.assertEquals("Sungyboi", score.getUsername());
    }

    @Test
    public void getScoreTest() {
        Assert.assertEquals(1000, score.getScore());
    }

    @Test
    public void setScoreTest() {
        score.setScore(2000);
        Assert.assertEquals(2000, score.getScore());
    }

    @Test
    public void getLevelTest() {
        Assert.assertEquals(10, score.getLevel());
    }

    @Test
    public void setLevelTest() {
        score.setLevel(20);
        Assert.assertEquals(20, score.getLevel());
    }
}
