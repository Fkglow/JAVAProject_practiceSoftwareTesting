package com.practicesoftwaretesting.tests;

import com.practicesoftwaretesting.pages.HomePage;
import com.practicesoftwaretesting.pages.ProductPage;
import com.practicesoftwaretesting.pages.TopMenuBar;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;
import utils.Core;

public class ProductPageTest extends Core {

    public HomePage homePage;
    public TopMenuBar topMenuBar;
    public ProductPage productPage;

    @BeforeMethod
    public void setUp() {
        driver = setDriver("chrome");
        driver.manage().window().maximize();
        driver.get("https://practicesoftwaretesting.com/");
        homePage = new HomePage(driver);
        topMenuBar = new TopMenuBar(driver);
    }

    @Test
    public void productPropertiesTest() {
        WebElement product = homePage.getProductsList().getFirst();
        homePage.waitForProductImageToLoad(product);
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
        WebElement product = homePage.getProductsList().getFirst();
        homePage.waitForProductImageToLoad(product);
        productPage = homePage.goToProduct(product);
        productPage.clickAddToCartButton();
        Assert.assertTrue(productPage.isSuccessToastDisplayed());
        Assert.assertEquals(productPage.getSuccessToastMessage(), "Product added to shopping cart.");

        topMenuBar.waitForCartIconToAppear();
        Assert.assertEquals(topMenuBar.getCartQuantity(), "1");
    }

    @Test
    public void quantityOfProductCanBeUpdated() {
        WebElement product = homePage.getProductsList().getFirst();
        homePage.waitForProductImageToLoad(product);
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