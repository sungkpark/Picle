package picle;

import org.junit.Assert;
import org.junit.Test;
import picle.model.Activity;

public class ActivityTest {

    private final int id = 1;
    private final int activity_type_id = 2;
    private final int score = 3;
    private final int param0 = 4;
    private final int param1 = 5;
    private final String activityString = "{\"activityId\":1,\"activityTypeId\":2,\"param1\":4,\"param2\":5,\"score\":3}";


    private final int id2 = 1;
    private final int activity_type_id2 = 2;
    private final int score2 = 3;
    private final int param02 = 4;
    private final int param12 = 5;

    @Test
    public void getActivityIdTest(){
        Activity activity = new Activity(id, activity_type_id, param0, param1, score);
        Assert.assertEquals(id, activity.getId());
    }

    @Test
    public void setActivityIdTest(){
        Activity activity = new Activity(id, activity_type_id, param0, param1, score);
        activity.setId(id2);
        Assert.assertEquals(id2, activity.getId());
    }

    @Test
    public void getActivityTypeIdTest(){
        Activity activity = new Activity(id, activity_type_id, param0, param1, score);
        Assert.assertEquals(activity_type_id, activity.getType_id());
    }

    @Test
    public void setActivityTypeIdTest(){
        Activity activity = new Activity(id, activity_type_id, param0, param1, score);
        activity.setType_id(activity_type_id2);
        Assert.assertEquals(activity_type_id2, activity.getType_id());
    }

    @Test
    public void getActivityScoreTest(){
        Activity activity = new Activity(id, activity_type_id, param0, param1, score);
        Assert.assertEquals(score, activity.getScore());
    }

    @Test
    public void setActivityScoreTest(){
        Activity activity = new Activity(id, activity_type_id, param0, param1, score);
        activity.setScore(score2);
        Assert.assertEquals(score2, activity.getScore());
    }

    @Test
    public void getActivityParam0Test(){
        Activity activity = new Activity(id, activity_type_id, param0, param1, score);
        Assert.assertEquals(param0, activity.getParam0());
    }

    @Test
    public void setActivityParam0Test(){
        Activity activity = new Activity(id, activity_type_id, param0, param1, score);
        activity.setParam0(param02);
        Assert.assertEquals(param02, activity.getParam0());
    }

    @Test
    public void getActivityParam1Test(){
        Activity activity = new Activity(id, activity_type_id, param0, param1, score);
        Assert.assertEquals(param1, activity.getParam1());
    }

    @Test
    public void setActivityParam1Test(){
        Activity activity = new Activity(id, activity_type_id, param0, param1, score);
        activity.setParam1(param12);
        Assert.assertEquals(param12, activity.getParam1());
    }

    @Test
    public void toJsonTest(){
        Activity activity = new Activity(id, activity_type_id, param0, param1, score);
        Assert.assertEquals(activityString, activity.toJsonString());
    }

    @Test
    public void activityEqualTest(){
        Activity activity = new Activity(id, activity_type_id, param0, param1, score);
        Activity activity2 = new Activity(id, activity_type_id, param0, param1, score);
        Assert.assertEquals(activity, activity2);
    }

    @Test
    public void activityNotEqualTest(){
        Activity activity = new Activity(id, activity_type_id, param0, param1, score);
        Activity activity2 = new Activity(id2, activity_type_id2, score2, param02, param12);
        Assert.assertNotEquals(activity, activity2);
    }

    @Test
    public void equalsMethodSameActivityTest(){
        Activity activity = new Activity(id, activity_type_id, param0, param1, score);
        Assert.assertTrue(activity.equals(activity));
    }

    @Test
    public void equalsMethodNullTest(){
        Activity activity = new Activity(id, activity_type_id, param0, param1, score);
        Activity activity2 = null;
        Assert.assertFalse(activity.equals(activity2));
    }

    @Test
    public void equalsMethodWrongTypeTest(){
        Activity activity = new Activity(id, activity_type_id, param0, param1, score);
        String activity2 = "vegetarian meal";
        Assert.assertFalse(activity.equals(activity2));
    }

    @Test
    public void equalsMethodDifferentIdTest(){
        Activity activity = new Activity(1, 1, 1, 1, 1);
        Activity activity2 = new Activity(2, 1, 1, 1, 1);
        Assert.assertFalse(activity.equals(activity2));
    }

    @Test
    public void equalsMethodDifferentActivityTypeTest(){
        Activity activity = new Activity(1, 1, 1, 1, 1);
        Activity activity2 = new Activity(1, 2, 1, 1, 1);
        Assert.assertFalse(activity.equals(activity2));
    }

    @Test
    public void equalsMethodDifferentParam1Test(){
        Activity activity = new Activity(1, 1, 1, 1, 1);
        Activity activity2 = new Activity(1, 1, 1, 2, 1);
        Assert.assertFalse(activity.equals(activity2));
    }

    @Test
    public void equalsMethodDifferentScoreTest(){
        Activity activity = new Activity(1, 1, 1, 1, 1);
        Activity activity2 = new Activity(1, 1, 1, 1, 2);
        Assert.assertFalse(activity.equals(activity2));
    }
}
