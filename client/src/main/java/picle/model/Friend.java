package picle.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Objects;

/**
 * Model of a friend of the user.
 */
public class Friend {

    @JsonProperty ("username") private String username;

    public Friend(String username) {
        this.username = username;
    }

    public Friend(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Maps the object to a JSON String.
     * @return JSON
     */
    public String toJsonString() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("username", this.username);

        return node.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Friend friend = (Friend) obj;
        return Objects.equals(getUsername(), friend.getUsername());
    }

}
