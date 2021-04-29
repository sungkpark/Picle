package picle;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import picle.dao.ActivityDao;
import picle.dao.FriendDao;
import picle.entity.Activity;
import picle.entity.Friend;
import picle.entity.PostActivity;
import picle.service.ActivityService;
import picle.service.SocialService;

import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.BDDMockito.*;

public class SocialServiceTest {

    @Mock
    private FriendDao friendDao;

    private SocialService socialService;
    private Collection<Friend> expectedFriends = new ArrayList<>();
    private Friend one = new Friend("friend1");
    private Friend two = new Friend("friend2");
    private String username = "username";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        socialService = new SocialService(friendDao);
        expectedFriends.add(one);
    }


    @Test
    public void getAllFriendsTest() {

        given(friendDao.getAllFriends(username)).willReturn(expectedFriends);

        Collection<Friend> allFriends = socialService.getAllFriends(username);

        Assert.assertEquals(1, allFriends.size());
        Assert.assertEquals(expectedFriends, allFriends);
    }

    @Test
    public void getFriendByUsernameTest() {

        given(friendDao.getFriendByUsername(username, one.getUsername())).willReturn(one);

        Friend friend = socialService.getFriendByUsername(username, one.getUsername());

        Assert.assertTrue(friend.getUsername().equals(one.getUsername()));

    }

    @Test
    public void deleteFriendByUsernameSuccessTest() {
        // given
        expectedFriends.remove(one);

        // when
        when(friendDao.getAllFriends(username)).thenReturn(expectedFriends);
        when(friendDao.deleteFriendByUsername(username, one.getUsername())).thenReturn(true);
        boolean actual = socialService.deleteFriendByUsername(username, one.getUsername());
        Collection<Friend> result = socialService.getAllFriends(username);

        //then
        Assert.assertEquals(true, actual);
        Assert.assertEquals(expectedFriends, result);
    }

    @Test
    public void deleteFriendByUsernameFailureTest() {
        // when
        when(friendDao.getAllFriends(username)).thenReturn(expectedFriends);
        when(friendDao.deleteFriendByUsername(username, one.getUsername())).thenReturn(false);
        boolean actual = socialService.deleteFriendByUsername(username, "nonsense");
        Collection<Friend> result = socialService.getAllFriends(username);

        // then
        Assert.assertEquals(false, actual);
        Assert.assertEquals(expectedFriends, result);
    }

    @Test
    public void addActivityTest() {
        // given
        expectedFriends.add(two);

        // when
        when(friendDao.getAllFriends(username)).thenReturn(expectedFriends);
        when(friendDao.addFriend(anyString(), any())).thenReturn(true);
        boolean actual = socialService.addFriend(username, two);
        Collection<Friend> result = socialService.getAllFriends(username);

        // then
        Assert.assertEquals(true, actual);
        Assert.assertEquals(expectedFriends, result);

    }

}
