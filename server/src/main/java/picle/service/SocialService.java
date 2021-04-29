package picle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import picle.dao.FriendDao;
import picle.entity.Friend;

import java.util.Collection;

/**
 * Business logic.
 */
@Service
public class SocialService {

    /**
     * Uses the interface FriendDao of which the used class is
     * defined in the @Qualifier.
     */
    @Autowired
    private FriendDao friendDao;

    public SocialService(FriendDao friendDao) {
        this.friendDao = friendDao;
    }

    public Collection<Friend> getAllFriends(String username) {
        return this.friendDao.getAllFriends(username);
    }

    public Friend getFriendByUsername(String username, String friendUsername) {
        return this.friendDao.getFriendByUsername(username, friendUsername);
    }

    public boolean deleteFriendByUsername(String username, String friendUsername) {
        return this.friendDao.deleteFriendByUsername(username, friendUsername);
    }

    /**
     * Register a friend with for the given user.
     * @param username the user to register the activity with.
     * @param friend the Friend object with the name of the friend.
     * @return true if success.
     */
    public boolean addFriend(String username, Friend friend) {
        return this.friendDao.addFriend(username, friend);
    }

}

