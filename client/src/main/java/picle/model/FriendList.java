package picle.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class FriendList {

    private List<Friend> friends;

    @JsonCreator
    public FriendList(@JsonProperty("friends") List<Friend> friends) {
        this.friends = friends;
    }

    public List<Friend> getFriends() {
        return friends;
    }

}
