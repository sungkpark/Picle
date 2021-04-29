package picle.entity;

public class User {
    /**
     * The id of the user in the database.
     */
    private int userId;

    /**
     * The username of the user.
     */
    private String username;

    /**
     * The password of the user. Note that this is the PLAINTEXT password, not the hashed one.
     */
    private String password;

    /**
     * Creates a User object, mostly used for in the UserDao.
     * @param userId The id of the user
     * @param username The username of the user
     * @param password The password or password hash of the user
     */
    public User(int userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}