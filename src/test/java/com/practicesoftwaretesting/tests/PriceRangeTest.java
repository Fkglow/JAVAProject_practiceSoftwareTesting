package com.practicesoftwaretesting.tests;

import com.practicesoftwaretesting.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.*;
import utils.Core;

import java.util.List;

public class PriceRangeTest extends Core {

    public HomePage homePage;

    @BeforeMethod
    public void setUp() {
        driver = setDriver("chrome");
        driver.get("https://practicesoftwaretesting.com/");
        driver.manage().window().maximize();
        homePage = new HomePage(driver);
    }

    @Test
    public void productsPricesShouldReflectPriceRangeAfterMinPriceUpdate() {
        homePage.changeMinPrice();
        homePage.waitForTableToReload();
        int minPrice = homePage.getMinPriceFromPriceRange();
        int maxPrice = homePage.getMaxPriceFromPriceRange();

        List<Double> prices = homePage.getAllProductsPrices();
        for (Double price : prices) {
            Assert.assertTrue(price > minPrice);
            Assert.assertTrue(price < maxPrice);
        }
    }

    @Test
    public void productsPricesShouldReflectPriceRangeAfterMaxPriceUpdate() {
        homePage.changeMaxPrice();
        homePage.waitForTableToReload();
        int minPrice = homePage.getMinPriceFromPriceRange();
        int maxPrice = homePage.getMaxPriceFromPriceRange();

        List<Double> prices = homePage.getAllProductsPrices();
        for (Double price : prices) {
            Assert.assertTrue(price > minPrice);
            Assert.assertTrue(price < maxPrice);
        }
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

}
