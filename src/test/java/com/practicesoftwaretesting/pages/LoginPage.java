package com.practicesoftwaretesting.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    public WebDriver driver;

    @FindBy(id = "email")
    private WebElement emailInput;
    @FindBy(id = "password")
    private WebElement passwordInput;
    @FindBy(css = "[data-test='login-submit']")
    private WebElement loginButton;
    @FindBy(css = "[data-test='register-link']")
    private WebElement registerAccountButton;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe("https://practicesoftwaretesting.com/auth/login"));
        PageFactory.initElements(driver, this);
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOf(emailInput));
    }

    public RegistrationPage clickRegisterAccountButton() {
        registerAccountButton.click();
        return new RegistrationPage(driver);
    }


}
