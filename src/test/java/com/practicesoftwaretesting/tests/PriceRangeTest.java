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
        driver.manage().window().maximize();
        driver.get("https://practicesoftwaretesting.com/");
        homePage = new HomePage(driver);
    }

    @Test
    public void productsPricesShouldReflectPriceRangeAfterMinPriceUpdate() {
        List<String> initialProducts = homePage.getProductsNamesFromTheList();
        homePage.changeMinPrice();
        homePage.waitForTableNamesToReload(initialProducts);
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
        List<String> initialProducts = homePage.getProductsNamesFromTheList();
        homePage.changeMaxPrice();
        homePage.waitForTableNamesToReload(initialProducts);
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
