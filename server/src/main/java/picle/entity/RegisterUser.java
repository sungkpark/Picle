package picle.entity;

import picle.auth.PasswordHash;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class RegisterUser {

    private String username;
    private String password;

    /**
     * The RegisterUser object is a specific object for when a new user
     * is registered. It also hashes the password before it's stored in
     * the database.
     * @param username the username of the user.
     * @param password the password of the new user, in plain text.
     */
    public RegisterUser(String username, String password) {
        this.username = username;

        try {
            this.password = PasswordHash.generateStoredHashedPassword(password);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("The current system does not support the used hashing algorithm.");
        } catch (InvalidKeySpecException e) {
            System.out.println("Something went wrong hashing the password.");
        }
    }

    public RegisterUser() { }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Set the password.
     * @param password the password in the plain text
     */
    public void setPassword(String password) {
        try {
            this.password = PasswordHash.generateStoredHashedPassword(password);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("The current system does not support the used hashing algorithm.");
        } catch (InvalidKeySpecException e) {
            System.out.println("Something went wrong hashing the password.");
        }
    }
}
