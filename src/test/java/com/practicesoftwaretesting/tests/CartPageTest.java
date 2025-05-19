package com.practicesoftwaretesting.tests;

import com.practicesoftwaretesting.pages.CartPage;
import com.practicesoftwaretesting.pages.HomePage;
import com.practicesoftwaretesting.pages.ProductPage;
import com.practicesoftwaretesting.pages.TopMenuBar;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;
import utils.Core;

import java.util.List;
import java.util.Locale;
import java.util.Random;

public class CartPageTest extends Core {

    public HomePage homePage;
    public TopMenuBar topMenuBar;
    public ProductPage productPage;
    public CartPage cartPage;
    public String PRODUCT_NAME;
    public double PRODUCT_PRICE;
    public WebElement productRow;

    @BeforeMethod
    public void setUp() throws InterruptedException {
        driver = setDriver("chrome");
        driver.get("https://practicesoftwaretesting.com/");
        driver.manage().window().maximize();
        homePage = new HomePage(driver);
        topMenuBar = new TopMenuBar(driver);
        WebElement product = homePage.getProductsList().getFirst();
        productPage = homePage.goToProduct(product);

        PRODUCT_NAME = productPage.getProductName();
        PRODUCT_PRICE = productPage.getProductPrice();

        productPage.clickAddToCartButton();
        topMenuBar.waitForCartIconToAppear();
        cartPage = topMenuBar.clickCartButton();
    }

    @Test
    public void addProductToCartTest() {
        List<WebElement> productsList = cartPage.getProductsRows();
        productRow = productsList.getFirst();

        Assert.assertEquals(productsList.size(), 1);
        Assert.assertEquals(cartPage.getProductName(productRow).strip(), PRODUCT_NAME);
        Assert.assertEquals(cartPage.getProductPrice(productRow), PRODUCT_PRICE);
        Assert.assertEquals(cartPage.getCartTotalPrice(), "$"+PRODUCT_PRICE);
    }

    @Test
    public void productQuantityIncreaseTest() {
        productPage.waitForToastToDisappear();
        List<WebElement> productsList = cartPage.getProductsRows();
        productRow = productsList.getFirst();
        int quantity = new Random().nextInt(10) + 1;
        double totalPrice = quantity * PRODUCT_PRICE;
        cartPage.enterProductQuantity(productRow, String.valueOf(quantity));

        cartPage.waitForToastToAppear();
        Assert.assertTrue(cartPage.isSuccessToastDisplayed());
        Assert.assertEquals(topMenuBar.getCartQuantity(), String.valueOf(quantity));
        String totalPriceValue = cartPage.getCartTotalPrice();
        String expectedTotalPrice = String.format(Locale.US,"%.2f", totalPrice);
        Assert.assertEquals(totalPriceValue, "$"+expectedTotalPrice);
    }

    @Test
    public void deleteProductFromCartTest() {
        productPage.waitForToastToDisappear();
        List<WebElement> productsList = cartPage.getProductsRows();
        productRow = productsList.getFirst();
        cartPage.clickRemoveProductFromCart(productRow);
        cartPage.waitForToastToAppear();
        Assert.assertTrue(cartPage.isSuccessToastDisplayed());
        Assert.assertEquals(cartPage.getSuccessToastMessage(), "Product deleted.");
        Assert.assertEquals(cartPage.getCartTotalPrice(), "$0.00");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
