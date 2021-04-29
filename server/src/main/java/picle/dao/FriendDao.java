package picle.dao;

import picle.entity.Friend;

import java.util.Collection;

public interface FriendDao {


    /**
     * Gets all friends corresponding to this username.
     * @return list of friends
     */
    Collection<Friend> getAllFriends(String username);

    /**
     * Gets a single friend of the user.
     * @param username User of which we want to get the friend.
     * @param friendUsername Friend we want to retrieve.
     * @return the friend.
     */
    Friend getFriendByUsername(String username, String friendUsername);

    /**
     * Deletes a friend of the user.
     * @param username User of which we want to delete the friend.
     * @param friendUsername Friend we want to delete.
     */
    boolean deleteFriendByUsername(String username, String friendUsername);

    /**
     * Adds a friend for the user.
     * @param username User we want to add a friend to.
     * @param friend Friend to add.
     */
    boolean addFriend(String username, Friend friend);

}

