package com.practicesoftwaretesting.tests;

import com.practicesoftwaretesting.pages.HomePage;
import com.practicesoftwaretesting.pages.ProductPage;
import com.practicesoftwaretesting.pages.TopMenuBar;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;
import utils.Core;

import java.util.List;

public class ProductPageTest extends Core {

    public HomePage homePage;
    public TopMenuBar topMenuBar;
    public ProductPage productPage;

    @BeforeMethod
    public void setUp() {
        driver = setDriver("chrome");
        driver.get("https://practicesoftwaretesting.com/");
        driver.manage().window().maximize();
        homePage = new HomePage(driver);
        topMenuBar = new TopMenuBar(driver);
    }

    @Test
    public void productPropertiesTest() {
        List<WebElement> productsList = homePage.getProductsList();
        WebElement product = productsList.getFirst();
        String productName = homePage.getProductName(product);
        double productPrice = homePage.getProductPrice(product);
        productPage = homePage.goToProduct(product);
        Assert.assertTrue(productPage.isPageDisplayed());
        Assert.assertEquals(productPage.getProductName(), productName);
        Assert.assertEquals(productPage.getProductPrice(), productPrice);
        Assert.assertFalse(productPage.getProductImageSrc().isEmpty());
    }

    @Test
    public void productCanBeAddedToCartTest() {
        List<WebElement> productsList = homePage.getProductsList();
        WebElement product = productsList.getFirst();
        productPage = homePage.goToProduct(product);
        productPage.clickAddToCartButton();
        Assert.assertTrue(productPage.isSuccessToastDisplayed());
        Assert.assertEquals(productPage.getSuccessToastMessage(), "Product added to shopping cart.");

        topMenuBar.waitForCartIconToAppear();
        Assert.assertEquals(topMenuBar.getCartQuantity(), "1");
    }

    @Test
    public void quantityOfProductCanBeUpdated() {
        List<WebElement> productsList = homePage.getProductsList();
        WebElement product = productsList.getFirst();
        productPage = homePage.goToProduct(product);
        productPage.clickIncreaseQuantityButton();
        productPage.clickIncreaseQuantityButton();
        productPage.clickAddToCartButton();
        productPage.waitForToastToAppear();
        Assert.assertEquals(topMenuBar.getCartQuantity(), "3");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}