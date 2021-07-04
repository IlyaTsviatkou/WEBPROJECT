package com.example.topoftops.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.*;
import java.util.Base64;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;

/**
 * The utility is responsible for encrypting
 *
 * @author Ilya Tsvetkov
 */
public class Encryptor {
    private static Logger logger = LogManager.getLogger();
    private static int key = 8;

    /**
     * Encrypts
     *
     * @param str {@link String} password
     * @return {@link String} encrypted password
     */
    public static String encrypt(String str) {
        MessageDigest messageDigest = null;
        byte[] bytesEncoded = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(str.getBytes("utf8"));
            bytesEncoded = messageDigest.digest();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            logger.log(Level.WARN, e.getMessage());
        }
        BigInteger bigInt = new BigInteger(1, bytesEncoded);
        String resHex = bigInt.toString(16);
        return resHex;
    }


    /**
     * Encrypts string
     *
     * @param input {@link String} password
     * @return {@link String} encrypted password
     */
    public static String encryptC(String input) {
        String string = "";
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c >= 'a' && c <= 'z') {
                c += key % 26;
                if (c < 'a')
                    c += 26;
                if (c > 'z')
                    c -= 26;
            } else if (c >= 'A' && c <= 'Z') {
                c += key % 26;
                if (c < 'A')
                    c += 26;
                if (c > 'Z')
                    c -= 26;
            }
            string += c;
        }
        return string;
    }

    /**
     * Decrypt string
     *
     * @param input {@link String} cipherText
     * @return {@link String} encrypted password
     */
    public static String decryptC(String input) {
        String result;
        int k = Integer.parseInt("-" + key);
        String string = "";
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c >= 'a' && c <= 'z') {
                c += k % 26;
                if (c < 'a')
                    c += 26;
                if (c > 'z')
                    c -= 26;
            } else if (c >= 'A' && c <= 'Z') {
                c += k % 26;
                if (c < 'A')
                    c += 26;
                if (c > 'Z')
                    c -= 26;
            }
            string += c;

        }
        return string;
    }
}

