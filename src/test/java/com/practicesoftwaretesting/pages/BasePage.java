package com.practicesoftwaretesting.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    public WebDriver driver;
    private By successToastMessage = By.cssSelector(".toast-message");
    private By pageLogo = By.id("Layer_1");

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public void waitForToastToDisappear() {
        // Making sure not hovering over toast, as it would not disappear
        WebElement logo = driver.findElement(pageLogo);
        new Actions(driver).moveToElement(logo).perform();
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.invisibilityOfElementLocated(successToastMessage));
    }

    public void waitForToastToAppear() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(successToastMessage));
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
