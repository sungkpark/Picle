package picle;

import org.junit.Assert;
import org.junit.Test;
import picle.model.User;

public class UserTest {

    private final String username1 = "username1";
    private final String password1 = "password1";
    private final String base64    = "dXNlcm5hbWUxOnBhc3N3b3JkMQ==";

    private final String username2 = "username2";
    private final String password2 = "password2";

    @Test
    public void getUsernameTest(){
        User user = new User(username1, password1);
        Assert.assertEquals(username1, user.getUsername());
    }

    @Test
    public void setUsernameTest(){
        User user = new User(username1, password1);
        user.setUsername(username2);
        Assert.assertEquals(username2, user.getUsername());
    }

    @Test
    public void getPasswordTest(){
        User user = new User(username1, password1);
        Assert.assertEquals(password1, user.getPassword());
    }

    @Test
    public void setPasswordTest(){
        User user = new User(username1, password1);
        user.setPassword(password2);
        Assert.assertEquals(password2, user.getPassword());
    }

    @Test
    public void base64EncodeTest(){
        User user = new User(username1, password1);
        Assert.assertEquals(base64, user.toBase64());
    }

    @Test
    public void equalUsersTest(){
        User user1 = new User(username1, password2);
        User user2 = new User(username1, password2);
        Assert.assertEquals(user1, user2);
    }

    @Test
    public void notEqualUsernameTest(){
        User user1 = new User(username1, password2);
        User user2 = new User(username2, password2);
        Assert.assertNotEquals(user1, user2);
    }

    @Test
    public void notEqualPasswordTest(){
        User user1 = new User(username1, password1);
        User user2 = new User(username1, password2);
        Assert.assertNotEquals(user1, user2);
    }

    @Test
    public void sameObjectUserTest(){
        User user1 = new User(username1, password1);
        User user2 = user1;
        Assert.assertEquals(user1, user2);
    }

    @Test
    public void nullUserTest(){
        User user1 = new User(username1, password1);
        Assert.assertNotEquals(user1, null);
    }

    @Test
    public void equalsMethodNullTest(){
        User user1 = new User(username1, password1);
        User user2 = null;
        Assert.assertFalse(user1.equals(user2));
    }

    @Test
    public void equalsMethodWrongTypeTest(){
        User user1 = new User(username1, password1);
        String user2 = "this user";
        Assert.assertFalse(user1.equals(user2));
    }




}
