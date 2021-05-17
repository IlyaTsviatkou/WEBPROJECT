package com.example.WEB_App.util;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryptor {
    public static Logger logger = LogManager.getLogger();
        public static String encrypt(String pass) {
            MessageDigest messageDigest = null;
            byte[] bytesEncoded = null;
            try {
                messageDigest = MessageDigest.getInstance("SHA-1");
                messageDigest.update(pass.getBytes("utf8"));
                bytesEncoded = messageDigest.digest();
            } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
                logger.log(Level.WARN,e.getMessage());
            }
            BigInteger bigInt = new BigInteger(1, bytesEncoded);
            String resHex = bigInt.toString(16);
            return resHex;
        }
}
