package picle;


import org.junit.Assert;
import org.junit.Test;
import picle.entity.Activity;

public class ActivityTest {

    private Activity basis = new Activity(1, "username", 1, 1, 1, 1);

    @Test
    public void setActivityIdTest() {
        basis.setActivityId(2);
        Assert.assertEquals(2, basis.getActivityId());
    }

    @Test
    public void testSetActivityTypeIdTest() {
        basis.setActivityTypeId(2);
        Assert.assertEquals(2, basis.getActivityTypeId());
    }

    @Test
    public void setParam1Test(){
        basis.setParam1(2);
        Assert.assertEquals(2, basis.getParam1());
    }

    @Test
    public void setParam2Test() {
        basis.setParam2(2);
        Assert.assertEquals(2, basis.getParam2());
    }

    @Test
    public void setScoreTest() {
        basis.setScore(2);
        Assert.assertEquals(2, basis.getScore());
    }

}
