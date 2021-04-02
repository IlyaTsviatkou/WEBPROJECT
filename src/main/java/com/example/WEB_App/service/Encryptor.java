package com.example.WEB_App.service;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class Encryptor {
        public static String encrypt(String pass) {
            MessageDigest messageDigest = null;
            byte[] bytesEncoded = null;
            try {
                messageDigest = MessageDigest.getInstance("SHA-1");
                messageDigest.update(pass.getBytes("utf8"));
                bytesEncoded = messageDigest.digest();
            } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            BigInteger bigInt = new BigInteger(1, bytesEncoded);
            String resHex = bigInt.toString(16);
            return resHex;
        }


}
