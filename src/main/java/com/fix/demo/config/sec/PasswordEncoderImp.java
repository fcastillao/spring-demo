package com.fix.demo.config.sec;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class PasswordEncoderImp implements PasswordEncoder {

    private static final Logger logger = Logger.getLogger(PasswordEncoderImp.class.getName());


    @Override
    public String encode(CharSequence rawPassword) {
        return getSha512SecurePassword(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }

    /**
     * Generate a hash String from the password provided.
     *
     * @param passwordToHash password to hash
     * @return a String
     */
    private String getSha512SecurePassword(String passwordToHash) {
        String salt = generateSalt(passwordToHash.hashCode());
        String generatedPassword = null;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes("UTF-8"));
            byte[] bytes = md.digest(passwordToHash.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.log(Level.ALL, "Couldn't find the algorithm to encrypt the password", e);
        } catch (UnsupportedEncodingException e) {
            logger.log(Level.ALL, "Couldn't encode the String to UTF-8", e);
        }

        return generatedPassword;
    }

    /**
     * Generate a random String, based on the seed provided.
     *
     * @param saltSeed the seed to use for the random generator
     * @return Random String;
     */
    private String generateSalt(int saltSeed) {
        Random rgn = new Random(saltSeed);
        StringBuilder saltBuilder = new StringBuilder();

        for (int i = 0; i < 16; i++) {
            saltBuilder.append((char) (rgn.nextInt(93) + 33));
        }

        return saltBuilder.toString();
    }
}
