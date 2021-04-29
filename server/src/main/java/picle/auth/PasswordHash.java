package picle.auth;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * This class contains static methods to store and validate
 * passwords.
 */
public final class PasswordHash {

    /**
     * Return a string with the hexadecimal representation of the input array.
     * @param arr an array of bytes.
     * @return the hexadecimal representation of the input arr.
     */
    private static String toHex(byte[] arr) {
        BigInteger arrInt = new BigInteger(1, arr);
        String hex = arrInt.toString(16);

        int padlength = (arr.length * 2) - hex.length();
        if (padlength > 0) {
            return String.format("%0" + padlength + "d", 0) + hex;
        } else {
            return hex;
        }
    }

    /**
     * Parse a string with the hexadecimal representation of data into a byte array.
     * @param hex The string with hexadecimal representation of data.
     * @return the byte array.
     */
    private static byte[] fromHex(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

    /**
     * Generates a salted hash for 'password' to be stored in the database.
     * @param password the plaintext password to be hashed.
     * @return the hashed password.
     */
    public static String generateStoredHashedPassword(String password)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        int iterations = 1000;
        char[] chars = password.toCharArray();
        byte[] salt = generateSalt();

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] hash = skf.generateSecret(spec).getEncoded();

        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
    }

    private static byte[] generateSalt() throws NoSuchAlgorithmException {
        // We use non-blocking random numbers from the operating system.
        // On *nix systems, this will be /dev/urandom.
        SecureRandom sr = SecureRandom.getInstance("NativePRNGNonBlocking");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    /**
     * Validate the given password against the hash in the database.
     * @param storedPassword the hash from the database
     * @param password the password from the client
     * @return true if the password matches the one in the database
     */
    public static boolean validatePassword(String storedPassword, String password)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        String[] parts = storedPassword.split(":");
        int iterations = Integer.parseInt(parts[0]);
        byte[] salt = fromHex(parts[1]);
        byte[] hash = fromHex(parts[2]);

        char[] chars = password.toCharArray();
        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] testHash = skf.generateSecret(spec).getEncoded();

        for (int i = 0; i < hash.length; i++) {
            if (hash[i] != testHash[i]) {
                return false;
            }
        }

        return true;
    }
}
