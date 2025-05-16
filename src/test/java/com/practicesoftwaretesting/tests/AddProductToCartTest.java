package com.practicesoftwaretesting.tests;

import com.practicesoftwaretesting.pages.CartPage;
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

public class AddProductToCartTest extends Core {

    public HomePage homePage;
    public TopMenuBar topMenuBar;
    public ProductPage productPage;
    public CartPage cartPage;

    @BeforeClass
    public void setUp() {
        driver = setDriver("chrome");
        driver.get("https://practicesoftwaretesting.com/");
        driver.manage().window().maximize();
        homePage = new HomePage(driver);
        topMenuBar = new TopMenuBar(driver);
    }

    @Test
    public void productCanBeAddedToCartTest() {
        WebElement product = homePage.getProductsList().getFirst();
        productPage = homePage.goToProduct(product);

        String productName = productPage.getProductName();
        double productPrice = productPage.getProductPrice();

        productPage.clickAddToCartButton();
        Assert.assertTrue(productPage.isSuccessToastDisplayed());
        Assert.assertEquals(productPage.getSuccessToastMessage(), "Product added to shopping cart.");

        cartPage = topMenuBar.clickCartButton();
        List<WebElement> productsList = cartPage.getProductsRows();
        WebElement productRow = productsList.getFirst();

        Assert.assertEquals(productsList.size(), 1);
        Assert.assertEquals(cartPage.getProductName(productRow).strip(), productName);
        Assert.assertEquals(cartPage.getProductPrice(productRow), productPrice);
        Assert.assertEquals(cartPage.getCartTotalPrice(), productPrice);

    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
