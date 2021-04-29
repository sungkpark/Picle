package picle.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Friend {

    /**
     * Username corresponding to friend.
     */
    private String username;

    /**
     * Constructor.
     * @param username Username of friend.
     */
    public Friend(@JsonProperty("username") String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
