package picle.entity;

import java.util.Collection;

public class FriendList {

    /**
     * List of friends of the authenticated user.
     */
    Collection<Friend> friends;

    /**
     * Constructor.
     * @param friends List of friends.
     */
    public FriendList(Collection<Friend> friends) {
        this.friends = friends;
    }

    public Collection<Friend> getFriends() {
        return friends;
    }

    public void setFriends(Collection<Friend> friends) {
        this.friends = friends;
    }
}
