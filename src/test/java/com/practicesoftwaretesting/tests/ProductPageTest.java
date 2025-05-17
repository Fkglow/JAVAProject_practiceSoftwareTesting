package com.practicesoftwaretesting.tests;

import com.practicesoftwaretesting.pages.HomePage;
import com.practicesoftwaretesting.pages.ProductPage;
import com.practicesoftwaretesting.pages.TopMenuBar;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.Core;

import java.util.List;

public class ProductPageTest extends Core {

    public HomePage homePage;
    public TopMenuBar topMenuBar;
    public ProductPage productPage;

    @BeforeClass
    public void setUp() {
        driver = setDriver("chrome");
        driver.get("https://practicesoftwaretesting.com/");
        driver.manage().window().maximize();
        homePage = new HomePage(driver);
        topMenuBar = new TopMenuBar(driver);
    }

    @Test(priority = 1)
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

    @Test(priority = 2)
    public void productCanBeAddedToCartTest() {
        productPage.clickAddToCartButton();
        Assert.assertTrue(productPage.isSuccessToastDisplayed());
        Assert.assertEquals(productPage.getSuccessToastMessage(), "Product added to shopping cart.");

        topMenuBar.waitForCartIconToAppear();
        Assert.assertEquals(topMenuBar.getCartQuantity(), "1");
    }

    @Test(priority = 3)
    public void quantityOfProductCanBeUpdated() throws InterruptedException {
        productPage.clickIncreaseQuantityButton();
        productPage.clickIncreaseQuantityButton();
        productPage.clickAddToCartButton();
        productPage.waitForToastToAppear();
        Thread.sleep(1000);
        Assert.assertEquals(topMenuBar.getCartQuantity(), "4");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
