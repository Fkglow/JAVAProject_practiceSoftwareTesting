package com.practicesoftwaretesting.tests;

import com.practicesoftwaretesting.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.Core;

import java.util.Comparator;
import java.util.List;

public class SortingTest extends Core {

    public HomePage homePage;

    @BeforeClass
    public void setUp() {
        driver = setDriver("chrome");
        driver.get("https://practicesoftwaretesting.com/");
        driver.manage().window().maximize();
        homePage = new HomePage(driver);
    }

    @Test
    public void sortProductsByNameAscendingTest() {
        homePage.selectSortOptionByIndex(1);
        homePage.waitForSortingToComplete();

        List<String> productsNames = homePage.getAllProductsNames();
        List<String> sortedProductsNames = productsNames.stream().sorted().toList();
        Assert.assertEquals(productsNames, sortedProductsNames);
    }

    @Test
    public void sortProductsByNameDescendingTest() {
        homePage.selectSortOptionByIndex(2);
        homePage.waitForSortingToComplete();

        List<String> productsNames = homePage.getAllProductsNames();
        List<String> sortedProductsNames = productsNames.stream().sorted(Comparator.reverseOrder()).toList();
        Assert.assertEquals(productsNames, sortedProductsNames);
        System.out.println(productsNames);
        System.out.println(sortedProductsNames);
    }

    @Test
    public void sortProductsByPriceHighToLowTest() {
        homePage.selectSortOptionByIndex(3);
        homePage.waitForSortingToComplete();

        List<Double> productsPrices = homePage.getAllProductsPrices();
        List<Double> sortedProductsPrices = productsPrices.stream().sorted(Comparator.reverseOrder()).toList();
        Assert.assertEquals(productsPrices, sortedProductsPrices);
    }

    @Test
    public void sortProductsByPriceLowToHighTest() {
        homePage.selectSortOptionByIndex(4);
        homePage.waitForSortingToComplete();

        List<Double> productsPrices = homePage.getAllProductsPrices();
        List<Double> sortedProductsPrices = productsPrices.stream().sorted().toList();
        Assert.assertEquals(productsPrices, sortedProductsPrices);
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

}
