package com.practicesoftwaretesting.tests;

import com.practicesoftwaretesting.pages.LoginPage;
import com.practicesoftwaretesting.pages.RegistrationPage;
import com.practicesoftwaretesting.pages.TopMenuBar;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import utils.Core;
import utils.TestDataGenerator;

import java.util.List;

public class RegistrationFormValidationsTest extends Core {

    public TopMenuBar topMenuBar;
    public LoginPage loginPage;
    public RegistrationPage regPage;
    public TestDataGenerator dataGenerator;
    public SoftAssert softlyAssert;

    @BeforeClass
    public void setUp() {
        driver = setDriver("chrome");
        driver.manage().window().maximize();
        driver.get("https://practicesoftwaretesting.com/");
        topMenuBar = new TopMenuBar(driver);
        dataGenerator = new TestDataGenerator();
        softlyAssert = new SoftAssert();
        loginPage = topMenuBar.clickSignInButton();
        regPage = loginPage.clickRegisterAccountButton();
    }

    @Test
    public void emptyRequiredFieldsValidationTest() {
        regPage.clickRegisterButton();
        List<WebElement> errorsList = regPage.getValidationErrors();
        List<String> validationMessagesList = regPage.getValidationErrorMessages(errorsList);
        Assert.assertEquals(errorsList.size(), 11);
        softlyAssert.assertEquals(validationMessagesList.get(0), "First name is required");
        softlyAssert.assertEquals(validationMessagesList.get(1), "Last name is required");
        softlyAssert.assertEquals(validationMessagesList.get(2), "Date of Birth is required");
        softlyAssert.assertEquals(validationMessagesList.get(3), "Street is required");
        softlyAssert.assertEquals(validationMessagesList.get(4), "Postcode is required");
        softlyAssert.assertEquals(validationMessagesList.get(5), "City is required");
        softlyAssert.assertEquals(validationMessagesList.get(6), "State is required");
        softlyAssert.assertEquals(validationMessagesList.get(7), "Country is required");
        softlyAssert.assertEquals(validationMessagesList.get(8), "Phone is required.");
        softlyAssert.assertEquals(validationMessagesList.get(9), "Email is required");
        softlyAssert.assertEquals(regPage.getPasswordValidationMessages().getFirst(), "Password is required");
        softlyAssert.assertAll();
    }

    @Test
    public void passwordMustBeAtLeast8CharactersLong() {
        String randomPassword = dataGenerator.generatePasswordShorterThan8Chars();
        regPage.enterPassword(randomPassword);
        System.out.println(randomPassword);
        Assert.assertEquals(regPage.getPasswordValidationMessages().size(), 1);
        Assert.assertFalse(regPage.doesPasswordMatchThisRuleByIndex(0));
    }

    @Test
    public void passwordMustContainBothUpperAndLowerCase() {
        String randomPass = dataGenerator.generatePasswordWithoutUpperCase();
        regPage.enterPassword(randomPass);
        Assert.assertFalse(regPage.doesPasswordMatchThisRuleByIndex(1));
    }

    @Test
    public void passwordMustIncludeAtLeast1NumberTest() {
        String pass = dataGenerator.generatePasswordWithoutNumber();
        regPage.enterPassword(pass);
        Assert.assertFalse(regPage.doesPasswordMatchThisRuleByIndex(2));
    }

    @Test
    public void passwordMustContainAtLeast1SpecialCharTest() {
        String pass = dataGenerator.generatePasswordWithoutSpecialSymbol();
        regPage.enterPassword(pass);
        System.out.println(pass);
        Assert.assertFalse(regPage.doesPasswordMatchThisRuleByIndex(3));
    }

    @Test
    public void validPasswordDoesNotThrowValidationErrors() {
        String pass = dataGenerator.generateCorrectPassword();
        regPage.enterPassword(pass);
        softlyAssert.assertTrue(regPage.doesPasswordMatchThisRuleByIndex(0));
        softlyAssert.assertTrue(regPage.doesPasswordMatchThisRuleByIndex(1));
        softlyAssert.assertTrue(regPage.doesPasswordMatchThisRuleByIndex(2));
        softlyAssert.assertTrue(regPage.doesPasswordMatchThisRuleByIndex(3));
        softlyAssert.assertAll();
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

}
