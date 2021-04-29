package picle.dao;

import picle.entity.RegisterUser;
import picle.entity.User;

public interface UserDao {

    /**
     * Get a user from the database.
     * @param userId the id of the user
     * @return a User object with all data retrieved from the database
     */
    User getUser(int userId);

    /**
     * Get a user from the database.
     * @param username the username of the user
     * @return a User object with all data retrieved from the database
     */
    User getUser(String username);

    /**
     * Check if there is a user for the given userID.
     * @param userId the id of the user
     * @return true if the userId is associated with an account in the database.
     */
    boolean userExists(int userId);

    /**
     * Check if the username is known in the database.
     * @param username the username of the user
     * @return true if the username is known
     */
    boolean userExists(String username);

    /**
     * Delete the user and all their data associated with the given userID.
     * @param userId the id of the user
     * @return true if successful
     */
    boolean deleteUser(int userId);

    /**
     * Delete the user and all their data associated with the given username.
     * @param username the username of the user
     * @return true if successful
     */
    boolean deleteUser(String username);

    // I'm not sure if we're going to use this, but who knows.
    /**
     * Change the username associated to userId to newUsername.
     * @param userId the id of the user
     * @param newUsername the new username of the user
     * @return true if successful
     */
    boolean changeUsername(int userId, String newUsername);

    boolean register(RegisterUser registerUser);
}