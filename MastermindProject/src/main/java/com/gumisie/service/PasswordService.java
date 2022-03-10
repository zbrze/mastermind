package com.gumisie.service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Random;

public class PasswordService {

    private static final Random random = new SecureRandom();
    private static final int hashingIterations = 100;
    private static final String algorithm = "PBKDF2WithHmacSHA256"; //according to https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#SecretKeyFactory
    private static final int keyLength = 256;

    /**
     * Generates a (securely) random String to be used as salt
     * the salt is a random 64 bits encoded to String in Base64
     *
     * @return a random salt
     */
    public static String generateSalt() {
        byte[] salt = new byte[64];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * Hashes a password with the given salt
     *
     * @param password plaintext password
     * @param salt salt
     * @return hashed password
     */
    public static String hash(String password, String salt) {
        //init hasher
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), Base64.getDecoder().decode(salt), hashingIterations, keyLength);
        try {
            //generate key
            SecretKeyFactory skf = SecretKeyFactory.getInstance(algorithm);
            byte[] hashedBytes = skf.generateSecret(spec).getEncoded();
            //encode to string
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Error while hashing password: " + e.getMessage(), e);
        } finally {
            //removes password cache from hasher
            spec.clearPassword();
        }
    }

    /**
     * Checks if a password+salt combination is correct
     *
     * @param password plaintext password to check
     * @param salt known salt for the authenticating user
     * @param expectedHash known hashed password for the authenticating user
     * @return true if the password is correct
     */
    public static boolean authenticate(String password, String salt, String expectedHash) {
        String hashed = hash(password, salt);
        System.out.println("Hashed: " + hashed + " Exapected: " + expectedHash + " Equal: " + hashed.equals(expectedHash));
        return hashed.equals(expectedHash);
    }

}
