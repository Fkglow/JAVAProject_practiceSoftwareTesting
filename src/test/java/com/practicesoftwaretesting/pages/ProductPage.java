package com.practicesoftwaretesting.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductPage {

    private WebDriver driver;

    @FindBy(css = "[data-test='product-name']")
    private WebElement productName;
    @FindBy(css = "[data-test='unit-price']")
    private WebElement productPrice;
    @FindBy(id = "btn-add-to-cart")
    private WebElement addToCartButton;
    @FindBy(id = "btn-increase-quantity")
    private WebElement increaseQuantityButton;
    private By successToastMessage = By.cssSelector(".toast-message");

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlContains("https://practicesoftwaretesting.com/product/"));
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOf(productName));
    }

    public String getProductName() {
        return productName.getText();
    }

    public double getProductPrice() {
        String price = productPrice.getText();
        return Double.parseDouble(price);
    }

    public void clickAddToCartButton() {
        addToCartButton.click();
    }

    public boolean isSuccessToastDisplayed() {
        WebElement message = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(successToastMessage));
        return message.isDisplayed();
    }

    public String getSuccessToastMessage() {
        WebElement message = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(successToastMessage));
        return message.getText();
    }

}
