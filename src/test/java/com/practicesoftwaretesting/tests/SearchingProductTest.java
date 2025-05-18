package com.practicesoftwaretesting.tests;

import com.practicesoftwaretesting.pages.HomePage;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.Core;
import utils.TestDataGenerator;

import java.util.List;

public class SearchingProductTest extends Core {

    public HomePage homePage;
    public TestDataGenerator dataGenerator;

    @BeforeClass
    public void setUp() {
        driver = setDriver("chrome");
        driver.get("https://practicesoftwaretesting.com/");
        driver.manage().window().maximize();
        homePage = new HomePage(driver);
    }

    @Test
    public void searchingProductsTest() {
        String searchInput = dataGenerator.getRandomProductName();
        homePage.enterProductInSearchField(searchInput);
        homePage.clickSearchProductButton();
        homePage.waitForTableToReload();
        List<WebElement> productsList = homePage.getProductsList();
        WebElement product = productsList.getFirst();
        Assert.assertEquals(homePage.getProductName(product), searchInput);
    }

    @Test
    public void enteringRandomTextInFilterTest() {
        dataGenerator = new TestDataGenerator();
        homePage.enterProductInSearchField(dataGenerator.generateRandomString());
        homePage.clickSearchProductButton();
        homePage.waitForTableToReload();
        List<WebElement> productsList = homePage.getProductsList();
        Assert.assertEquals(productsList.size(), 0);
    }

    @Test
    public void productNameIsDisplayedInSearchCaption() {
        String searchInput = dataGenerator.getRandomProductName();
        homePage.enterProductInSearchField(searchInput);
        homePage.clickSearchProductButton();
        homePage.waitForTableToReload();
        Assert.assertEquals(homePage.getSearchCaptionText(), "Searched for: " + searchInput);
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
