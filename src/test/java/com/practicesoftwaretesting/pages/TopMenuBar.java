package com.practicesoftwaretesting.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TopMenuBar {

    private WebDriver driver;

    @FindBy(css = "[data-test='nav-home']")
    private WebElement homeButton;
    @FindBy(css = "[data-test='nav-categories']")
    private WebElement categoriesButton;
    @FindBy(css = "[data-test='nav-contact']")
    private WebElement contactButton;
    @FindBy(css = "[data-test='nav-sign-in']")
    private WebElement signInButton;
    private By cartQuantity = By.cssSelector("[data-test='cart-quantity']");
    private By cartButton = By.cssSelector("[data-test='nav-cart']");
    @FindBy(id = "language")
    private WebElement languageButton;

    public TopMenuBar(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    public void clickHomeButton() {
        homeButton.click();
    }

    public void clickCategoriesButton() {
        categoriesButton.click();
    }

    public void clickContactButton() {
        contactButton.click();
    }

    public LoginPage clickSignInButton() {
        signInButton.click();
        return new LoginPage(driver);
    }

    public void waitForCartIconToAppear() {
        new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.visibilityOfElementLocated(cartButton));
    }

    public CartPage clickCartButton() {
        driver.findElement(cartButton).click();
        return new CartPage(driver);
    }

    public String getCartQuantity() {
        return driver.findElement(cartQuantity).getText();
    }
}
