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
import java.util.Random;

public class CartPageTest extends Core {

    public HomePage homePage;
    public TopMenuBar topMenuBar;
    public ProductPage productPage;
    public CartPage cartPage;
    public String PRODUCT_NAME;
    public double PRODUCT_PRICE;
    public WebElement productRow;

    @BeforeClass
    public void setUp() {
        driver = setDriver("chrome");
        driver.get("https://practicesoftwaretesting.com/");
        driver.manage().window().maximize();
        homePage = new HomePage(driver);
        topMenuBar = new TopMenuBar(driver);
    }

    @Test(priority = 1)
    public void addProductToCartTest() {
        WebElement product = homePage.getProductsList().getFirst();
        productPage = homePage.goToProduct(product);

        PRODUCT_NAME = productPage.getProductName();
        PRODUCT_PRICE = productPage.getProductPrice();

        productPage.clickAddToCartButton();
        topMenuBar.waitForCartIconToAppear();
        cartPage = topMenuBar.clickCartButton();
        List<WebElement> productsList = cartPage.getProductsRows();
        productRow = productsList.getFirst();

        Assert.assertEquals(productsList.size(), 1);
        Assert.assertEquals(cartPage.getProductName(productRow).strip(), PRODUCT_NAME);
        Assert.assertEquals(cartPage.getProductPrice(productRow), PRODUCT_PRICE);
        Assert.assertEquals(cartPage.getCartTotalPrice(), PRODUCT_PRICE);
    }

    @Test(priority = 2)
    public void productQuantityIncreaseTest() throws InterruptedException {
        int quantity = new Random().nextInt(10) + 1;
        double totalPrice = quantity * PRODUCT_PRICE;
        cartPage.enterProductQuantity(productRow, String.valueOf(quantity));

        Assert.assertTrue(cartPage.isSuccessToastDisplayed());
        Thread.sleep(2000);
        Assert.assertEquals(topMenuBar.getCartQuantity(), String.valueOf(quantity));
        Assert.assertEquals(cartPage.getCartTotalPrice(), totalPrice);
    }

    @Test(priority = 3)
    public void deleteProductFromCartTest() throws InterruptedException {
        Thread.sleep(1000);
        cartPage.clickRemoveProductFromCart(productRow);
        Assert.assertTrue(cartPage.isSuccessToastDisplayed());
        Assert.assertEquals(cartPage.getSuccessToastMessage(), "Product deleted.");
        Assert.assertEquals(cartPage.getCartTotalPrice(), "0.00");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
