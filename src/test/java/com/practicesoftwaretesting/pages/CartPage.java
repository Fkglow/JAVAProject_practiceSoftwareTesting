package com.practicesoftwaretesting.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CartPage {

    private WebDriver driver;

    @FindBy(css = "tbody")
    private WebElement productsTable;
    @FindBy(css = "[data-test='cart-total']")
    private WebElement cartTotalPrice;
    // PRODUCT ROW
    private By productRow = By.cssSelector("tr.ng-star-inserted");
    private By productName = By.cssSelector(".product-title");
    private By quantityInput = By.cssSelector("[data-test='product-quantity']");
    private By productPrice = By.cssSelector("[data-test='product-price']");
    private By productRowTotalPrice = By.cssSelector("data-test='line-price']");
    private By removeProductButton = By.cssSelector(".btn-danger");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe("https://practicesoftwaretesting.com/checkout"));
    }

    public List<WebElement> getProductsRows() {
        return productsTable.findElements(productRow);
    }

    public String getProductName(WebElement productRow) {
        return productRow.findElement(productName).getText();
    }

    public void enterProductQuantity(WebElement productRow, String quantity) {
        productRow.findElement(quantityInput).sendKeys(quantity);
    }

    public double getProductPrice(WebElement productRow) {
        String priceStr = productRow.findElement(productPrice).getText();
        return getDoubleFromPriceWithCurrencySting(priceStr);
    }

    public double getProductRowTotalPrice(WebElement productRow) {
        String priceStr = productRow.findElement(productRowTotalPrice).getText();
        return getDoubleFromPriceWithCurrencySting(priceStr);
    }

    public double getCartTotalPrice() {
        String total = cartTotalPrice.getText();
        return getDoubleFromPriceWithCurrencySting(total);
    }

    private double getDoubleFromPriceWithCurrencySting(String priceWithCurrency) {
        String priceNum = priceWithCurrency.substring(1);
        return Double.parseDouble(priceNum);
    }

}
