package com.example.topoftops.util;

import com.example.topoftops.validation.InputInfoValidator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

import java.util.Arrays;
import java.util.List;

public class EncryptorTest {

    @DataProvider(name = "correctEncrypt")
    public Object[][] encryptCorrectProvider() {
        return new Object[][] {
                new Object[] { "qtgibad", "ilyatsv" },
                new Object[] { "bmabcamz", "testuser" },
        };
    }

    @DataProvider(name = "incorrectEncrypt")
    public Object[][] encryptIncorrectProvider() {
        return new Object[][] {
                new Object[] { "qkjdbwajk", "ilyatsv" },
                new Object[] { "1231aasdz", "testuser" },
        };
    }

    @Test(dataProvider = "correctEncrypt")
    public void encryptCTest(String encrypted, String decrypted ) {
        Assert.assertEquals(encrypted,Encryptor.encryptC(decrypted));
    }

    @Test(dataProvider = "incorrectEncrypt")
    public void encryptICTest(String encrypted, String decrypted ) {
        Assert.assertNotEquals(encrypted,Encryptor.encryptC(decrypted));
    }

    @Test(dataProvider = "correctEncrypt")
    public void decryptCTest(String encrypted, String decrypted ) {
        Assert.assertEquals(decrypted,Encryptor.decryptC(encrypted));
    }

    @Test(dataProvider = "incorrectEncrypt")
    public void decryptICTest(String encrypted, String decrypted ) {
        Assert.assertNotEquals(decrypted,Encryptor.decryptC(encrypted));
    }
}
