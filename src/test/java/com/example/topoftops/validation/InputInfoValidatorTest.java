package com.example.topoftops.validation;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class InputInfoValidatorTest {
    @DataProvider(name = "validTitle")
    public static Object[][] validTitle() {
        return new Object[][]{{"ilyatsv"}, {"testetitle2"}, {"bubul2k1"}};
    }

    @Test(dataProvider = "validTitle")
    public void isValidTitlePositiveTest(String title) {
        Assert.assertTrue(InputInfoValidator.isValidTitle(title));
    }

    @DataProvider(name = "invalidTitle")
    public static Object[][] invalidTitle() {
        return new Object[][]{{"asddskadkanskdnkawndwdawdawa"}, {null}, {"@mail.sd@da"}};
    }

    @Test(dataProvider = "invalidTitle")
    public void isValidtitleNegativeTest(String title) {
        Assert.assertFalse(InputInfoValidator.isValidTitle(title));
    }

    @DataProvider(name = "validDescription")
    public static Object[][] validDescription() {
        return new Object[][]{{"new top"}, {"about marvel films"}, {"топ игр"}};
    }

    @Test(dataProvider = "validDescription")
    public void isValidDescriptionPositiveTest(String description) {
        Assert.assertTrue(InputInfoValidator.isValidDescription(description));
    }

    @DataProvider(name = "invalidDescription")
    public static Object[][] invalidDescription() {
        return new Object[][]{{"asd21d."}, {"//ad2/1a']"}, {null}};
    }

    @Test(dataProvider = "invalidDescription")
    public void isValidDescriptionNegativeTest(String description) {
        Assert.assertFalse(InputInfoValidator.isValidDescription(description));
    }

    @DataProvider(name = "validSearch")
    public static Object[][] validSearch() {
        return new Object[][]{{"testuser"}, {"Pushka"}, {"pelmeShek"}};
    }

    @Test(dataProvider = "validSearch")
    public void isValidSearchPositiveTest(String search) {
        Assert.assertTrue(InputInfoValidator.isValidSearch(search));
    }

    @DataProvider(name = "invalidSearch")
    public static Object[][] invalidSearch() {
        return new Object[][]{{"a.,.,"}, {null}, {"i1203912039j12093j11klnlkawndlkan"}};
    }

    @Test(dataProvider = "invalidSearch")
    public void isValidSearchNegativeTest(String search) {
        Assert.assertFalse(InputInfoValidator.isValidSearch(search));
    }
}
