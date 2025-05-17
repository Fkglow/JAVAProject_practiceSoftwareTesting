package com.practicesoftwaretesting.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    private WebDriver driver;
    private By successToastMessage = By.cssSelector(".toast-message");

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public void waitForToastToAppear() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(successToastMessage));
    }

    public void waitForToastToDisappear() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.invisibilityOfElementLocated(successToastMessage));
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
