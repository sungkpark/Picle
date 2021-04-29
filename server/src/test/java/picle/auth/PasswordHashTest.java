package picle.auth;

import org.junit.Assert;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.Assert.*;

public class PasswordHashTest {

    private final String examplePlainTextPassword1 = "password";
    private final String exampleHashedPassword1 = "1000:"
            + "d8c60cb463e7540ad6fc66a49f26ba7c:"
            + "94313c1c5fd9e47e4c19a61125a1c4d69570633b9d75f149c680137e"
            + "05200f5c1cdc703920ce7a985d78f3c7a3196b7fb32d534fa3a65f69"
            + "3ed2b223f62f1e29";

    @Test
    public void validationTestWithCorrectPreconfiguredHash() {
        try {
            Assert.assertTrue(PasswordHash.validatePassword(exampleHashedPassword1,
                    examplePlainTextPassword1));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("The current system does not support the algorithm we"
                    + "use for hashing.");
            Assert.assertTrue(false);
        } catch (InvalidKeySpecException e) {
            System.out.println(e.toString());
            Assert.assertTrue(false);
        }
    }

    @Test
    public void validationTestWithIncorrectPreconfiguredPassword() {
        try {
            Assert.assertFalse(PasswordHash.validatePassword(exampleHashedPassword1,
                    "passw0rd"));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("The current system does not support the algorithm we"
                    + "use for hashing.");
            Assert.assertTrue(false);
        } catch (InvalidKeySpecException e) {
            System.out.println(e.toString());
            Assert.assertTrue(false);
        }
    }

    @Test
    public void validationTestWithNewlyGeneratedHash() {
        String password = "hunter2";
        try {
            String hash = PasswordHash.generateStoredHashedPassword(password);
            Assert.assertTrue(PasswordHash.validatePassword(hash, password));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("The current system does not support the algorithm we"
                    + "use for hashing.");
            Assert.assertTrue(false);
        } catch (InvalidKeySpecException e) {
            System.out.println(e.toString());
            Assert.assertTrue(false);
        }
    }



}