package picle.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Base64;

/**
 * Model of the user which keeps track of the username and password.
 */
public class User {

    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * The server uses http basic authentication for which we need to set
     * the 'Authorization' header.
     * This header is of the form 'username:password' encoded in base64.
     * @return username and password separated by colon and encoded in base64.
     */
    public String toBase64() {
        String credentials =  username + ":" + password;
        return Base64.getEncoder().encodeToString(credentials.getBytes());
    }

    /**
     * Two users are equal when they have the same username and password.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        User user = (User) other;
        return username.equals(user.getUsername()) && password.equals(user.getPassword());
    }

    /**
     * ONLY used for registration in order to send username and password as JSON.
     */
    public String toJsonString() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("username", this.username);
        node.put("password", this.password);

        return node.toString();
    }

}
