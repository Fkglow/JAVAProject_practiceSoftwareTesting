package com.practicesoftwaretesting.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductPage extends BasePage{

    private WebDriver driver;

    @FindBy(css = ".figure-img")
    private WebElement image;
    @FindBy(css = "[data-test='product-name']")
    private WebElement productName;
    @FindBy(css = "[data-test='unit-price']")
    private WebElement productPrice;
    @FindBy(id = "btn-add-to-cart")
    private WebElement addToCartButton;
    @FindBy(id = "btn-increase-quantity")
    private WebElement increaseQuantityButton;

    public ProductPage(WebDriver driver) {
        super(driver);
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlContains("https://practicesoftwaretesting.com/product/"));
        PageFactory.initElements(driver, this);
    }

    public boolean isPageDisplayed() {
        return image.isDisplayed();
    }

    public String getProductImageSrc() {
        return image.getAttribute("src");
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

    public void clickIncreaseQuantityButton() {
        increaseQuantityButton.click();
    }

}
