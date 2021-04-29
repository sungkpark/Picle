package picle;

import org.junit.Assert;
import org.junit.Test;
import picle.model.Score;
import picle.model.User;

public class ScoreTest {

   private final double score1 = 10000.0;
   private final double score2 = 12345.67;
   private final int level1 = 1;
   private final int level2 = 2;
   private final String scoreString = "{\"username\":\"john\",\"score\":10.0,\"level\":2}";

    @Test
    public void getScoreTest(){
        Score score = new Score("john", score1, level1);
        Assert.assertEquals(score.getScore(), 10.0, 0);
    }

    @Test
    public void setScoreTest(){
        Score score = new Score("john", score1, level1);
        score.setScore(score2);
        Assert.assertEquals(score.getScore(),12.34567 , 0);
    }

    @Test
    public void getLevelTest(){
        Score score = new Score("john", score1, level1);
        Assert.assertEquals(score.getLevel(), 1);
    }

    @Test
    public void setLevelTest(){
        Score score = new Score("john", score1, level1);
        score.setLevel(level2);
        Assert.assertEquals(score.getLevel(),2);
    }

    @Test
    public void toJsonTest(){
        Score score = new Score("john", 10.0, level2);
        Assert.assertEquals(scoreString, score.toJsonString());
    }

    @Test
    public void activityEqualTest(){
        Score score1 = new Score("john", 10.0, level1);
        Score score2 = new Score("john", 10.0, level1);
        Assert.assertEquals(score1, score2);
    }

    @Test
    public void activityNotEqualTest(){
        Score score1 = new Score("john", 10.0, level1);
        Score score2 = new Score("sam", 12.34567, level2);
        Assert.assertNotEquals(score1, score2);
    }

    @Test
    public void equalsMethodSameScoreTest(){
        Score score = new Score("john", 10.0, level1);
        Assert.assertTrue(score.equals(score));
    }

    @Test
    public void equalsMethodNullTest(){
        Score score = new Score("john", 10.0, level1);
        Score score2 = null;
        Assert.assertFalse(score.equals(score2));
    }

    @Test
    public void equalsMethodWrongTypeTest(){
        Score score = new Score("john", 10.0, level1);
        String score2 = "score";
        Assert.assertFalse(score.equals(score2));
    }

    @Test
    public void equalsMethodDifferentUsernameTest(){
        Score score = new Score("john", 10.0, level1);
        Score score2 = new Score("max", 10.0, level1);
        Assert.assertFalse(score.equals(score2));
    }

    @Test
    public void equalsMethodDifferentLevelTest(){
        Score score = new Score("max", 10.0, level1);
        Score score2 = new Score("max", 10.0, level2);
        Assert.assertFalse(score.equals(score2));
    }
}
