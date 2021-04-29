package picle;

import org.junit.Assert;
import org.junit.Test;
import picle.model.Friend;
import picle.model.Score;

public class FriendTest {

   private final String username1 = "john";
   private final String username2 = "doe";
   private final String friendString = "{\"username\":\"john\"}";

    @Test
    public void getUsernameTest(){
        Friend friend = new Friend(username1);
        Assert.assertEquals(username1, friend.getUsername());
    }

    @Test
    public void setUsernameTest(){
        Friend friend = new Friend(username1);
        friend.setUsername(username2);
        Assert.assertEquals(username2, friend.getUsername());
    }

    @Test
    public void toJsonTest(){
        Friend friend = new Friend(username1);
        Assert.assertEquals(friendString, friend.toJsonString());
    }

    @Test
    public void friendEqualTest(){
        Friend friend1 = new Friend(username1);
        Friend friend2 = new Friend(username1);
        Assert.assertEquals(friend2, friend1);
    }

    @Test
    public void activityNotEqualTest(){
        Friend friend1 = new Friend(username1);
        Friend friend2 = new Friend(username2);
        Assert.assertNotEquals(friend2, friend1);
    }

    @Test
    public void methodEqualsSuccessTest(){
        Friend friend1 = new Friend(username1);
        Friend friend2 = new Friend(username1);
        Assert.assertTrue(friend1.equals(friend2));
    }

    @Test
    public void methodEqualsSameFriendTest(){
        Friend friend1 = new Friend(username1);
        Assert.assertTrue(friend1.equals(friend1));
    }

    @Test
    public void methodEqualsNotEqualTest(){
        Friend friend1 = new Friend(username1);
        Friend friend2 = new Friend(username2);
        Assert.assertFalse(friend1.equals(friend2));
    }

    @Test
    public void methodEqualsWrongTypeTest(){
        Friend friend1 = new Friend(username1);
        String friend2 = "Max";
        Assert.assertFalse(friend1.equals(friend2));
    }

    @Test
    public void methodEqualsNullTest(){
        Friend friend1 = new Friend(username1);
        Friend friend2 = null;
        Assert.assertFalse(friend1.equals(friend2));
    }


}
