package com.example.topoftops.validation;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class UserInfoValidatorTest {
    @DataProvider(name = "validEmail")
    public static Object[][] validEmail() {
        return new Object[][]{{"ilyatsv@gmail.com"}, {"testemail@tut.by"}, {"ilya0702@rambler.ru"}};
    }

    @Test(dataProvider = "validEmail")
    public void isValidEmailPositiveTest(String email) {
        Assert.assertTrue(UserInfoValidator.isValidEmail(email));
    }

    @DataProvider(name = "invalidEmail")
    public static Object[][] invalidEmail() {
        return new Object[][]{{"testemail"}, {null}, {"@mail.sd@da"}};
    }

    @Test(dataProvider = "invalidEmail")
    public void isValidEmailNegativeTest(String email) {
        Assert.assertFalse(UserInfoValidator.isValidEmail(email));
    }

    @DataProvider(name = "validPassword")
    public static Object[][] validPassword() {
        return new Object[][]{{"Ilyatsv123456"}, {"sdaSDaw212"}, {"Acw12A2d"}};
    }

    @Test(dataProvider = "validPassword")
    public void isValidPasswordPositiveTest(String password) {
        Assert.assertTrue(UserInfoValidator.isValidPassword(password));
    }

    @DataProvider(name = "invalidPassword")
    public static Object[][] invalidPassword() {
        return new Object[][]{{"1"}, {"asdasd"}, {null}};
    }

    @Test(dataProvider = "invalidPassword")
    public void isValidPasswordNegativeTest(String password) {
        Assert.assertFalse(UserInfoValidator.isValidPassword(password));
    }

    @DataProvider(name = "validLogin")
    public static Object[][] validLogin() {
        return new Object[][]{{"testuser"}, {"Pushka"}, {"pelmeShek"}};
    }

    @Test(dataProvider = "validLogin")
    public void isValidLoginPositiveTest(String login) {
        Assert.assertTrue(UserInfoValidator.isValidLogin(login));
    }

    @DataProvider(name = "invalidLogin")
    public static Object[][] invalidLogin() {
        return new Object[][]{{"a"}, {null}, {"прикол"}};
    }

    @Test(dataProvider = "invalidLogin")
    public void isValidLoginNegativeTest(String login) {
        Assert.assertFalse(UserInfoValidator.isValidLogin(login));
    }

}
