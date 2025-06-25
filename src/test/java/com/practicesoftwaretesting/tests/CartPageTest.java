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
    public void setUp() {
        driver = setDriver("chrome");
        driver.manage().window().maximize();
        driver.get("https://practicesoftwaretesting.com/");
        homePage = new HomePage(driver);
        topMenuBar = new TopMenuBar(driver);
        WebElement product = homePage.getProductsList().getFirst();
        homePage.waitForProductImageToLoad(product);
        productPage = homePage.goToProduct(product);

        PRODUCT_NAME = productPage.getProductName();
        PRODUCT_PRICE = productPage.getProductPrice();

        productPage.clickAddToCartButton();
        productPage.waitForToastToAppear();
        productPage.waitForToastToDisappear();
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
        List<WebElement> productsList = cartPage.getProductsRows();
        productRow = productsList.getFirst();
        int quantity = new Random().nextInt(10) + 1;
        double totalPrice = quantity * PRODUCT_PRICE;
        cartPage.enterProductQuantity(productRow, String.valueOf(quantity));

        cartPage.waitForToastToAppear();
        Assert.assertTrue(cartPage.isSuccessToastDisplayed());
        Assert.assertEquals(cartPage.getSuccessToastMessage(), "Product quantity updated.");
        cartPage.waitForToastToDisappear();
        String totalPriceValue = cartPage.getCartTotalPrice();
        String expectedTotalPrice = String.format(Locale.US,"%.2f", totalPrice);
        Assert.assertEquals(totalPriceValue, "$"+expectedTotalPrice);
        Assert.assertEquals(topMenuBar.getCartQuantity(), String.valueOf(quantity));
    }

    @Test
    public void deleteProductFromCartTest() {
        List<WebElement> productsList = cartPage.getProductsRows();
        productRow = productsList.getFirst();
        cartPage.clickRemoveProductFromCart(productRow);
        cartPage.waitForToastToAppear();
        Assert.assertTrue(cartPage.isSuccessToastDisplayed());
        Assert.assertEquals(cartPage.getSuccessToastMessage(), "Product deleted.");
        cartPage.waitForToastToDisappear();
        Assert.assertEquals(cartPage.getEmptyCartMessage(), "The cart is empty. Nothing to display.");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
